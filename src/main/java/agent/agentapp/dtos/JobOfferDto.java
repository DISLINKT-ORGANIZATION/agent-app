package agent.agentapp.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDto {

	private Long id;
	private String title;
	private String description;
	private String jobPositionName;
	private String companyName;
	private int seniorityLevel;
	private String companyLink;
	private String jobPositionLink;

	public JobOfferDto(String title, String description, String jobPositionName, String companyName, int seniorityLevel,
			String companyLink, String jobPositionLink) {
		super();
		this.title = title;
		this.description = description;
		this.jobPositionName = jobPositionName;
		this.companyName = companyName;
		this.seniorityLevel = seniorityLevel;
		this.companyLink = companyLink;
		this.jobPositionLink = jobPositionLink;
	}

}
