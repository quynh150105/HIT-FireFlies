package com.example.hit_networking_base.controller;

import com.example.hit_networking_base.base.RestApiV1;
import com.example.hit_networking_base.base.VsResponseUtil;
import com.example.hit_networking_base.constant.TargetType;
import com.example.hit_networking_base.constant.UrlConstant;
import com.example.hit_networking_base.domain.dto.request.ReactionRequestDTO;
import com.example.hit_networking_base.service.ReactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class ReactionController {
    private final ReactionService service;
    @Operation(summary = "User Delete Reaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User delete reaction successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping(UrlConstant.Reaction.DELETE)
    public ResponseEntity<?> removeReaction( @RequestParam Long targetId, @RequestParam TargetType targetType){
        return VsResponseUtil.success(service.removeReaction( targetId,targetType));
    }

    @Operation(summary = "User Get All Reactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User get all reaction successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(UrlConstant.Reaction.GET_ALL)
    public ResponseEntity<?> getReaction(@RequestParam Long targetId, @RequestParam TargetType targetType){
        return VsResponseUtil.success(service.getReaction(targetId, targetType));
    }

    @Operation(summary = "User Create Reaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User delete reaction successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(UrlConstant.Reaction.CREATE)
    public ResponseEntity<?> reactToTarget(@RequestBody ReactionRequestDTO request){
        return VsResponseUtil.success(service.createReaction(request));
    }

    @Operation(summary = "User Update Reactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Update reaction successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(UrlConstant.Reaction.UPDATE)
    public ResponseEntity<?> updateReactions(@RequestBody ReactionRequestDTO request){
        return VsResponseUtil.success(service.updateReaction(request));
    }

}
