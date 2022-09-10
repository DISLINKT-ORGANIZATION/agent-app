package agent.agentapp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "companies_requests")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RegisterCompanyRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	@NonNull
	private String name;

	@Column(name = "description", unique = false, nullable = false)
	@NonNull
	private String description;

	@Column(name = "user_id", unique = false, nullable = false)
	private Long userId;

	@Column(name = "approved", unique = false, nullable = false)
	private Boolean approved;

	public RegisterCompanyRequest(@NonNull String name, @NonNull String description, Long userId) {
		super();
		this.name = name;
		this.description = description;
		this.userId = userId;
		this.approved = false;
	}

}
