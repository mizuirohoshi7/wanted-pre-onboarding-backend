package com.wantedpreonboardingbackend.dto;

import lombok.Getter;

@Getter
public class Result<T> {

    private String message;
    private T data;

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

}
