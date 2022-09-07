package agent.agentapp.mappers;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.entities.User;

@Service
public class UserDtoMapper {

	public PersonDto toDto(User user) {
		return new PersonDto(user.getId(), user.getUsername(), user.getEmail(),
				user.getAuthorities().get(0).getAuthority());
	}
}
