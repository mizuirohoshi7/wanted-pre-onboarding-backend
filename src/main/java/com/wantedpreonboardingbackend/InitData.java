package com.wantedpreonboardingbackend;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.repository.CompanyRepository;
import com.wantedpreonboardingbackend.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitData {

    private final CompanyRepository companyRepository;
    private final RecruitmentRepository recruitmentRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Company company = new Company("원티드랩");
        companyRepository.save(company);

        Recruitment recruitment = Recruitment.builder()
                .company(company)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        recruitmentRepository.save(recruitment);
    }

}
