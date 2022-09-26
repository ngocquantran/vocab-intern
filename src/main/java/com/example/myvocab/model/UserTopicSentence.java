package com.example.myvocab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_topic_sentence")
public class UserTopicSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_title",nullable = false,length = 100)
    private String questionTitle;

    @Column(name = "question_content",length = 100)
    private String questionContent;

    @Column(nullable = false,length = 100)
    private String answer;

    @Column(name = "user_answer",nullable = false,length = 150)
    private String userAnswer;

    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_topic",nullable = false)
    @JsonIgnore
    private UserTopic userTopic;

}
