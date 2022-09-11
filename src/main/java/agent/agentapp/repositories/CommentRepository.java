package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	Optional<Comment> findById(Long id);

}