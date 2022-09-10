package agent.agentapp.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.CompanyDto;
import agent.agentapp.entities.Company;
import agent.agentapp.entities.User;
import agent.agentapp.repositories.PersonRepository;

@Service
public class CompanyMapper {

	@Autowired
	private PersonRepository personRepository;

	public CompanyDto toDto(Company company, User user) {
		return new CompanyDto(company.getId(), company.getName(), company.getDescription(), user.getId(),
				user.getUsername());
	}
	
	//TODO: add lists
	public CompanyDto toDtoWithLists(Company company, User user) {
		return new CompanyDto(company.getId(), company.getName(), company.getDescription(), user.getId(),
				user.getUsername());
	}

	public List<CompanyDto> toListDto(List<Company> companies) {
		return companies.stream().map(company -> {
			return toDto(company, company.getUser());
		}).collect(Collectors.toList());
	}

}
