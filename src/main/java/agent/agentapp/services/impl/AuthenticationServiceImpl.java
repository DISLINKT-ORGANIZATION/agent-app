package agent.agentapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.PersonDto;
import agent.agentapp.dtos.UserJwtToken;
import agent.agentapp.dtos.UserRegistrationRequest;
import agent.agentapp.entities.Administrator;
import agent.agentapp.entities.Person;
import agent.agentapp.entities.User;
import agent.agentapp.exceptions.EmailAlreadyExists;
import agent.agentapp.exceptions.InvalidUsername;
import agent.agentapp.exceptions.UsernameAlreadyExists;
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
import java.util.stream.Collectors;

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
    
    public PersonDto registerUser(UserRegistrationRequest request) {

        checkUsername(request.getUsername());
        checkEmail(request.getEmail());

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(new ArrayList<>() {
            {
                add(authorityRepository.findByName("ROLE_USER"));
            }
        });

        User newUser = userRepository.save(user);

        return userMapper.toDto(newUser);
    }
    
    public PersonDto updatePerson(PersonDto updateDto) {
        Optional<Administrator> adminOptional = administratorRepository.findById(updateDto.getId());
        if (adminOptional.isPresent()) {
            Administrator admin = adminOptional.get();
            HashMap<String, Object> params = updatePerson(admin, updateDto);
            Administrator updatedAdmin = (Administrator) params.get("updatedPerson");
            String token = (String) params.get("token");
            updatedAdmin = administratorRepository.save(updatedAdmin);
            return administratorMapper.toDtoWithToken(updatedAdmin, token);
        }
        Optional<User> userOptional = userRepository.findById(updateDto.getId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            HashMap<String, Object> params = updatePerson(user, updateDto);
            User updatedUser = (User) params.get("updatedPerson");
            String token = (String) params.get("token");
            updatedUser = userRepository.save(updatedUser);
            return userMapper.toDtoWithToken(updatedUser, token);
        }
        return null;
    }
    
    private HashMap<String, Object> updatePerson(Person person, PersonDto updateDto) {
        String token = "";
        if (!person.getUsername().equalsIgnoreCase(updateDto.getUsername())) {
            checkUsername(updateDto.getUsername());
            token = tokenUtils.generateToken(
                    updateDto.getUsername(), person.getId() + "",
                    person.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        }
        if (!person.getEmail().equalsIgnoreCase(updateDto.getEmail())) {
            checkEmail(updateDto.getEmail());
        }
        person.setUsername(updateDto.getUsername());
        person.setEmail(updateDto.getEmail());
        HashMap<String, Object> response = new HashMap<>();
        response.put("updatedPerson", person);
        response.put("token", token);
        return response;
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
    
    public PersonDto getPersonById(Long id) {
        Optional<Administrator> admin = administratorRepository.findById(id);
        if (admin.isPresent()) {
            return administratorMapper.toDto(admin.get());
        }
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toDto(user.get());
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
        String jwt = tokenUtils.generateToken(
                user.getUsername(), user.getId() + "",
                user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        PersonDto person = getPersonByUsername(user.getUsername());

        return new UserJwtToken(jwt, person.getId(), person.getRole());
    }
    
    public void checkUsername(String username) {
        boolean usernameExists = checkIfUsernameExists(username);
        if (usernameExists) {
            throw new UsernameAlreadyExists("Username already exists.");
        }
    }

    public void checkEmail(String email) {
        boolean emailExists = checkIfEmailExists(email);
        if (emailExists) {
            throw new EmailAlreadyExists("Email already exists.");
        }
    }
    
    public boolean checkIfUsernameExists(String username) {
        Person person = personRepository.findByUsernameIgnoreCase(username);
        return person != null;
    }
    
    private boolean checkIfEmailExists(String email) {
        Person person = personRepository.findByEmailIgnoreCase(email);
        return person != null;
    }
}