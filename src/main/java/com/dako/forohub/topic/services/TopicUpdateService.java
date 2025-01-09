package com.dako.forohub.topic.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dako.forohub.authentication.service.AuthorizationService;
import com.dako.forohub.topic.domain.Topic;
import com.dako.forohub.topic.domain.TopicStatusEnum;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;

import jakarta.transaction.Transactional;

@Service
public class TopicUpdateService {

    private final TopicRepository topicRepository;
    private final AuthorizationService authorizationService;

    public TopicUpdateService(TopicRepository topicRepository, AuthorizationService authorizationService) {
        this.topicRepository = topicRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void updateTopic(Long idTopic, String title, String message, TopicStatusEnum status, LocalDateTime updatedAt) {
        String username = authorizationService.getAuthenticatedUsername();
        User author = authorizationService.findUserByUsername(username);

        Topic topic = findTopicById(idTopic);

        if (!authorizationService.isAuthorizedToUpdate(author, topic.getAuthor().getId())) {
            throw new IllegalArgumentException("No tienes permisos para actualizar este tema");
        }

        updateTopicDetails(topic, title, message, status, updatedAt);
    }

    private Topic findTopicById(Long idTopic) {
        return topicRepository.findById(idTopic)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado con el id: " + idTopic));
    }

    private void updateTopicDetails(Topic topic, String title, String message, TopicStatusEnum status, LocalDateTime updatedAt) {
        topic.setTitle(title);
        topic.setMessage(message);
        topic.setStatus(status);
        topic.setUpdatedAt(updatedAt);
        topicRepository.save(topic);
    }
}
