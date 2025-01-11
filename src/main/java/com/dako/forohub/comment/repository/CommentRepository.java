package com.dako.forohub.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.comment.domain.Comment;
import com.dako.forohub.topic.domain.Topic;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    boolean existsByTopicAndMessage(Topic topic, String message);
}
