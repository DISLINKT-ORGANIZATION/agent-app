package agent.agentapp.mappers;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.UserRegistrationRequest;
import agent.agentapp.entities.User;

@Service
public class UserDtoMapper {

	public PersonDto toDto(User user) {
		return new PersonDto(user.getId(), user.getUsername(), user.getEmail(),
				user.getAuthorities().get(0).getAuthority());
	}

	public User toEntity(UserRegistrationRequest request) {
		return new User(request.getEmail(), request.getUsername());
	}

	public PersonDto toDtoWithToken(User user, String token) {
		return new PersonDto(user.getId(), user.getUsername(), user.getEmail(),
				user.getAuthorities().get(0).getAuthority(), token);
	}
}
