package agent.agentapp.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.SelectionProcessDto;
import agent.agentapp.entities.SelectionProcess;
import agent.agentapp.entities.User;
import agent.agentapp.exceptions.EntityNotFound;
import agent.agentapp.repositories.UserRepository;

import java.util.Optional;

@Service
public class SelectionProcessMapper {

	@Autowired
	private UserRepository userRepository;

	public SelectionProcessDto toDto(SelectionProcess process, User user) {
		return new SelectionProcessDto(process.getId(), process.getCompany().getId(), process.getUserId(),
				user.getUsername(), process.getDescription());
	}

	public SelectionProcess toEntity(SelectionProcessDto dto) {
		return new SelectionProcess(dto.getUserId(), dto.getDescription());
	}

	public List<SelectionProcessDto> toListDto(List<SelectionProcess> processes) {
		return processes.stream().map(process -> {
			Optional<User> userOptional = userRepository.findById(process.getUserId());
			if (userOptional.isEmpty()) {
				throw new EntityNotFound("User not found.");
			}
			return toDto(process, userOptional.get());
		}).collect(Collectors.toList());
	}
}
