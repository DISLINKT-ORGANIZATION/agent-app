package agent.agentapp.services;

import agent.agentapp.dtos.UserJwtToken;

public interface AuthenticationService {

	UserJwtToken loginUser(String username, String password);

}
