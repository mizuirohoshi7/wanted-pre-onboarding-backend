package com.wantedpreonboardingbackend.service;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentUpdateParam;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
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
        Company company = companyRepository.findById(saveParam.getCompanyId())
                .orElseThrow(() -> new DataNotFoundException("해당 회사가 존재하지 않습니다"));

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

    public RecruitmentResponse update(Long recruitmentId, RecruitmentUpdateParam updateParam) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));

        Recruitment updatedRecruitment = recruitment.update(updateParam);

        return new RecruitmentResponse(updatedRecruitment);
    }

}
