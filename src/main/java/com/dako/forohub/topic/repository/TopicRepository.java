package com.dako.forohub.topic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.topic.domain.Topic;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}
