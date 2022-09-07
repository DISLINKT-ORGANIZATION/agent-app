package agent.agentapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.UserJwtToken;
import agent.agentapp.entities.Administrator;
import agent.agentapp.entities.Person;
import agent.agentapp.entities.User;
import agent.agentapp.exceptions.InvalidUsername;
import agent.agentapp.mappers.AdministratorDtoMapper;
import agent.agentapp.mappers.UserDtoMapper;
import agent.agentapp.repositories.AdministratorRepository;
import agent.agentapp.repositories.AuthorityRepository;
import agent.agentapp.repositories.PersonRepository;
import agent.agentapp.repositories.UserRepository;
import agent.agentapp.security.TokenUtils;
import agent.agentapp.services.AuthenticationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserDtoMapper userMapper;

    @Autowired
    private AdministratorDtoMapper administratorMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    public UserJwtToken loginUser(String username, String password) {
        PersonDto person = getPersonByUsername(username);
        if (person == null) {
            throw new InvalidUsername("Invalid credentials");
        }
        return generateTokenResponse(username, password);
    }

    public PersonDto getPersonByUsername(String username) {
        Administrator admin = administratorRepository.findByUsername(username);
        if (admin != null) {
            return administratorMapper.toDto(admin);
        }
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return userMapper.toDto(user);
        }
        return null;
    }

    private UserJwtToken generateTokenResponse(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // create token
        Person user = (Person) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        PersonDto person = getPersonByUsername(user.getUsername());

        return new UserJwtToken(jwt, person.getId(), person.getRole());
    }
}