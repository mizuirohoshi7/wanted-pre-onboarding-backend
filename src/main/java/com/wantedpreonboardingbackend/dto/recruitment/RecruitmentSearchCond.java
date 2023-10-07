package com.wantedpreonboardingbackend.dto.recruitment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecruitmentSearchCond {

    @JsonProperty("recruitment_id")
    private Long recruitmentId;
    @JsonProperty("company_name")
    private String companyName;
    private String country;
    private String region;
    private String position;
    private Integer reward;
    private String techStack;
    private String detail;

    @Builder
    private RecruitmentSearchCond(Long recruitmentId, String companyName, String country, String region, String position, Integer reward, String techStack, String detail) {
        this.recruitmentId = recruitmentId;
        this.companyName = companyName;
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.techStack = techStack;
        this.detail = detail;
    }

}
