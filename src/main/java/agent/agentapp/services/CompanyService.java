package agent.agentapp.services;

import java.util.List;

import agent.agentapp.dtos.CommentDto;
import agent.agentapp.dtos.CompanyDto;
import agent.agentapp.dtos.CreateRegisterCompanyRequestDto;
import agent.agentapp.dtos.PasswordDto;
import agent.agentapp.dtos.RegisterCompanyRequestDto;
import agent.agentapp.dtos.ReviewDto;
import agent.agentapp.dtos.SelectionProcessDto;
import agent.agentapp.dtos.UpdateCompanyDto;

public interface CompanyService {

	CompanyDto getCompany(Long companyId);
	
	CompanyDto getCompanyByAgent(Long userId);
	
	List<CompanyDto> getCompanies();
	
	CompanyDto updateCompany(UpdateCompanyDto dto);
	
	RegisterCompanyRequestDto requestRegistration(CreateRegisterCompanyRequestDto registerCompanyRequestDto);
	
	List<RegisterCompanyRequestDto> getRegisterCompanyRequests();
	
	Boolean approveRequest(Long requestId);
	
	RegisterCompanyRequestDto getApprovedRequestForUser(Long userId);
	
	CompanyDto registerCompany(PasswordDto passwordDto);
	
	ReviewDto addReview(ReviewDto dto);
	
	CommentDto addComment(CommentDto dto);
	
	SelectionProcessDto addSelectionProcess(SelectionProcessDto dto);

}
