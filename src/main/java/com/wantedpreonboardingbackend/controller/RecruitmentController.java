package com.wantedpreonboardingbackend.controller;

import com.wantedpreonboardingbackend.dto.Result;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;

    @PostMapping("/save")
    public Result<RecruitmentResponse> save(@RequestBody RecruitmentSaveParam saveParam) {
        RecruitmentResponse response = recruitmentService.save(saveParam);
        return new Result<>("채용공고 등록에 성공했습니다", response);
    }

}
