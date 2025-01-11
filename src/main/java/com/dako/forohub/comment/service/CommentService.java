package com.dako.forohub.comment.service;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dako.forohub.comment.domain.Comment;
import com.dako.forohub.comment.repository.CommentRepository;
import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.topic.domain.Topic;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository,
            TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    @Transactional
    public void creationNewComment(Long topicId, String message) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();
        User author = userRepository.findByUsername(username);
        if (author == null) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + username);
        }
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + topicId));

        // Check if a comment with the same message already exists for this topic
        boolean commentExists = commentRepository.existsByTopicAndMessage(topic, message);
        if (commentExists) {
            throw new IllegalArgumentException("Ya existe un comentario idéntico en este tópico");
        }
        Comment comment = Comment.builder()
                .author(author)
                .message(message)
                .topic(topic)
                .build();

        commentRepository.save(comment);
    }
}
