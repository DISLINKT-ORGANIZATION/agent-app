package agent.agentapp.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import agent.agentapp.dtos.AgentCreateRequest;
import agent.agentapp.dtos.CompanyDto;
import agent.agentapp.dtos.CreateRegisterCompanyRequestDto;
import agent.agentapp.dtos.PasswordDto;
import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.RegisterCompanyRequestDto;
import agent.agentapp.dtos.UpdateCompanyDto;
import agent.agentapp.entities.Company;
import agent.agentapp.entities.RegisterCompanyRequest;
import agent.agentapp.entities.User;
import agent.agentapp.exceptions.EmailAlreadyExists;
import agent.agentapp.exceptions.EntityNotFound;
import agent.agentapp.exceptions.RequestAlreadyExists;
import agent.agentapp.exceptions.UsernameAlreadyExists;
import agent.agentapp.exceptions.UsernameOrEmailAlreadyExists;
import agent.agentapp.mappers.CompanyMapper;
import agent.agentapp.mappers.RegisterCompanyRequestMapper;
import agent.agentapp.repositories.AuthorityRepository;
import agent.agentapp.repositories.CompanyRepository;
import agent.agentapp.repositories.PersonRepository;
import agent.agentapp.repositories.RegisterCompanyRequestRepository;
import agent.agentapp.repositories.UserRepository;
import agent.agentapp.services.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Value("${dislinkt.auth-service.api}")
	private String authServiceApi;

	@Autowired
	private RegisterCompanyRequestRepository registerCompanyRequestRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private RegisterCompanyRequestMapper registerCompanyRequestMapper;

	@Autowired
	private CompanyMapper companyMapper;

	public CompanyDto getCompany(Long companyId) {
		Optional<Company> companyOptional = companyRepository.findById(companyId);
		if (companyOptional.isEmpty()) {
			throw new EntityNotFound("Company not found.");
		}
		Company company = companyOptional.get();
		return companyMapper.toDtoWithLists(company, company.getUser());
	}

	public List<CompanyDto> getCompanies() {
		return companyMapper.toListDto(companyRepository.findAll());
	}

	public CompanyDto updateCompany(UpdateCompanyDto dto) {
		Optional<Company> companyOptional = companyRepository.findById(dto.getId());
		if (companyOptional.isEmpty()) {
			throw new EntityNotFound("Company not found.");
		}
		Company company = companyOptional.get();
		company.setName(dto.getName());
		company.setDescription(dto.getDescription());
		companyRepository.save(company);
		return companyMapper.toDtoWithLists(company, company.getUser());
	}

	public RegisterCompanyRequestDto requestRegistration(CreateRegisterCompanyRequestDto registerCompanyRequestDto) {
		Optional<RegisterCompanyRequest> requestOptional = registerCompanyRequestRepository
				.findByUserId(registerCompanyRequestDto.getUserId());
		if (requestOptional.isPresent()) {
			throw new RequestAlreadyExists("You have already send a company registration request.");
		}
		
		User user = (User) personRepository.findById(registerCompanyRequestDto.getUserId()).get();

		RestTemplate restTemplate = new RestTemplate();
		String checkUsernameUrl = authServiceApi + "/authentication/users/check-username/" + user.getUsername();
		String checkEmailUrl = authServiceApi + "/authentication/users/check-email/" + user.getEmail();

		ResponseEntity<Boolean> checkUsernameResponse = restTemplate.getForEntity(checkUsernameUrl, Boolean.class);
		if (checkUsernameResponse.getBody()) {
			throw new UsernameAlreadyExists("Your username already exists on dislinkt application. Please change it.");
		}

		ResponseEntity<Boolean> checkEmailResponse = restTemplate.getForEntity(checkEmailUrl, Boolean.class);
		if (checkEmailResponse.getBody()) {
			throw new EmailAlreadyExists("Your email already exists on dislinkt application. Please change it.");
		}

		RegisterCompanyRequest newRegisterCompanyRequest = registerCompanyRequestMapper
				.toEntity(registerCompanyRequestDto);
		RegisterCompanyRequest savedRegisterCompanyRequest = registerCompanyRequestRepository
				.save(newRegisterCompanyRequest);
		return registerCompanyRequestMapper.toDto(savedRegisterCompanyRequest, user);
	}

	public List<RegisterCompanyRequestDto> getRegisterCompanyRequests() {
		List<RegisterCompanyRequest> requests = registerCompanyRequestRepository.findByApproved(false);
		return registerCompanyRequestMapper.toListDto(requests);
	}

	public Boolean approveRequest(Long requestId) {
		// request approved
		Optional<RegisterCompanyRequest> requestOptional = registerCompanyRequestRepository.findById(requestId);
		if (requestOptional.isEmpty()) {
			throw new EntityNotFound("Registration request with this id not found.");
		}
		RegisterCompanyRequest request = requestOptional.get();
		request.setApproved(true);
		registerCompanyRequestRepository.save(request);
		return true;
	}

	public RegisterCompanyRequestDto getApprovedRequestForUser(Long userId) {
		Optional<RegisterCompanyRequest> requestOptional = registerCompanyRequestRepository
				.findByUserIdAndApproved(userId, true);
		if (requestOptional.isEmpty()) {
			return null;
		}
		RegisterCompanyRequest request = requestOptional.get();
		User user = (User) personRepository.findById(userId).get();
		return registerCompanyRequestMapper.toDto(request, user);
	}

	public CompanyDto registerCompany(PasswordDto passwordDto) {
		Optional<RegisterCompanyRequest> requestOptional = registerCompanyRequestRepository
				.findByIdAndApproved(passwordDto.getRequestId(), true);
		if (requestOptional.isEmpty()) {
			throw new EntityNotFound("Registration request with this id not found.");
		}
		RegisterCompanyRequest request = requestOptional.get();

		// new company
		User user = (User) personRepository.findById(request.getUserId()).get();
		Company newCompany = new Company(request.getName(), request.getDescription(), user);
		Company savedCompany = companyRepository.save(newCompany);
		
		user.setCompany(savedCompany);

		// user becomes agent
		user.setAuthorities(new ArrayList<>() {
			{
				add(authorityRepository.findByName("ROLE_AGENT"));
			}
		});
		User savedUser = userRepository.save(user);

		// register agent on dislinkt
		RestTemplate restTemplate = new RestTemplate();
		String createAgentUrl = authServiceApi + "/authentication/create-agent";
		AgentCreateRequest agentCreateRequest = new AgentCreateRequest(savedUser.getUsername(),
				passwordDto.getPassword(), savedUser.getEmail(), savedCompany.getName());
		ResponseEntity<PersonDto> createAgentResponse = restTemplate.postForEntity(createAgentUrl, agentCreateRequest,
				PersonDto.class);
		if (createAgentResponse.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
			registerCompanyRequestRepository.delete(request);
			throw new UsernameOrEmailAlreadyExists(
					"Your username or email already exists on dislinkt application. Please change it and create new company registration request.");
		}

		// delete request
		registerCompanyRequestRepository.delete(request);

		return companyMapper.toDto(savedCompany, savedUser);
	}

}
