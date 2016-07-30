package com.hades.ssh.web.controller.front;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginFormController {

	@RequestMapping(value = {"/{login:login;?.*}"})	//spring3.2.2 bug see  http://jinnianshilongnian.iteye.com/blog/1831408
	public String loginForm(HttpServletRequest request, ModelMap model) {
		
		return "front/login";
	}
}
