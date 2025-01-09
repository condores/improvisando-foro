package com.dako.forohub.topic.services;

import org.springframework.stereotype.Service;

import com.dako.forohub.authentication.service.AuthorizationService;
import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;

import jakarta.transaction.Transactional;

@Service
public class TopicDeletionService {

    private final TopicRepository topicRepository;
    private final AuthorizationService authorizationService;

    public TopicDeletionService(TopicRepository topicRepository, AuthorizationService authorizationService) {
        this.topicRepository = topicRepository;
        this.authorizationService = authorizationService;
    }

    @Transactional
    public void deleteTopic(Long idTopic) {
        String username = authorizationService.getAuthenticatedUsername();
        User author = authorizationService.findUserByUsername(username);

        var topic = topicRepository.findById(idTopic)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado: " + idTopic));

        if (!authorizationService.isAuthorizedToUpdate(author, topic.getAuthor().getId())) {
            throw new IllegalArgumentException("No tienes permisos para eliminar este tema");
        }

        topicRepository.deleteById(idTopic);
    }
}
