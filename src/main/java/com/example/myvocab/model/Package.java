package com.example.myvocab.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "package")
public class Package {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 50)
    private String title;

    @Column(nullable = false)
    private int duration;

    @Column(name = "price_per_month",nullable = false)
    private Long pricePerMonth;

    @Column(nullable = false)
    private String description;

    private String type;


}
