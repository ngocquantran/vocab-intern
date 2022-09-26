package com.example.myvocab.dto;

import com.example.myvocab.model.enummodel.CourseStatus;

import java.util.List;

public interface CourseInfo {
    Long getId();

    CourseStatus getStatus();

    String getTitle();

    CourseCategoryInfo getCategory();

    CourseGroupInfo getGroup();

    List<LevelsInfo> getLevels();

    interface CourseCategoryInfo {
        String getTitle();
    }

    interface CourseGroupInfo {
        String getTitle();
    }

    interface LevelsInfo {
        String getTitle();
    }
}
