package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	Optional<Person> findById(Long id);

	Person findByUsername(String username);

	Person findByEmail(String email);

	Person findByUsernameIgnoreCase(String username);

	Person findByEmailIgnoreCase(String email);
}