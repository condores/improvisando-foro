package com.dako.forohub.topic.dtos;

import java.time.LocalDateTime;

import com.dako.forohub.topic.domain.TopicStatusEnum;

public record TopicUpdateDto(
    String title,
    String message,
    TopicStatusEnum status,
    LocalDateTime updateAt
) {

}
