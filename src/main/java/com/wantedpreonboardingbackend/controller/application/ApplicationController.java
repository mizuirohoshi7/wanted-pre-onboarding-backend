package com.wantedpreonboardingbackend.controller.application;

import com.wantedpreonboardingbackend.dto.Result;
import com.wantedpreonboardingbackend.dto.application.ApplicationResponse;
import com.wantedpreonboardingbackend.dto.application.ApplicationSaveParam;
import com.wantedpreonboardingbackend.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/save")
    public Result<ApplicationResponse> save(@RequestBody ApplicationSaveParam saveParam) {
        ApplicationResponse response = applicationService.save(saveParam);
        return new Result<>("채용공고 지원 등록에 성공했습니다", response);
    }

}
