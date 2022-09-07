package agent.agentapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import agent.agentapp.repositories.AdministratorRepository;
import agent.agentapp.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails person = administratorRepository.findByUsername(username);
		if (person != null) {
			return person;
		}
		person = userRepository.findByUsername(username);
		if (person != null) {
			return person;
		} else {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		}
	}
}