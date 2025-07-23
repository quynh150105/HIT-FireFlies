package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.constant.EmotionType;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.domain.dto.request.ReactionRequestDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionListResponseDTO;
import com.example.hit_networking_base.domain.dto.response.ReactionResponseDTO;
import com.example.hit_networking_base.domain.entity.Reaction;
import com.example.hit_networking_base.domain.entity.User;
import com.example.hit_networking_base.domain.mapstruct.UserMapper;
import com.example.hit_networking_base.exception.NotFoundException;
import com.example.hit_networking_base.exception.UserException;
import com.example.hit_networking_base.repository.ReactionRepository;
import com.example.hit_networking_base.repository.UserRepository;
import com.example.hit_networking_base.service.ReactionService;
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

    private final ReactionRepository repository;
    private final UserMapper userMapper;

     private final UserRepository userRepository;

    @Override
    public List<ReactionResponseDTO> findReactionByTargetIdAndTargetType(long targetId, TargetType targetType) {
        return repository.findByTargetIdAndTargetType(targetId, targetType).stream()
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
    public String reactToTarget(ReactionRequestDTO request) {
        Reaction reaction = repository.findByUserIdAndTarget(request.getUserId(), request.getTargetId(), request.getTargetType()).orElse(null);

        // neu da co reaction thi thay doi
        if(reaction !=null){
            reaction.setEmotionType(request.getEmotionType());
        }
        else{ // khong thi them moi

            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(()-> new UserException("User not found"));

            Reaction react = new Reaction();
            react.setEmotionType(request.getEmotionType());
            react.setTargetId(request.getTargetId());
            react.setTargetType(request.getTargetType());
            react.setCreatedAt(LocalDateTime.now());
            react.setUser(user);
        }
        return "SUCCESS";
    }

    @Override
    public String removeReaction(Long userId, Long targetId, TargetType targetType) {
        Reaction reaction = repository.findByUserIdAndTarget(userId,targetId,targetType)
                .orElseThrow(()-> new NotFoundException("Reaction not found"));
        repository.delete(reaction);
        return "SUCCESS";
    }

    @Override
    public ReactionListResponseDTO getReaction(Long targetId, TargetType targetType) {
        List<Object[]> result = repository.countReactionsByType(targetId,targetType);

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

}
