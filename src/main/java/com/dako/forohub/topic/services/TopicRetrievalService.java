package com.dako.forohub.topic.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dako.forohub.authentication.service.AuthorizationService;
import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.topic.domain.Topic;
import com.dako.forohub.topic.dtos.TopicDto;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.repositories.UserRepository;

@Service
public class TopicRetrievalService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public TopicRetrievalService(TopicRepository topicRepository, UserRepository userRepository, AuthorizationService authorizationService) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    // Obtiene todos los temas por autor (GET /topics/{id}/topics)
    public Page<TopicDto> allTopicsById(Long authorId, Pageable pageable) {
        // Verificar si el usuario existe
        if (!userRepository.existsById(authorId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con id: " + authorId);
        }

        // Buscar los temas del usuario
        Page<Topic> topics = topicRepository.findAllByAuthor_Id(authorId, pageable);
        return topics.map(topic -> new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getUpdatedAt().toLocalDate()));
    }

    // Muestra todos los temas (GET /topics/all) del foro (con paginación)
    public Page<TopicDto> getAllTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);
        return topics.map(topic -> new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getUpdatedAt().toLocalDate()));
    }

    public Page<TopicDto> getMyTopics(Pageable pageable) {
        String username = authorizationService.getAuthenticatedUsername();
        User author = authorizationService.findUserByUsername(username);

        Page<Topic> topics = topicRepository.findAllByAuthor_Id(author.getId(), pageable);
        return topics.map(topic -> new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getUpdatedAt().toLocalDate()));
    }

    public TopicDto getById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));
        return new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getUpdatedAt().toLocalDate());
    }
}
