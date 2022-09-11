package agent.agentapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import agent.agentapp.dtos.JobPositionDto;
import agent.agentapp.dtos.SalaryDto;
import agent.agentapp.services.JobPositionService;

@RestController
@RequestMapping("/job-position")
public class JobPositionController {

	@Autowired
	private JobPositionService jobPositionService;

	@GetMapping("/{jobPositionId}")
	public ResponseEntity<?> getJobPosition(@PathVariable Long jobPositionId) {
		JobPositionDto dto = jobPositionService.getJobPosition(jobPositionId);
		return ResponseEntity.ok(dto);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_AGENT', 'ROLE_USER')")
	@GetMapping("")
	public ResponseEntity<?> getJobPositions() {
		List<JobPositionDto> dtos = jobPositionService.getJobPositions();
		return ResponseEntity.ok(dtos);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/salary")
	public ResponseEntity<?> addSalary(@RequestBody SalaryDto salaryDto) {
		SalaryDto dto = jobPositionService.addSalary(salaryDto);
		return ResponseEntity.ok(dto);
	}
}
