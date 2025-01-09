package com.dako.forohub.topic.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.topic.domain.Topic;

import org.springframework.lang.NonNull;
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findAllByAuthor_Id(Long authorId, Pageable pageable);

    void deleteById(@NonNull Long idTopic);
}
