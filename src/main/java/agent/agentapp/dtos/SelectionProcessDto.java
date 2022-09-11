package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectionProcessDto {
	
	private Long id;
	private Long companyId;
	private Long userId;
	private String username;
	private String description;

}
