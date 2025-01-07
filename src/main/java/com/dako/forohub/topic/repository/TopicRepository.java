package com.dako.forohub.topic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.topic.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByAuthor_Id(Long authorId);
}
