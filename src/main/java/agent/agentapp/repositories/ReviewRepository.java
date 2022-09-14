package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
	Optional<Review> findById(Long id);
	
	@Query("SELECT r FROM Review r WHERE r.userId = ?1 AND r.company.id = ?2")
	Review findByUserIdAndCompanyId(Long userId, Long companyId);

}