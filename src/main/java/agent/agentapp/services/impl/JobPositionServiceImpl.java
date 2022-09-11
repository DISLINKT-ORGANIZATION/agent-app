package agent.agentapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import agent.agentapp.dtos.CreateJobOfferDto;
import agent.agentapp.dtos.JobOfferDto;
import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.dtos.SalaryDto;
import agent.agentapp.entities.Company;
import agent.agentapp.entities.JobPosition;
import agent.agentapp.entities.Salary;
import agent.agentapp.exceptions.EntityNotFound;
import agent.agentapp.exceptions.ServerException;
import agent.agentapp.mappers.JobPositionMapper;
import agent.agentapp.mappers.SalaryMapper;
import agent.agentapp.repositories.CompanyRepository;
import agent.agentapp.repositories.JobPositionRepository;
import agent.agentapp.repositories.SalaryRepository;
import agent.agentapp.services.JobPositionService;

@Service
public class JobPositionServiceImpl implements JobPositionService {

	@Value("${cors.origin}")
	private String frontendAppURL;

	@Value("${dislinkt.job-offer-service.api}")
	private String jobOfferServiceApi;

	@Autowired
	private JobPositionRepository jobPositionRepository;

	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private JobPositionMapper jobPositionMapper;

	@Autowired
	private SalaryMapper salaryMapper;

	public List<JobPositionDto> getJobPositions() {
		return jobPositionMapper.toCollectionDto(jobPositionRepository.findAll());
	}

	public JobPositionDto getJobPosition(Long id) {
		Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(id);
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Job position not found.");
		}
		return jobPositionMapper.toDto(jobPositionOptional.get());
	}

	public SalaryDto addSalary(SalaryDto dto) {
		Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(dto.getJobPositionId());
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Job position not found.");
		}
		JobPosition jobPosition = jobPositionOptional.get();
		Salary newSalary = salaryMapper.toEntity(dto);
		newSalary.setJobPosition(jobPosition);
		jobPosition.getSalaries().add(newSalary);
		jobPositionRepository.save(jobPosition);
		Salary savedSalary = salaryRepository.save(newSalary);
		return salaryMapper.toDto(savedSalary);
	}

	public JobOfferDto createJobOffer(CreateJobOfferDto jobOfferDto, String token) {
		Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(jobOfferDto.getJobPositionId());
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Job position not found.");
		}
		String jobPositionName = jobPositionOptional.get().getTitle();
		String jobPositionLink = frontendAppURL + "/job-position/" + jobOfferDto.getJobPositionId();
		Optional<Company> companyOptional = companyRepository.findByUserId(jobOfferDto.getUserId());
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Company not found.");
		}
		String companyName = companyOptional.get().getName();
		String companyLink = frontendAppURL + "/company/" + companyOptional.get().getId();

		JobOfferDto jobOffer = new JobOfferDto(jobOfferDto.getTitle(), jobOfferDto.getDescription(), jobPositionName,
				companyName, jobOfferDto.getSeniorityLevel(), jobPositionLink, companyLink);

		RestTemplate restTemplate = new RestTemplate();
		String createJobOfferUrl = jobOfferServiceApi + "/job-offer";
		System.out.println(createJobOfferUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		HttpEntity<JobOfferDto> entity = new HttpEntity<JobOfferDto>(jobOffer, headers);
		ResponseEntity<JobOfferDto> createJobOfferResponse = restTemplate.postForEntity(createJobOfferUrl, entity,
				JobOfferDto.class);
		if (!createJobOfferResponse.getStatusCode().equals(HttpStatus.OK)) {
			throw new ServerException("Service is unavailable. Please create new company registration request.");
		}
		return createJobOfferResponse.getBody();
	}
}
