package com.wantedpreonboardingbackend.repository;

import com.wantedpreonboardingbackend.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
}
