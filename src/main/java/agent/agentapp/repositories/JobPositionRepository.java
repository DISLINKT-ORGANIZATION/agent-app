package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.JobPosition;

@Repository
public interface JobPositionRepository extends JpaRepository<JobPosition, Long> {
	
	Optional<JobPosition> findById(Long id);

}