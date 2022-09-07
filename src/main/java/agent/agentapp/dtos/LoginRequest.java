package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Username should not be empty")
	@Size(max = 250, message = "Username should not be too long")
	private String username;

	@NotBlank(message = "Password should not be empty")
	@Size(min = 5, max = 250, message = "Password should be at least 5 characters long")
	private String password;
}