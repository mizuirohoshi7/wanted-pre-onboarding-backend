package com.wantedpreonboardingbackend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    private Recruitment(Company company, String country, String region, String position, Integer reward, String techStack, String detail) {
        this.company = company;
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.techStack = techStack;
        this.detail = detail;
    }

}
