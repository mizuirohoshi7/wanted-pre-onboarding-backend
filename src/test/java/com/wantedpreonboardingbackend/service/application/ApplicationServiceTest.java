package com.wantedpreonboardingbackend.service.application;

import com.wantedpreonboardingbackend.domain.Application;
import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.domain.User;
import com.wantedpreonboardingbackend.dto.application.ApplicationResponse;
import com.wantedpreonboardingbackend.dto.application.ApplicationSaveParam;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
import com.wantedpreonboardingbackend.exception.DuplicateApplicationException;
import com.wantedpreonboardingbackend.repository.application.ApplicationRepository;
import com.wantedpreonboardingbackend.repository.recruitment.RecruitmentRepository;
import com.wantedpreonboardingbackend.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    ApplicationService applicationService;

    @Mock
    ApplicationRepository applicationRepository;

    @Mock
    RecruitmentRepository recruitmentRepository;

    @Mock
    UserRepository userRepository;

    static ApplicationSaveParam saveParam = new ApplicationSaveParam(1L, 1L);
    static Application application;
    static Recruitment recruitment;

    @BeforeAll
    static void beforeAll() {
        Company company = new Company("원티드랩");
        recruitment = Recruitment.builder()
                .company(company)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        User user = new User("사용자");
        application = new Application(recruitment, user);
    }

    @Test
    void 지원_등록_성공() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.of(recruitment));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(new User("사용자")));
        given(applicationRepository.save(any(Application.class))).willReturn(application);
        given(applicationRepository.checkDuplicate(anyLong(), anyLong())).willReturn(false);

        ApplicationResponse response = applicationService.save(saveParam);

        assertThat(response).isNotNull();
    }

    @Test
    void 지원_등록_중복지원_실패() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.of(recruitment));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(new User("사용자")));
        given(applicationRepository.checkDuplicate(anyLong(), anyLong())).willReturn(true);

        assertThrows(DuplicateApplicationException.class, () -> applicationService.save(saveParam));
    }

    @Test
    void 지원_등록_없는공고_실패() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> applicationService.save(saveParam));
    }

    @Test
    void 지원_등록_없는사용자_실패() {
        given(recruitmentRepository.findById(anyLong())).willReturn(Optional.of(recruitment));
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> applicationService.save(saveParam));
    }

}