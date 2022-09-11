package agent.agentapp.services;

import java.util.List;

import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.dtos.SalaryDto;

public interface JobPositionService {

	List<JobPositionDto> getJobPositions();
	
	JobPositionDto getJobPosition(Long id);
	
	SalaryDto addSalary(SalaryDto dto);
}
