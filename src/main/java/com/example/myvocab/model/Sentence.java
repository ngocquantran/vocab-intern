package com.example.myvocab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sentence")
public class Sentence implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String content;

    @Column(nullable = false,length = 150)
    private String img;

    @Column(nullable = false,length = 100)
    private String phonetic;

    @Column(name = "vn_sentence",nullable = false,length = 100)
    private String vnSentence;

    @Column(name = "sen_audio",nullable = false,length = 150)
    private String senAudio;

    @Column(name = "words_audio",nullable = false,length = 150)
    private String wordsAudio;

    @Column(name = "words_timestamp",nullable = false,length = 100)
    private String wordsTimestamp;

    @Column(nullable = false,length = 100)
    private String apply;

    @Column(name = "en_context_desc",nullable = false,length = 100)
    private String enContextDesc;

    @Column(name = "vn_context_desc",nullable = false,length = 100)
    private String vnContextDesc;

    @Column(name = "context_audio",nullable = false,length = 150)
    private String contextAudio;


    @ManyToMany(mappedBy = "sentences",fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
    private Set<Topic> topics=new HashSet<>();

    public void addTopic(Topic topic){
        this.topics.add(topic);
    }


}
