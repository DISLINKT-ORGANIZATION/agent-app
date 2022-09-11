package agent.agentapp.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.SalaryDto;
import agent.agentapp.entities.Salary;
import agent.agentapp.entities.SeniorityLevel;


@Service
public class SalaryMapper {

	public SalaryDto toDto(Salary salary) {
		return new SalaryDto(salary.getId(), salary.getUserId(), salary.getSalaryValue(),
				salary.getSenioriyLevel().getValue(), salary.getJobPosition().getId());
	}

	public Salary toEntity(SalaryDto dto) {
		return new Salary(dto.getUserId(), dto.getSalaryValue(), SeniorityLevel.valueOfInt(dto.getSenioriyLevel()));
	}

	public List<SalaryDto> toCollectionDto(Collection<Salary> salaries) {
		return salaries.stream().map(this::toDto).collect(Collectors.toList());
	}

}
