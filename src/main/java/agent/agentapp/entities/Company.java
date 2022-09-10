package agent.agentapp.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", unique = false, nullable = false)
	@NonNull
	private String name;

	@Column(name = "description", unique = false, nullable = false)
	@NonNull
	private String description;

	@OneToMany(mappedBy = "company")
	private List<Comment> comments;

	@OneToMany(mappedBy = "company")
	private List<Review> reviews;

	@OneToMany(mappedBy = "company")
	private List<SelectionProcess> selectionProcesses;

	@OneToMany(mappedBy = "company")
	private List<JobPosition> jobPositions;

	@OneToOne
	private User user;

	public Company(String name, String description, User user) {
		this.name = name;
		this.description = description;
		this.user = user;
		this.comments = new ArrayList<Comment>();
		this.reviews = new ArrayList<Review>();
		this.selectionProcesses = new ArrayList<SelectionProcess>();
		this.jobPositions = new ArrayList<JobPosition>();
	}

}
