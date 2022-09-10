package agent.agentapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import agent.agentapp.dtos.LoginRequest;
import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.UserJwtToken;
import agent.agentapp.dtos.UserRegistrationRequest;
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
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest) {
		PersonDto userDto = authenticationService.registerUser(userRegistrationRequest);
		return ResponseEntity.ok(userDto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_AGENT', 'ROLE_ADMINISTRATOR')")
	@PutMapping("/users/update-person")
	public ResponseEntity<?> updatePerson(@RequestBody PersonDto updateDto) {
		PersonDto userDto = authenticationService.updatePerson(updateDto);
		return ResponseEntity.ok(userDto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_AGENT', 'ROLE_ADMINISTRATOR')")
	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Long id) {
		PersonDto userDto = authenticationService.getPersonById(id);
		return ResponseEntity.ok(userDto);
	}

}
