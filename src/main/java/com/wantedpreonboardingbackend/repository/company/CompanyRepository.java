package com.wantedpreonboardingbackend.repository.company;

import com.wantedpreonboardingbackend.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
