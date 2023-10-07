package com.wantedpreonboardingbackend.service.application;

import com.wantedpreonboardingbackend.domain.Application;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.domain.User;
import com.wantedpreonboardingbackend.dto.application.ApplicationResponse;
import com.wantedpreonboardingbackend.dto.application.ApplicationSaveParam;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
import com.wantedpreonboardingbackend.exception.DuplicateApplicationException;
import com.wantedpreonboardingbackend.repository.application.ApplicationRepository;
import com.wantedpreonboardingbackend.repository.recruitment.RecruitmentRepository;
import com.wantedpreonboardingbackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final UserRepository userRepository;

    public ApplicationResponse save(ApplicationSaveParam saveParam) {
        Recruitment recruitment = recruitmentRepository.findById(saveParam.getRecruitmentId())
                .orElseThrow(() -> new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));
        User user = userRepository.findById(saveParam.getUserId())
                .orElseThrow(() -> new DataNotFoundException("해당 사용자가 존재하지 않습니다"));

        boolean isDuplicated = applicationRepository.checkDuplicate(saveParam.getRecruitmentId(), saveParam.getUserId());
        if (isDuplicated) {
            throw new DuplicateApplicationException("이 사용자는 해당 채용공고에 이미 지원했습니다");
        }

        Application savedApplication = applicationRepository.save(new Application(recruitment, user));

        return new ApplicationResponse(savedApplication);
    }

}
