package agent.agentapp.mappers;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.ReviewDto;
import agent.agentapp.entities.Company;
import agent.agentapp.entities.Review;

@Service
public class ReviewMapper {

	public ReviewDto toDto(Review review) {
		return new ReviewDto(review.getId(), review.getCompany().getId(), review.getUserId(), review.getReviewValue());
	}

	public Review toEntity(ReviewDto dto, Company company) {
		return new Review(dto.getUserId(), dto.getReviewValue(), company);
	}
}
