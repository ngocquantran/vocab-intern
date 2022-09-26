package com.example.myvocab.dto;

import com.example.myvocab.model.Levels;
import com.example.myvocab.model.enummodel.CourseStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface CourseInTableDto {
    Long getId();

    String getTitle();
    String getCategory();

    String getGroup();
    CourseStatus getStatus();

    List<Levels> getLevels();

    interface Levels{

        String getTitle();
    }


}
