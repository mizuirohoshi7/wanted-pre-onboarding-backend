package com.wantedpreonboardingbackend.repository.recruitment;

import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSearchCond;
import com.wantedpreonboardingbackend.repository.recruitment.RecruitmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RecruitmentRepositoryTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;

    @Test
    void 채용공고_전체_조회_성공() {
        RecruitmentSearchCond searchCond = RecruitmentSearchCond.builder().build();
        List<Recruitment> recruitments = recruitmentRepository.search(searchCond);

        for (Recruitment recruitment : recruitments) {
            assertThat(recruitment.getId()).isNotNull();
            assertThat(recruitment.getCompany().getName()).isNotEmpty();
            assertThat(recruitment.getCountry()).isNotEmpty();
            assertThat(recruitment.getRegion()).isNotEmpty();
            assertThat(recruitment.getPosition()).isNotEmpty();
            assertThat(recruitment.getReward()).isNotNull();
            assertThat(recruitment.getTechStack()).isNotEmpty();
        }
    }

    @Test
    void 채용공고_일부조건_조회_성공() {
        RecruitmentSearchCond searchCond = RecruitmentSearchCond.builder()
                .country("한국")
                .region("서울")
                .position("백엔드")
                .build();
        List<Recruitment> recruitments = recruitmentRepository.search(searchCond);

        for (Recruitment recruitment : recruitments) {
            assertThat(recruitment.getId()).isNotNull();
            assertThat(recruitment.getCompany().getName()).isNotEmpty();
            assertThat(recruitment.getCountry()).contains("한국");
            assertThat(recruitment.getRegion()).contains("서울");
            assertThat(recruitment.getPosition()).contains("백엔드");
            assertThat(recruitment.getReward()).isNotNull();
            assertThat(recruitment.getTechStack()).isNotEmpty();
        }
    }

    @Test
    void 채용공고_전체조건_조회_성공() {
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
        List<Recruitment> recruitments = recruitmentRepository.search(searchCond);

        for (Recruitment recruitment : recruitments) {
            assertThat(recruitment.getId()).isEqualTo(1L);
            assertThat(recruitment.getCompany().getName()).contains("원티드랩");
            assertThat(recruitment.getCountry()).contains("한국");
            assertThat(recruitment.getRegion()).contains("서울");
            assertThat(recruitment.getPosition()).contains("백엔드");
            assertThat(recruitment.getReward()).isGreaterThanOrEqualTo(1500000);
            assertThat(recruitment.getTechStack()).contains("Python");
        }
    }

}