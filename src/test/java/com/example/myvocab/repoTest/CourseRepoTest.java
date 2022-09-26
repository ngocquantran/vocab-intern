package com.example.myvocab.repoTest;


import com.example.myvocab.config.TestJpaConfig;
import com.example.myvocab.model.*;
import com.example.myvocab.repo.CourseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(
        classes = {TestJpaConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@Transactional
public class CourseRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Resource
    private CourseRepo courseRepo;

    @BeforeEach
    void create_course() {
        CourseGroup group1 = CourseGroup.builder().title("group 1").build();
        CourseGroup group2 = CourseGroup.builder().title("group 2").build();

        CourseCategory category1 = CourseCategory.builder().title("cate A").img("").build();
        CourseCategory category2 = CourseCategory.builder().title("cate B").img("").build();

        Levels level1 = Levels.builder().title("A0").build();
        Levels level2 = Levels.builder().title("A1").build();
        Levels level3 = Levels.builder().title("A2").build();

        Course course1 = Course.builder()
                .title("Let go")
                .category(category1)
                .group(group1)
                .levels(List.of(level1, level2))
                .build();

        Course course2 = Course.builder()
                .title("Beginner")
                .category(category2)
                .group(group2)
                .levels(List.of(level2, level3))
                .build();

        entityManager.persist(course1);
        entityManager.persist(course2);
        entityManager.flush();
    }




}
