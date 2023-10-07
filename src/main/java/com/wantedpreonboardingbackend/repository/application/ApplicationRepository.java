package com.wantedpreonboardingbackend.repository.application;

import com.wantedpreonboardingbackend.domain.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findByRecruitmentIdAndUserId(Long recruitmentId, Long userId);

    default boolean checkDuplicate(Long recruitmentId, Long userId) {
        Application application = findByRecruitmentIdAndUserId(recruitmentId, userId).orElse(null);
        return application != null;
    }

}
