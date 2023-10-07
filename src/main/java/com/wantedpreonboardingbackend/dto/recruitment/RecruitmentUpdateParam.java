package com.wantedpreonboardingbackend.dto.recruitment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecruitmentUpdateParam {

    private String country;

    private String region;

    private String position;

    private Integer reward;

    private String techStack;

    private String detail;

    @Builder
    private RecruitmentUpdateParam(String country, String region, String position, Integer reward, String techStack, String detail) {
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.techStack = techStack;
        this.detail = detail;
    }

}
