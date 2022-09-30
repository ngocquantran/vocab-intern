package com.example.myvocab.model;

import com.example.myvocab.request.VocabAddRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "topic")
public class Topic implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String img;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "id_course", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    private Course course;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "vocab_topic",
            joinColumns = @JoinColumn(name = "id_topic"),
            inverseJoinColumns = @JoinColumn(name = "id_vocab"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_vocab","id_topic"})
    )
    @JsonIgnore
    private List<Vocab> vocabs=new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "sentence_topic",
            joinColumns = @JoinColumn(name = "id_topic"),
            inverseJoinColumns = @JoinColumn(name = "id_sentence"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_sentence","id_topic"})
    )
    @JsonIgnore
    private List<Sentence> sentences=new ArrayList<>();

    @Transient
    private int numberOfVocabs;

    @Transient
    private int numberOfSens;

    public int getNumberOfVocabs() {
        return vocabs.size();
    }

    public int getNumberOfSens() {
        return sentences.size();
    }


    public void addVocab(Vocab vocab){
        this.vocabs.add(vocab);
    }

    public void addSentence(Sentence sentence){
        this.sentences.add(sentence);
    }

}
