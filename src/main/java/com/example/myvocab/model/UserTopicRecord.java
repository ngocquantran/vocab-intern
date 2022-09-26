package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.LearningStage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_topic_record",
        indexes = @Index(name = "stage", columnList = "stage"))
public class UserTopicRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "test_time")
    private Integer testTime;

    @Column(name = "right_answers")
    private Integer rightAnswers;

    @Column(name = "total_answers")
    private Integer totalAnswers;

    @Column(nullable = false)
    private LearningStage stage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_topic",nullable = false)
    @JsonIgnore
    private UserTopic userTopic;


}
