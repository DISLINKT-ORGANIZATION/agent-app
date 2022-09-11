package agent.agentapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.dtos.SalaryDto;
import agent.agentapp.entities.JobPosition;
import agent.agentapp.entities.Salary;
import agent.agentapp.exceptions.EntityNotFound;
import agent.agentapp.mappers.JobPositionMapper;
import agent.agentapp.mappers.SalaryMapper;
import agent.agentapp.repositories.JobPositionRepository;
import agent.agentapp.repositories.SalaryRepository;
import agent.agentapp.services.JobPositionService;

@Service
public class JobPositionServiceImpl implements JobPositionService {

	@Autowired
	private JobPositionRepository jobPositionRepository;

	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	private JobPositionMapper jobPositionMapper;

	@Autowired
	private SalaryMapper salaryMapper;

	public List<JobPositionDto> getJobPositions() {
		return jobPositionMapper.toCollectionDto(jobPositionRepository.findAll());
	}

	public JobPositionDto getJobPosition(Long id) {
		Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(id);
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Job position not found.");
		}
		return jobPositionMapper.toDto(jobPositionOptional.get());
	}

	public SalaryDto addSalary(SalaryDto dto) {
		Optional<JobPosition> jobPositionOptional = jobPositionRepository.findById(dto.getJobPositionId());
		if (jobPositionOptional.isEmpty()) {
			throw new EntityNotFound("Job position not found.");
		}
		JobPosition jobPosition = jobPositionOptional.get();
		Salary newSalary = salaryMapper.toEntity(dto);
		newSalary.setJobPosition(jobPosition);
		jobPosition.getSalaries().add(newSalary);
		jobPositionRepository.save(jobPosition);
		Salary savedSalary = salaryRepository.save(newSalary);
		return salaryMapper.toDto(savedSalary);
	}
}
