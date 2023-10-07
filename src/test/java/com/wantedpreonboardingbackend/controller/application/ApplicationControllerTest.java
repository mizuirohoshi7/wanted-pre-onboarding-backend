package com.wantedpreonboardingbackend.controller.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedpreonboardingbackend.domain.Application;
import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.domain.User;
import com.wantedpreonboardingbackend.dto.application.ApplicationResponse;
import com.wantedpreonboardingbackend.dto.application.ApplicationSaveParam;
import com.wantedpreonboardingbackend.exception.DuplicateApplicationException;
import com.wantedpreonboardingbackend.service.application.ApplicationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ApplicationService applicationService;

    static ApplicationResponse response;
    static Map<String, String> saveParam = new HashMap<>();

    @BeforeAll
    static void beforeAll() {
        Company company = new Company("원티드랩");
        Recruitment recruitment = Recruitment.builder()
                .company(company)
                .country("한국")
                .region("서울")
                .position("백엔드 주니어 개발자")
                .reward(1500000)
                .techStack("Python")
                .detail("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..")
                .build();
        User user = new User("사용자");
        Application application = new Application(recruitment, user);
        response = new ApplicationResponse(application);
        saveParam.put("recruitmentId", "1");
        saveParam.put("userId", "1");
    }

    @Test
    void 지원_등록_성공() throws Exception {
        given(applicationService.save(any(ApplicationSaveParam.class))).willReturn(response);

        mvc.perform(post("/applications/save")
                .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(saveParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 지원 등록에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void 지원_등록_중복지원_실패() throws Exception {
        given(applicationService.save(any(ApplicationSaveParam.class))).willThrow(new DuplicateApplicationException("이 사용자는 해당 채용공고에 이미 지원했습니다"));

        mvc.perform(post("/applications/save")
                        .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(saveParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("이 사용자는 해당 채용공고에 이미 지원했습니다"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

}