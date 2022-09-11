package agent.agentapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobOfferDto {

	private String title;
	private String description;
	private Long jobPositionId;
	private Long userId;
	private int seniorityLevel;
}
