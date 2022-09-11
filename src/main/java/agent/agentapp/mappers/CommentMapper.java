package agent.agentapp.mappers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agent.agentapp.dtos.CommentDto;
import agent.agentapp.entities.Comment;
import agent.agentapp.entities.User;
import agent.agentapp.exceptions.EntityNotFound;
import agent.agentapp.repositories.UserRepository;

@Service
public class CommentMapper {

	@Autowired
	private UserRepository userRepository;

	public CommentDto toDto(Comment comment, User user) {
		return new CommentDto(comment.getId(), comment.getCompany().getId(), comment.getUserId(), user.getUsername(),
				comment.getContent());
	}

	public Comment toEntity(CommentDto dto) {
		return new Comment(dto.getUserId(), dto.getContent());
	}

	public List<CommentDto> toListDto(List<Comment> comments) {
		return comments.stream().map(comment -> {
			Optional<User> userOptional = userRepository.findById(comment.getUserId());
			if (userOptional.isEmpty()) {
				throw new EntityNotFound("User not found.");
			}
			return toDto(comment, userOptional.get());
		}).collect(Collectors.toList());
	}
}
