package it.live.educationtest.mapper;

import it.live.educationtest.entity.Contest;
import it.live.educationtest.payload.ContestDTO;
import it.live.educationtest.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContestMapper {
    private final GroupRepository groupRepository;

    public Contest toEntity(ContestDTO contestDTO) {
        return Contest.builder()
                .title(contestDTO.getTitle())
                .secretKey(contestDTO.getSecretKey())
                .isValid(contestDTO.getIsValid())
                .timeExpired(contestDTO.getTimeExpired())
                .group(groupRepository.findById(contestDTO.getGroupId()).get())
                .build();

    }

    public Contest toEntity(ContestDTO contestDTO, Contest contest) {
        contest.setTitle(contestDTO.getTitle());
        contest.setIsValid(contestDTO.getIsValid());
        contest.setSecretKey(contestDTO.getSecretKey());
        contest.setTimeExpired(contestDTO.getTimeExpired());
        contest.setGroup(groupRepository.findById(contestDTO.getGroupId()).get());
        return contest;
    }

    public ContestDTO toDTO(Contest contest) {
        return ContestDTO
                .builder()
                .title(contest.getTitle())
                .groupId(contest.getGroup().getId())
                .isValid(contest.getIsValid())
                .id(contest.getId())
                .secretKey(contest.getSecretKey())
                .timeExpired(contest.getTimeExpired())
                .build();
    }
}
