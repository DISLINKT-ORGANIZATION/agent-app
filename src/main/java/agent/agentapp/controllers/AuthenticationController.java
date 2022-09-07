package agent.agentapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import agent.agentapp.dtos.LoginRequest;
import agent.agentapp.dtos.UserJwtToken;
import agent.agentapp.services.AuthenticationService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		UserJwtToken userToken = authenticationService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
		return ResponseEntity.ok(userToken);
	}

}
