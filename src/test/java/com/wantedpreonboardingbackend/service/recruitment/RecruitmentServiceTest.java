package com.wantedpreonboardingbackend.service.recruitment;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.*;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
import com.wantedpreonboardingbackend.repository.company.CompanyRepository;
import com.wantedpreonboardingbackend.repository.recruitment.RecruitmentRepository;
import com.wantedpreonboardingbackend.service.recruitment.RecruitmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    static Company company;
    static List<Recruitment> recruitments = new ArrayList<>();

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
        recruitments.add(recruitment);
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

        assertThat(response.getCountry()).isEqualTo(saveParam.getCountry());
        assertThat(response.getRegion()).isEqualTo(saveParam.getRegion());
        assertThat(response.getPosition()).isEqualTo(saveParam.getPosition());
        assertThat(response.getReward()).isEqualTo(saveParam.getReward());
        assertThat(response.getTechStack()).isEqualTo(saveParam.getTechStack());
    }

    @Test
    void 채용공고_등록_없는회사_실패() {
        Long wrongId = 100L;
        RecruitmentSaveParam saveParam = RecruitmentSaveParam.builder()
                .companyId(wrongId)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        given(companyRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> recruitmentService.save(saveParam));
    }

    @Test
    void 채용공고_수정_성공() {
        Long recruitmentId = 1L;
        RecruitmentUpdateParam updateParam = RecruitmentUpdateParam.builder()
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        given(recruitmentRepository.findById(recruitmentId)).willReturn(Optional.of(recruitment));

        RecruitmentResponse response = recruitmentService.update(recruitmentId, updateParam);

        assertThat(response.getCountry()).isEqualTo(updateParam.getCountry());
        assertThat(response.getRegion()).isEqualTo(updateParam.getRegion());
        assertThat(response.getPosition()).isEqualTo(updateParam.getPosition());
        assertThat(response.getReward()).isEqualTo(updateParam.getReward());
        assertThat(response.getTechStack()).isEqualTo(updateParam.getTechStack());
    }

    @Test
    void 채용공고_수정_없는공고_실패() {
        Long wrongId = 100L;
        RecruitmentUpdateParam updateParam = RecruitmentUpdateParam.builder()
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> recruitmentService.update(wrongId, updateParam));
    }

    @Test
    void 채용공고_삭제_성공() {
        Long recruitmentId = 1L;
        given(recruitmentRepository.findById(recruitmentId)).willReturn(Optional.of(recruitment));

        RecruitmentResponse response = recruitmentService.delete(recruitmentId);

        assertThat(response.getCountry()).isEqualTo(recruitment.getCountry());
        assertThat(response.getRegion()).isEqualTo(recruitment.getRegion());
        assertThat(response.getPosition()).isEqualTo(recruitment.getPosition());
        assertThat(response.getReward()).isEqualTo(recruitment.getReward());
        assertThat(response.getTechStack()).isEqualTo(recruitment.getTechStack());
    }

    @Test
    void 채용공고_삭제_없는공고_실패() {
        Long wrongId = 100L;
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> recruitmentService.delete(wrongId));
    }

    @Test
    void 채용공고_전체_조회_성공() {
        RecruitmentSearchCond searchCond = RecruitmentSearchCond.builder().build();
        given(recruitmentRepository.search(any(RecruitmentSearchCond.class))).willReturn(recruitments);

        Page<RecruitmentResponse> page = recruitmentService.search(searchCond);

        for (RecruitmentResponse response : page) {
            assertThat(response.getCountry()).isNotEmpty();
            assertThat(response.getRegion()).isNotEmpty();
            assertThat(response.getPosition()).isNotEmpty();
            assertThat(response.getReward()).isNotNull();
            assertThat(response.getTechStack()).isNotEmpty();
        }
    }

    @Test
    void 채용공고_일부조건_조회_성공() {
        RecruitmentSearchCond searchCond = RecruitmentSearchCond.builder()
                .country("한국")
                .region("서울")
                .position("백엔드")
                .build();
        given(recruitmentRepository.search(any(RecruitmentSearchCond.class))).willReturn(recruitments);

        Page<RecruitmentResponse> page = recruitmentService.search(searchCond);

        for (RecruitmentResponse response : page) {
            assertThat(response.getCountry()).contains("한국");
            assertThat(response.getRegion()).contains("서울");
            assertThat(response.getPosition()).contains("백엔드");
            assertThat(response.getReward()).isNotNull();
            assertThat(response.getTechStack()).isNotEmpty();
        }
    }

    @Test
    void 채용공고_모든조건_조회_성공() {
        RecruitmentSearchCond searchCond = RecruitmentSearchCond.builder()
                .recruitmentId(1L)
                .companyName("원티드랩")
                .country("한국")
                .region("서울")
                .position("백엔드")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서")
                .build();
        given(recruitmentRepository.search(any(RecruitmentSearchCond.class))).willReturn(recruitments);

        Page<RecruitmentResponse> page = recruitmentService.search(searchCond);

        for (RecruitmentResponse response : page) {
            assertThat(response.getCompanyName()).contains("원티드랩");
            assertThat(response.getCountry()).contains("한국");
            assertThat(response.getRegion()).contains("서울");
            assertThat(response.getPosition()).contains("백엔드");
            assertThat(response.getReward()).isGreaterThanOrEqualTo(1500000);
            assertThat(response.getTechStack()).contains("Python");
        }
    }

    @Test
    void 채용공고상세_조회_성공() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.of(recruitment));
        given(recruitmentRepository.findByCompanyId(any())).willReturn(new ArrayList<>());

        RecruitmentDetailResponse response = recruitmentService.findById(1L);

        assertThat(response.getCompanyName()).isNotEmpty();
        assertThat(response.getCountry()).isNotEmpty();
        assertThat(response.getRegion()).isNotEmpty();
        assertThat(response.getPosition()).isNotEmpty();
        assertThat(response.getReward()).isNotNull();
        assertThat(response.getTechStack()).isNotEmpty();
        assertThat(response.getDetail()).isNotEmpty();
        assertThat(response.getAnotherRecruitments()).isNotNull();
    }

    @Test
    void 채용공고상세_조회_실패() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> recruitmentService.findById(1L));
    }

}