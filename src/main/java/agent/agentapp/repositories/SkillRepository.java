package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
	
	Optional<Skill> findById(Long id);

}