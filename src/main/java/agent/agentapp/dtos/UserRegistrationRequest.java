package agent.agentapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

	@NotBlank(message = "Username should not be empty")
	@Size(max = 250, message = "Username should not be too long")
	private String username;

	@NotBlank(message = "Password should not be empty")
	@Size(min = 5, max = 250, message = "Password should be at least 5 characters long")
	private String password;

	@NotBlank(message = "Email should not be empty")
	@Size(max = 50, message = "Username should not be too long")
	@Pattern(regexp = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}", message = "Email should be in a valid format")
	private String email;
}