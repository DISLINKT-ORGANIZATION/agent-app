package agent.agentapp.services;

import java.util.List;

import agent.agentapp.dtos.CreateJobOfferDto;
import agent.agentapp.dtos.JobOfferDto;
import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.dtos.SalaryDto;

public interface JobPositionService {

	List<JobPositionDto> getJobPositions();
	
	JobPositionDto getJobPosition(Long id);
	
	SalaryDto addSalary(SalaryDto dto);
	
	JobOfferDto createJobOffer(CreateJobOfferDto jobOfferDto, String token);
}
