package com.wantedpreonboardingbackend.domain;

import jakarta.persistence.*;

@Entity
public class Recruitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Integer reward;

    @Column(nullable = false)
    private String techStack;

    @Lob
    private String detail;

}
