package com.dako.forohub.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.comment.dtos.CommentRequestCreationDto;
import com.dako.forohub.comment.service.CommentService;
import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.infra.responses.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("comments")
@Tag(name = "Comment Creation", description = "Endpoints for creating new comments")
public class CommentsCreationController {

    private final CommentService commentService;

    public CommentsCreationController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("new/{topicId}")
    @Operation(summary = "Create a New Comment", description = "EndPoint to create a new comment in a topic", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ApiResponse> creationNewComment(@RequestBody @Valid CommentRequestCreationDto commentDto,
            @PathVariable Long topicId) {
        try {
            commentService.creationNewComment(topicId, commentDto.message());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Comment created successfully", HttpStatus.CREATED.value()));
        } catch (ResourceNotFoundException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse(message, status.value()));
    }

}
