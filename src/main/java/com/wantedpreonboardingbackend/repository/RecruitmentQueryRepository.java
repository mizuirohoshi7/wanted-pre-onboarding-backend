package com.wantedpreonboardingbackend.repository;

import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSearchCond;

import java.util.List;

public interface RecruitmentQueryRepository {

    List<Recruitment> search(RecruitmentSearchCond searchCond);

}
