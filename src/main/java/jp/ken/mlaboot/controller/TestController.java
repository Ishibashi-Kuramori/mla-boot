package jp.ken.mlaboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping("/test")
	public String open(Model model) {
		String str = "Spring Boot Test";
		model.addAttribute("value", str);
		return "test";
	}
}
