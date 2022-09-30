package com.example.myvocab.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "vocab")
public class Vocab {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40,nullable = false)
    private String word;

    @Column(length = 100,nullable = false)
    private String img;

    @Column(length = 10,nullable = false)
    private String type;

    @Column(length = 100,nullable = false)
    private String audio;

    @Column(length = 40,nullable = false)
    private String phonetic;

    @Column(name = "en_meaning",length = 200,nullable = false)
    private String enMeaning;

    @Column(name = "vn_meaning",length = 200,nullable = false)
    private String vnMeaning;

    @Column(name = "en_sentence",length = 200,nullable = false)
    private String enSentence;

    @Column(name = "vn_sentence",length = 200,nullable = false)
    private String vnSentence;

    @Column(name = "sen_audio",length = 100,nullable = false)
    private String senAudio;


    @ManyToMany(mappedBy = "vocabs",fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JsonIgnore
    private List<Topic> topics=new ArrayList<>();

    public void addTopic(Topic topic){
        this.topics.add(topic);
    }



}
