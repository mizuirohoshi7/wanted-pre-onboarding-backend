package com.wantedpreonboardingbackend.dto.recruitment;

import com.wantedpreonboardingbackend.domain.Recruitment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RecruitmentDetailResponse {

    private Long id;
    private String companyName;
    private String country;
    private String region;
    private String position;
    private Integer reward;
    private String techStack;
    private String detail;
    private List<Long> anotherRecruitments;

    public RecruitmentDetailResponse(Recruitment recruitment) {
        id = recruitment.getId();
        companyName = recruitment.getCompany().getName();
        country = recruitment.getCountry();
        region = recruitment.getRegion();
        position = recruitment.getPosition();
        reward = recruitment.getReward();
        techStack = recruitment.getTechStack();
        detail = recruitment.getDetail();
        anotherRecruitments = new ArrayList<>();
    }

}
