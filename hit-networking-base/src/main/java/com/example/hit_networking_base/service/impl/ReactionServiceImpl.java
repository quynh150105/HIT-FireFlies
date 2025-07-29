package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.EmotionType;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.ReactionRequestDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionListResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;
import com.example.hit_networking_base.domain.entity.Event;
import com.example.hit_networking_base.domain.entity.JobPost;
import com.example.hit_networking_base.domain.entity.Reaction;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.EventRepository;
import com.example.hit_networking_base.repository.JobPostRepository;
import com.example.hit_networking_base.repository.ReactionRepository;
import com.example.hit_networking_base.service.ReactionService;
import com.example.hit_networking_base.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserMapper userMapper;
    private final EventRepository eventRepository;
    private final UserService userService;

    private final JobPostRepository jobPostRepository;

    @Override
    public List<ReactionResponseDTO> findReactionByTargetIdAndTargetType(long targetId, TargetType targetType) {
        return reactionRepository.findByTargetIdAndTargetType(targetId, targetType).stream()
                .map(reaction -> {
                    ReactionResponseDTO reactionResponseDTO = new ReactionResponseDTO();
                    reactionResponseDTO.setEmotionType(reaction.getEmotionType());
                    reactionResponseDTO.setCreatedAt(reaction.getCreatedAt());
                    reactionResponseDTO.setUserPostResponseDTO(userMapper.toUserPostResponseDTO(reaction.getUser()));
                    return reactionResponseDTO;
                })
                .collect(Collectors.toList());
    }

    // create
    @Override
    public String createReaction(ReactionRequestDTO request) {
            User user = userService.checkToken();
            boolean exists = reactionRepository
                    .findByUserIdAndTarget(request.getTargetId(), request.getTargetType(), user.getUserId())
                    .isPresent();
            if (exists) {
                throw new UserException("User has already reacted to this target");
            }
            Reaction reaction = new Reaction();
            reaction.setUser(user);
            reaction.setTargetId(request.getTargetId());
            reaction.setTargetType(request.getTargetType());
            reaction.setEmotionType(request.getEmotionType());
            reaction.setCreatedAt(LocalDateTime.now());
            increaseReactionCount(request.getTargetType(), request.getTargetId());
            reactionRepository.save(reaction);
            return "SUCCESS";
    }

    @Override
    public String removeReaction(Long targetId, TargetType targetType) {
        User user = userService.checkToken();

        Reaction reaction = reactionRepository
                .findByUserIdAndTarget(targetId, targetType, user.getUserId())
                .orElseThrow(() -> new NotFoundException("Reaction not found"));

        reactionRepository.delete(reaction);
        decreaseReactionCount(targetType, targetId);

        return "SUCCESS";
    }

    @Override
    public ReactionListResponseDTO getReaction(Long targetId, TargetType targetType) {
        List<Object[]> result = reactionRepository.countReactionsByType(targetId, targetType);

        Map<EmotionType, Long> reactionMap = new EnumMap<EmotionType, Long>(EmotionType.class);
        long total = 0;

        for(EmotionType type: EmotionType.values()){
            reactionMap.put(type,0L);
        }

        for(Object[] row : result){
            EmotionType emotion = (EmotionType) row[0];
            Long count = (Long) row[1];
            reactionMap.put(emotion,count);
            total +=count;
        }
        return new ReactionListResponseDTO(total,reactionMap);
    }
    @Override
    public String updateReaction(ReactionRequestDTO requestDTO) {
        User user = userService.checkToken();

        Reaction reaction = reactionRepository
                .findByUserIdAndTarget(requestDTO.getTargetId(), requestDTO.getTargetType(), user.getUserId())
                .orElseThrow(() -> new NotFoundException("Reaction not found"));

        EmotionType oldEmotion = reaction.getEmotionType();
        EmotionType newEmotion = requestDTO.getEmotionType();

        if (oldEmotion != newEmotion) {
            reaction.setEmotionType(newEmotion);
            reaction.setCreatedAt(LocalDateTime.now());
            reactionRepository.save(reaction);
        }

        return "SUCCESS";
    }

    private void increaseReactionCount(TargetType targetType, Long targetId) {
        switch (targetType) {
            case EVENT:
                Event event = eventRepository.findByEventIdAndDeletedAtIsNull(targetId)
                        .orElseThrow(() -> new NotFoundException("Event not found"));
                event.setCountReaction(event.getCountReaction() + 1);
                break;
            case JOB:
                JobPost job = jobPostRepository.findByPostIdAndDeletedAtIsNull(targetId)
                        .orElseThrow(() -> new NotFoundException("JobPost not found"));
                job.setCountReaction(job.getCountReaction() + 1);
                break;

            default:
                throw new UnsupportedOperationException("Unsupported TargetType: " + targetType);
        }
    }

    private void decreaseReactionCount(TargetType targetType, Long targetId) {
        switch (targetType) {
            case EVENT:
                Event event = eventRepository.findByEventIdAndDeletedAtIsNull(targetId)
                        .orElseThrow(() -> new NotFoundException("Event not found"));
                event.setCountReaction(event.getCountReaction() - 1);
                break;
            case JOB:
                JobPost job = jobPostRepository.findByPostIdAndDeletedAtIsNull(targetId)
                        .orElseThrow(() -> new NotFoundException("JobPost not found"));
                job.setCountReaction(job.getCountReaction() - 1);
                break;
            default:
                throw new UnsupportedOperationException("Unsupported TargetType: " + targetType);
        }
    }


}
