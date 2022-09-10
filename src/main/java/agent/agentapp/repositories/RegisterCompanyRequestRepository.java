package agent.agentapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agent.agentapp.entities.RegisterCompanyRequest;

@Repository
public interface RegisterCompanyRequestRepository extends JpaRepository<RegisterCompanyRequest, Long> {
	
	Optional<RegisterCompanyRequest> findById(Long id);
	
	Optional<RegisterCompanyRequest> findByIdAndApproved(Long id, Boolean approved);
	
	Optional<RegisterCompanyRequest> findByUserIdAndApproved(Long userId, Boolean approved);
	
	List<RegisterCompanyRequest> findByApproved(boolean approved);
	
	Optional<RegisterCompanyRequest> findByUserId(Long userId);
	
}