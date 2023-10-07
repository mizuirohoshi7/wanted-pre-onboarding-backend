package com.wantedpreonboardingbackend.dto.application;

import com.wantedpreonboardingbackend.domain.Application;
import lombok.Getter;

@Getter
public class ApplicationResponse {

    private Long recruitmentId;
    private Long userId;

    public ApplicationResponse(Application application) {
        recruitmentId = application.getRecruitment().getId();
        userId = application.getUser().getId();
    }

}
