package com.spring.axxya;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/msg")
    public String getMsg(){
        return  "Hello World";
    }

    @GetMapping("/ms02/msg")
    public  String getData(){
        return "Message from MS-02";
    }
}
