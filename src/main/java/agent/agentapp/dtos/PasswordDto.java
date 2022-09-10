package agent.agentapp.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDto {
	
	private Long requestId;
	
	@NotBlank(message = "Password should not be empty")
	@Size(min = 5, max = 250, message = "Password should be at least 5 characters long")
	private String password;

}
