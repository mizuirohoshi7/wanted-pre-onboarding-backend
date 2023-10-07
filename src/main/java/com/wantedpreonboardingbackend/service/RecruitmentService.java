package com.wantedpreonboardingbackend.service;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.repository.CompanyRepository;
import com.wantedpreonboardingbackend.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final CompanyRepository companyRepository;

    public RecruitmentResponse save(RecruitmentSaveParam saveParam) {
        Company company = companyRepository.findById(saveParam.getCompanyId()).orElseThrow();

        Recruitment recruitment = Recruitment.builder()
                .company(company)
                .country(saveParam.getCountry())
                .region(saveParam.getRegion())
                .position(saveParam.getPosition())
                .reward(saveParam.getReward())
                .techStack(saveParam.getTechStack())
                .detail(saveParam.getDetail())
                .build();

        Recruitment savedRecruitment = recruitmentRepository.save(recruitment);

        return new RecruitmentResponse(savedRecruitment);
    }

}
