package agent.agentapp.services;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.UserJwtToken;
import agent.agentapp.dtos.UserRegistrationRequest;

public interface AuthenticationService {

	UserJwtToken loginUser(String username, String password);
	
	PersonDto registerUser(UserRegistrationRequest request);
	
	PersonDto updatePerson(PersonDto updateDto);
	
	PersonDto getPersonById(Long id);

}
