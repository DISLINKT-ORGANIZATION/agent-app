package agent.agentapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {

	Administrator findByUsername(String username);
	
	Administrator findByEmail(String email);

}