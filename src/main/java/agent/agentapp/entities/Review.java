package agent.agentapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", unique = false, nullable = false)
	@NonNull
	private Long userId;

	@Column(name = "review_value", unique = false, nullable = false)
	private int reviewValue;
	
	@ManyToOne
	private Company company;

	public Review(@NonNull Long userId, int reviewValue, Company company) {
		super();
		this.userId = userId;
		this.reviewValue = reviewValue;
		this.company = company;
	}

}
