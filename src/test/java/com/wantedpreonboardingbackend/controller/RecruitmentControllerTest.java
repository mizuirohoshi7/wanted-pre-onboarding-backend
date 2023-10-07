package com.wantedpreonboardingbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.service.RecruitmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecruitmentController.class)
class RecruitmentControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    RecruitmentService recruitmentService;

    static RecruitmentResponse response;

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
        response = new RecruitmentResponse(recruitment);
    }

    @Test
    void 채용공고_등록_성공() throws Exception {
        Map<String, String> saveParam = new HashMap<>();
        saveParam.put("companyId", "1");
        saveParam.put("country", "한국");
        saveParam.put("region", "서울");
        saveParam.put("position", "백엔드 주니어 개발자");
        saveParam.put("reward", "1500000");
        saveParam.put("techStack", "Python");
        saveParam.put("detail", "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        given(recruitmentService.save(any(RecruitmentSaveParam.class))).willReturn(response);

        mvc.perform(post("/recruitments/save")
                .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(saveParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 등록에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

}