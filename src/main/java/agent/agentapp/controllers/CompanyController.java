package agent.agentapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agent.agentapp.dtos.CommentDto;
import agent.agentapp.dtos.CompanyDto;
import agent.agentapp.dtos.CreateRegisterCompanyRequestDto;
import agent.agentapp.dtos.PasswordDto;
import agent.agentapp.dtos.RegisterCompanyRequestDto;
import agent.agentapp.dtos.ReviewDto;
import agent.agentapp.dtos.SelectionProcessDto;
import agent.agentapp.dtos.UpdateCompanyDto;
import agent.agentapp.services.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/request-registration")
	public ResponseEntity<?> requestRegistration(@RequestBody CreateRegisterCompanyRequestDto registerCompanyRequest) {
		RegisterCompanyRequestDto dto = companyService.requestRegistration(registerCompanyRequest);
		return ResponseEntity.ok(dto);
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<?> getCompany(@PathVariable Long companyId) {
		CompanyDto dto = companyService.getCompany(companyId);
		return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasRole('ROLE_AGENT')")
	@GetMapping("/by-agent/{userId}")
	public ResponseEntity<?> getCompanyByAgent(@PathVariable Long userId) {
		CompanyDto dto = companyService.getCompanyByAgent(userId);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_AGENT', 'ROLE_USER')")
	@GetMapping("")
	public ResponseEntity<?> getCompanies() {
		List<CompanyDto> dtos = companyService.getCompanies();
		return ResponseEntity.ok(dtos);
	}

	@PreAuthorize("hasRole('ROLE_AGENT')")
	@PutMapping("")
	public ResponseEntity<?> updateCompany(@RequestBody UpdateCompanyDto updateDto) {
		CompanyDto dto = companyService.updateCompany(updateDto);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@GetMapping("/registration-requests")
	public ResponseEntity<?> getRegisterCompanyRequests() {
		List<RegisterCompanyRequestDto> dtos = companyService.getRegisterCompanyRequests();
		return ResponseEntity.ok(dtos);
	}

	@PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
	@GetMapping("/approve-request/{requestId}")
	public ResponseEntity<?> approveRequest(@PathVariable Long requestId) {
		Boolean approved = companyService.approveRequest(requestId);
		return ResponseEntity.ok(approved);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/approved-request/{userId}")
	public ResponseEntity<?> getApprovedRequestForUser(@PathVariable Long userId) {
		RegisterCompanyRequestDto dto = companyService.getApprovedRequestForUser(userId);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/register-company")
	public ResponseEntity<?> registerCompany(@RequestBody PasswordDto passwordDto) {
		CompanyDto dto = companyService.registerCompany(passwordDto);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/review")
	public ResponseEntity<?> review(@RequestBody ReviewDto reviewDto) {
		ReviewDto dto = companyService.addReview(reviewDto);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/comment")
	public ResponseEntity<?> comment(@RequestBody CommentDto commentDto) {
		CommentDto dto = companyService.addComment(commentDto);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/selection-process")
	public ResponseEntity<?> selectionProcess(@RequestBody SelectionProcessDto selectionProcessDto) {
		SelectionProcessDto dto = companyService.addSelectionProcess(selectionProcessDto);
		return ResponseEntity.ok(dto);
	}

}
