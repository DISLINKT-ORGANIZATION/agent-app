package agent.agentapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import agent.agentapp.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Optional<Company> findById(Long id);
	
	@Query("SELECT c FROM Company c WHERE c.user.id = ?1")
	Optional<Company> findByUserId(Long userId);

}