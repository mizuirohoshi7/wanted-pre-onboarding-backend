package com.wantedpreonboardingbackend.service.recruitment;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.*;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
import com.wantedpreonboardingbackend.repository.company.CompanyRepository;
import com.wantedpreonboardingbackend.repository.recruitment.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
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

    public RecruitmentResponse delete(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));

        recruitmentRepository.delete(recruitment);

        return new RecruitmentResponse(recruitment);
    }

    public Page<RecruitmentResponse> search(RecruitmentSearchCond searchCond) {
        List<Recruitment> recruitments = recruitmentRepository.search(searchCond);
        List<RecruitmentResponse> response = new ArrayList<>();

        for (Recruitment recruitment : recruitments) {
            response.add(new RecruitmentResponse(recruitment));
        }

        return new PageImpl<>(response);
    }

    public RecruitmentDetailResponse findById(Long recruitmentId) {
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .orElseThrow(() -> new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));
        List<Recruitment> anotherRecruitments = recruitmentRepository.findByCompanyId(recruitment.getCompany().getId());

        List<Long> anotherIds = new ArrayList<>();
        for (Recruitment anotherRecruitment : anotherRecruitments) {
            if (!anotherRecruitment.getId().equals(recruitmentId)) {
                anotherIds.add(anotherRecruitment.getId());
            }
        }

        return new RecruitmentDetailResponse(recruitment, anotherIds);
    }

}
