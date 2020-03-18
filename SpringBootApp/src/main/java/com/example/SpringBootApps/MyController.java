package com.example.SpringBootApps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class MyController {

	@GetMapping("/")
	public String greeting() {
		return "hello!!!!!!!!!";
	}

}
