package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCompanyRequestDto {

	private Long id;
	private Long userId;
	private String username;
	private String name;
	private String description;
}
