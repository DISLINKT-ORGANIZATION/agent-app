package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.SelectionProcess;

@Repository
public interface SelectionProcessRepository extends JpaRepository<SelectionProcess, Long> {
	
	Optional<SelectionProcess> findById(Long id);

}