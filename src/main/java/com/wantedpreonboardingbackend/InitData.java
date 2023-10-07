package com.wantedpreonboardingbackend;

import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitData {

    private final CompanyRepository companyRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Company company = new Company("원티드랩");
        companyRepository.save(company);
    }

}
