package com.dako.forohub.course.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.course.domain.Course;
import com.dako.forohub.course.domain.CourseCategoryEnum;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameAndCategory(String name, CourseCategoryEnum category);
}
