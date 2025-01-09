package com.dako.forohub.topic.services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dako.forohub.topic.domain.Topic;
import com.dako.forohub.topic.domain.TopicStatusEnum;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TopicCreationService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicCreationService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createNewTopic(String title, String message, LocalDateTime createdAt) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        User author = userRepository.findByUsername(username);
        if (author == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + username);
        }

        Topic topic = Topic.builder()
                .title(title)
                .message(message)
                .createdAt(createdAt)
                .status(TopicStatusEnum.OPEN)
                .author(author)
                .build();
        topicRepository.save(topic);
    }
}
