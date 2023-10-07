package com.wantedpreonboardingbackend.repository.recruitment;

import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSearchCond;

import java.util.List;

public interface RecruitmentQueryRepository {

    List<Recruitment> search(RecruitmentSearchCond searchCond);

}
