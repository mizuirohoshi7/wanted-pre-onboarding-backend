package com.wantedpreonboardingbackend.dto.recruitment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RecruitmentSaveParam {

    @NotNull
    private Long companyId;

    @NotEmpty
    private String country;

    @NotEmpty
    private String region;

    @NotEmpty
    private String position;

    @NotNull
    private Integer reward;

    @NotEmpty
    private String techStack;

    private String detail;

    @Builder
    private RecruitmentSaveParam(Long companyId, String country, String region, String position, Integer reward, String techStack, String detail) {
        this.companyId = companyId;
        this.country = country;
        this.region = region;
        this.position = position;
        this.reward = reward;
        this.techStack = techStack;
        this.detail = detail;
    }

}
