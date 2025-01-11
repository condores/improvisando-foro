package com.dako.forohub.topic.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.dako.forohub.course.domain.Course;
import com.dako.forohub.topic.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByAuthor_Id(Long authorId, Pageable pageable);

    void deleteById(@NonNull Long idTopic);

    @NonNull
    Optional<Topic> findById(@NonNull Long id);

    boolean existsByTitleAndCourse(String title, Course course);
}
