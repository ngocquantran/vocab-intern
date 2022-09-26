package com.example.myvocab.model;

import com.example.myvocab.model.enummodel.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "context",
        indexes = @Index(name = "gender", columnList = "gender"))
public class Context {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_number", nullable = false)
    private int personNumber;

    @Column(nullable = false,length = 30)
    private String name;

    private Gender gender;

    @Column(name = "en_sentence",nullable = false,length = 200)
    private String enSentence;

    @Column(name = "vn_sentence",nullable = false,length = 200)
    private String vnSentence;

    @Column(name = "contain_key",nullable = false)
    private boolean isContainKey;

    @Column(name = "start_time",nullable = false)
    private int startTime;

    @Column(name = "end_time",nullable = false)
    private int endTime;

    @ManyToOne
    @JoinColumn(name = "id_sentence",nullable = false)
    @JsonIgnore
    private Sentence sentence;

}
