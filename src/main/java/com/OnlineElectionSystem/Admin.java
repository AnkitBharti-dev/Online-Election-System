package com.OnlineElectionSystem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Admin {
	@GetMapping("/singUp")
	public String signUp() {
		return "home";
	}
}
