package agent.agentapp.mappers;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.entities.Administrator;

@Service
public class AdministratorDtoMapper {

	public PersonDto toDto(Administrator administrator) {
		return new PersonDto(administrator.getId(), administrator.getUsername(), administrator.getEmail(),
				administrator.getAuthorities().get(0).getAuthority());
	}
	
	public PersonDto toDtoWithToken(Administrator administrator, String token) {
		return new PersonDto(administrator.getId(), administrator.getUsername(), administrator.getEmail(),
				administrator.getAuthorities().get(0).getAuthority(), token);
    }
}
