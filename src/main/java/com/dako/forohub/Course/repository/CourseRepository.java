package com.dako.forohub.Course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.Course.domain.Course;
import com.dako.forohub.Course.domain.CourseCategoryEnum;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameAndCategory(String name, CourseCategoryEnum category);
}
