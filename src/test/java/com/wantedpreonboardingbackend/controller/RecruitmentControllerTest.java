package com.wantedpreonboardingbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedpreonboardingbackend.domain.Company;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSearchCond;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentUpdateParam;
import com.wantedpreonboardingbackend.exception.DataNotFoundException;
import com.wantedpreonboardingbackend.service.RecruitmentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    static Page<RecruitmentResponse> page;

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
        page = new PageImpl<>(List.of(response));
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

    @Test
    void 채용공고_등록_없는회사_실패() throws Exception {
        Map<String, String> saveParam = new HashMap<>();
        String wrongId = "100";
        saveParam.put("companyId", wrongId);
        saveParam.put("country", "한국");
        saveParam.put("region", "서울");
        saveParam.put("position", "백엔드 주니어 개발자");
        saveParam.put("reward", "1500000");
        saveParam.put("techStack", "Python");
        saveParam.put("detail", "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        given(recruitmentService.save(any(RecruitmentSaveParam.class))).willThrow(new DataNotFoundException("해당 회사가 존재하지 않습니다"));

        mvc.perform(post("/recruitments/save")
                        .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(saveParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 회사가 존재하지 않습니다"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void 채용공고_수정_성공() throws Exception {
        Map<String, String> updateParam = new HashMap<>();
        updateParam.put("country", "한국");
        updateParam.put("region", "서울");
        updateParam.put("position", "백엔드 주니어 개발자");
        updateParam.put("reward", "1500000");
        updateParam.put("techStack", "Python");
        updateParam.put("detail", "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        given(recruitmentService.update(anyLong(), any(RecruitmentUpdateParam.class))).willReturn(response);

        mvc.perform(patch("/recruitments/1")
                .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(updateParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 수정에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void 채용공고_수정_없는공고_실패() throws Exception {
        String wrongId = "100";
        Map<String, String> updateParam = new HashMap<>();
        updateParam.put("country", "한국");
        updateParam.put("region", "서울");
        updateParam.put("position", "백엔드 주니어 개발자");
        updateParam.put("reward", "1500000");
        updateParam.put("techStack", "Python");
        updateParam.put("detail", "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..");
        given(recruitmentService.update(anyLong(), any(RecruitmentUpdateParam.class))).willThrow(new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));

        mvc.perform(patch("/recruitments/" + wrongId)
                        .contentType(APPLICATION_JSON).content(mapper.writeValueAsString(updateParam)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 채용공고가 존재하지 않습니다"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void 채용공고_삭제_성공() throws Exception {
        given(recruitmentService.delete(anyLong())).willReturn(response);

        mvc.perform(delete("/recruitments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 삭제에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void 채용공고_삭제_없는공고_실패() throws Exception {
        String wrongId = "100";
        given(recruitmentService.delete(anyLong())).willThrow(new DataNotFoundException("해당 채용공고가 존재하지 않습니다"));

        mvc.perform(delete("/recruitments/" + wrongId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 채용공고가 존재하지 않습니다"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void 채용공고_전체_조회_성공() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        given(recruitmentService.search(any(RecruitmentSearchCond.class))).willReturn(page);

        mvc.perform(get("/recruitments").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 검색에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void 채용공고_일부조건_조회_성공() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("country", "한국");
        params.add("region", "서울");
        params.add("position", "백엔드");
        given(recruitmentService.search(any(RecruitmentSearchCond.class))).willReturn(page);

        mvc.perform(get("/recruitments").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 검색에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void 채용공고_모든조건_조회_성공() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("recruitment_id", "1");
        params.add("company_name", "원티드랩");
        params.add("country", "한국");
        params.add("region", "서울");
        params.add("position", "백엔드");
        params.add("reward", "1500000");
        params.add("techStack", "Python");
        params.add("detail", "원티드랩에서");
        given(recruitmentService.search(any(RecruitmentSearchCond.class))).willReturn(page);

        mvc.perform(get("/recruitments").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("채용공고 검색에 성공했습니다"))
                .andExpect(jsonPath("$.data").exists());
    }

}