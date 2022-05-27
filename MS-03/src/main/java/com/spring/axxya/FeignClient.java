package com.spring.axxya;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.cloud.openfeign.FeignClient(name = "MS-04")
public interface FeignClient {

    @GetMapping("/ms02/msg")
    public String getMessgaeFromMS02();
}
