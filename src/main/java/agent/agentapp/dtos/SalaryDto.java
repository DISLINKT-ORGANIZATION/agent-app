package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDto {

	private Long id;
	private Long userId;
	private int salaryValue;
	private int senioriyLevel;
	private Long jobPositionId;
}
