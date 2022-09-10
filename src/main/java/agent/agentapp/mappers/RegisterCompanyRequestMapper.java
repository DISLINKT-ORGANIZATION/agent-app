package agent.agentapp.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.CreateRegisterCompanyRequestDto;
import agent.agentapp.dtos.RegisterCompanyRequestDto;
import agent.agentapp.entities.RegisterCompanyRequest;
import agent.agentapp.entities.User;
import agent.agentapp.repositories.PersonRepository;

@Service
public class RegisterCompanyRequestMapper {

	@Autowired
	private PersonRepository personRepository;

	public RegisterCompanyRequestDto toDto(RegisterCompanyRequest request, User user) {
		return new RegisterCompanyRequestDto(request.getId(), user.getId(), user.getUsername(), request.getName(),
				request.getDescription());
	}

	public RegisterCompanyRequest toEntity(CreateRegisterCompanyRequestDto dto) {
		return new RegisterCompanyRequest(dto.getName(), dto.getDescription(), dto.getUserId());
	}

	public List<RegisterCompanyRequestDto> toListDto(List<RegisterCompanyRequest> requests) {
		return requests.stream().map(request -> {
			User user = (User) personRepository.findById(request.getUserId()).get();
			return toDto(request, user);
		}).collect(Collectors.toList());
	}

}
