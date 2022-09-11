package agent.agentapp.mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import agent.agentapp.dtos.SkillDto;
import agent.agentapp.entities.Skill;

@Service
public class SkillMapper {

	public SkillDto toDto(Skill skill) {
		return new SkillDto(skill.getId(), skill.getName(), skill.getType().getValue());
	}

	public List<SkillDto> toCollectionDto(Collection<Skill> skills) {
		return skills.stream().map(this::toDto).collect(Collectors.toList());
	}
}
