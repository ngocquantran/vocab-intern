package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.CourseStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course",
        indexes = @Index(name = "status", columnList = "status"))
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String title;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(length = 600)
    private String content;

    @Basic
    @Column(name = "target_learner", length = 600)
    private String targetLearner;

    @Column(length = 600)
    private String goal;

    @Column(nullable = false)
    private String thumbnail;

    @Column (nullable = false)
    private CourseStatus status;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_group", referencedColumnName = "id", nullable = false)
    private CourseGroup group;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category", referencedColumnName = "id", nullable = false)
    private CourseCategory category;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "course_level",
            joinColumns = @JoinColumn(name = "id_course"),
            inverseJoinColumns = @JoinColumn(name = "id_level"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_course","id_level"})
    )
    private List<Levels> levels = new ArrayList<>();
    @Formula("(SELECT COUNT(*) FROM topic t WHERE t.id_course = id)")
    private int numberOfTopics;

    @PreRemove
    private void removeLevels() {
        for (Levels l : levels) {
            l.getCourses().remove(this);
        }
    }

    public List<String> getLevelTitles() {
        return levels.stream().map(Levels::getTitle).collect(Collectors.toList());
    }


}
