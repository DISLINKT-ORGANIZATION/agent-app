package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
	
	Optional<Salary> findById(Long id);

}