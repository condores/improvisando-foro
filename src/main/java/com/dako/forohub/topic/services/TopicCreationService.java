package com.dako.forohub.topic.services;

import java.time.LocalDateTime;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dako.forohub.Course.domain.Course;
import com.dako.forohub.Course.domain.CourseCategoryEnum;
import com.dako.forohub.Course.repository.CourseRepository;
import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
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
    private final CourseRepository courseRepository;

    public TopicCreationService(TopicRepository topicRepository, UserRepository userRepository,
            CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public void createNewTopic(String title, String message, CourseCategoryEnum category, String courseName,
            LocalDateTime createdAt) {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .getUsername();

        User author = userRepository.findByUsername(username);
        if (author == null) {
            throw new ResourceNotFoundException("Usuario no encontrado: " + username);
        }

        Course course = courseRepository.findByNameAndCategory(courseName, category)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Curso no encontrado: " + courseName + " en la categoría: " + category));

        if (topicRepository.existsByTitleAndCourse(title, course)) {
            throw new IllegalArgumentException("Ya existe un tema idéntico en este curso");
        }
        Topic topic = Topic.builder()
                .title(title)
                .message(message)
                .createdAt(createdAt)
                .status(TopicStatusEnum.OPEN)
                .author(author)
                .course(course)
                .build();
        topicRepository.save(topic);
    }
}
