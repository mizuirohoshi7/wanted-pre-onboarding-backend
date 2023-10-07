package com.wantedpreonboardingbackend.dto.recruitment;

import com.wantedpreonboardingbackend.domain.Recruitment;
import lombok.Getter;

@Getter
public class RecruitmentResponse {

    private Long id;
    private String companyName;
    private String country;
    private String region;
    private String position;
    private Integer reward;
    private String techStack;

    public RecruitmentResponse(Recruitment recruitment) {
        id = recruitment.getId();
        companyName = recruitment.getCompany().getName();
        country = recruitment.getCountry();
        region = recruitment.getRegion();
        position = recruitment.getPosition();
        reward = recruitment.getReward();
        techStack = recruitment.getTechStack();
    }

}
