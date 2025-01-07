package com.dako.forohub.topic.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dako.forohub.topic.domain.Topic;
import com.dako.forohub.topic.domain.TopicStatusEnum;
import com.dako.forohub.topic.dto.TopicDto;
import com.dako.forohub.topic.repository.TopicRepository;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    // Método para crear un nuevo tema en la base de datos (con seguridad)
    @Transactional
    public void createTopic(String title, String message, LocalDateTime createdAt) {
        // Obtener el nombre de usuario del usuario autenticado
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        // Buscar el usuario en la base de datos
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

    public List<TopicDto> allTopicsById(Long authorId) {
        List<Topic> topics = topicRepository.findAllByAuthor_Id(authorId);
        return topics.stream().map(topic -> new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName() 
        )).collect(Collectors.toList());
    }
    // metodo para obtener todos los temas por ID

    public Page<TopicDto> getAllTopics(Pageable pageable) {
        Page<Topic> topics = topicRepository.findAll(pageable);
        return topics.map(topic -> new TopicDto(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt().toLocalDate(),
                topic.getStatus(),
                topic.getAuthor().getName()
        ));
    }

}