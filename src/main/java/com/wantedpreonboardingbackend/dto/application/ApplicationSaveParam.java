package com.wantedpreonboardingbackend.dto.application;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApplicationSaveParam {

    private Long recruitmentId;
    private Long userId;

    public ApplicationSaveParam(Long recruitmentId, Long userId) {
        this.recruitmentId = recruitmentId;
        this.userId = userId;
    }

}
