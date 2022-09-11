package agent.agentapp.dtos;

import java.util.List;

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
	private double reviewsRating;
	
	private ReviewDto currentUserReview;
	private List<CommentDto> comments;
	private List<SelectionProcessDto> selectionProcesses;
	
	public CompanyDto(Long id, String name, String description, Long userId, String username, double reviewsRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.username = username;
		this.reviewsRating = reviewsRating;
	}

	public CompanyDto(Long id, String name, String description, Long userId, String username, double reviewsRating,
			List<CommentDto> comments, List<SelectionProcessDto> selectionProcesses) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.username = username;
		this.reviewsRating = reviewsRating;
		this.comments = comments;
		this.selectionProcesses = selectionProcesses;
	}
	
	

}
