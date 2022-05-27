package com.spring.axxya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    FeignClient feignClient;

    @GetMapping("/ms03/msg")
    public  String getData(){
        return "Message from MS-03";
    }
    @GetMapping("/data/msg")
    public  String getDataFromMS02(){
        return feignClient.getMessgaeFromMS02();
    }

}
