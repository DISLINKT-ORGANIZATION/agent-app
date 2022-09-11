package agent.agentapp.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.entities.JobPosition;

@Service
public class JobPositionMapper {

	@Autowired
	private SalaryMapper salaryMapper;

	@Autowired
	private SkillMapper skillMapper;

	public JobPositionDto toDto(JobPosition jobPosition) {
		return new JobPositionDto(jobPosition.getId(), jobPosition.getTitle(),
				skillMapper.toCollectionDto(jobPosition.getSkills()),
				salaryMapper.toCollectionDto(jobPosition.getSalaries()));
	}

	public List<JobPositionDto> toCollectionDto(Collection<JobPosition> jobPositions) {
		return jobPositions.stream().map(this::toDto).collect(Collectors.toList());
	}
}
