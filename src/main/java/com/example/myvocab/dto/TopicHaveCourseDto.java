package com.example.myvocab.dto;

import com.example.myvocab.model.Course;
import com.example.myvocab.model.CourseCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Transient;

public interface TopicHaveCourseDto {
    @JsonProperty("id")
    Long getId();

    @JsonProperty("title")
    String getTitle();

    @JsonProperty("course_id")
    Long getCourseId();

    @JsonProperty("category")
    String getCategory();

    @JsonProperty("course_title")
    String getCourseTitle();

    int getNumberOfVocabs();


}
