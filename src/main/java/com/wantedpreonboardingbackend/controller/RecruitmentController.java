package com.wantedpreonboardingbackend.controller;

import com.wantedpreonboardingbackend.dto.Result;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentResponse;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSaveParam;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentUpdateParam;
import com.wantedpreonboardingbackend.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{recruitmentId}")
    public Result<RecruitmentResponse> update(@PathVariable Long recruitmentId, @RequestBody RecruitmentUpdateParam updateParam) {
        RecruitmentResponse response = recruitmentService.update(recruitmentId, updateParam);
        return new Result<>("채용공고 수정에 성공했습니다", response);
    }

}
