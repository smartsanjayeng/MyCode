package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/api/")
public class MyCodeController {
	
	@GetMapping("getMyCode/{champ}")
	public String getMyCode(@PathVariable("champ") String champ) {
		return "Welcome to My Code "+champ;
	}

}
