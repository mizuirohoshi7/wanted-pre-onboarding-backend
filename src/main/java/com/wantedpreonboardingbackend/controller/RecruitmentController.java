package com.wantedpreonboardingbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wantedpreonboardingbackend.dto.Result;
import com.wantedpreonboardingbackend.dto.recruitment.*;
import com.wantedpreonboardingbackend.service.RecruitmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recruitments")
public class RecruitmentController {

    private final RecruitmentService recruitmentService;
    private final ObjectMapper mapper;

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

    @DeleteMapping("/{recruitmentId}")
    public Result<RecruitmentResponse> delete(@PathVariable Long recruitmentId) {
        RecruitmentResponse response = recruitmentService.delete(recruitmentId);
        return new Result<>("채용공고 삭제에 성공했습니다", response);
    }

    @GetMapping
    public Result<Page<RecruitmentResponse>> search(@RequestParam Map<String, String> params) {
        RecruitmentSearchCond searchCond = mapper.convertValue(params, RecruitmentSearchCond.class);
        Page<RecruitmentResponse> page = recruitmentService.search(searchCond);

        return new Result<>("채용공고 검색에 성공했습니다", page);
    }

    @GetMapping("/{recruitmentId}")
    public Result<RecruitmentDetailResponse> detail(@PathVariable Long recruitmentId) {
        RecruitmentDetailResponse response = recruitmentService.findById(recruitmentId);
        return new Result<>("채용공고 상세 조회에 성공했습니다", response);
    }

}
