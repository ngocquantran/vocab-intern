package com.example.myvocab.dto;

import java.io.Serializable;

public interface VocabTestResultDto extends Serializable {
    Long getId();

    boolean isStatus();

    VocabInfo getVocab();

    interface VocabInfo {
        Long getId();

        String getAudio();

        String getImg();

        String getPhonetic();

        String getType();

        String getVnMeaning();

        String getWord();
    }
}
