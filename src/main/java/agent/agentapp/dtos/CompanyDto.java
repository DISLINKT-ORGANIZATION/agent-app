package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

	private Long id;
	private String name;
	private String description;
	private Long userId;
	private String username;
	
	//TODO: add lists
}
