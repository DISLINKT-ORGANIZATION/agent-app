package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRegisterCompanyRequestDto {
	
	private Long userId;
	private String name;
	private String description;
}
