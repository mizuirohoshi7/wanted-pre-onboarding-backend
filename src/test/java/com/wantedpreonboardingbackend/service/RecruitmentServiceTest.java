package com.wantedpreonboardingbackend.service;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.repository.CompanyRepository;
import com.wantedpreonboardingbackend.repository.RecruitmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecruitmentServiceTest {

    @InjectMocks
    RecruitmentService recruitmentService;

    @Mock
    RecruitmentRepository recruitmentRepository;

    @Mock
    CompanyRepository companyRepository;

    static Recruitment recruitment;
    static RecruitmentResponse response;
    static Company company;

    @BeforeAll
    static void beforeAll() {
        company = new Company("원티드랩");
        recruitment = Recruitment.builder()
                .company(company)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        response = new RecruitmentResponse(recruitment);
    }

    @Test
    void 채용공고_등록_성공() {
        RecruitmentSaveParam saveParam = RecruitmentSaveParam.builder()
                .companyId(1L)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        given(companyRepository.findById(anyLong())).willReturn(Optional.of(company));
        given(recruitmentRepository.save(any(Recruitment.class))).willReturn(recruitment);

        RecruitmentResponse response = recruitmentService.save(saveParam);

        assertThat(response.getCountry()).isNotEmpty();
        assertThat(response.getRegion()).isNotEmpty();
        assertThat(response.getPosition()).isNotEmpty();
        assertThat(response.getReward()).isNotNull();
        assertThat(response.getTechStack()).isNotEmpty();
    }

}