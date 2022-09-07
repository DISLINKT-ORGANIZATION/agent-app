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
@Table(name = "salaries")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Salary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id", unique = false, nullable = false)
	@NonNull
	private Long userId;

	@Column(name = "salary_value", unique = false, nullable = false)
	private int salaryValue;
	
	@ManyToOne
	private JobPosition jobPosition;

}
