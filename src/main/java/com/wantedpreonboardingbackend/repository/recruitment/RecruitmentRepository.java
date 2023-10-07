package com.wantedpreonboardingbackend.repository.recruitment;

import com.wantedpreonboardingbackend.domain.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long>, RecruitmentQueryRepository {

    List<Recruitment> findByCompanyId(Long companyId);

}
