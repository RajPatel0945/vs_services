package com.vs.service.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vs.service.models.Identity;
import com.vs.service.models.Login;
import com.vs.service.services.LoginService;

@RestController
public class LoginController {

	@Autowired LoginService loginService;
	
	
	@PostMapping("login")
	public String getLogin(@RequestBody Login login) {
		return loginService.verifyLogin(login);
	}
	
	@PostMapping("verify-identity")
	public String verifyVoterExist(@RequestBody Identity identity) {
		return loginService.verifyVoterExist(identity);
	}
	
	@PostMapping("register-user")
	public Map<String,Object> registerUser(@RequestBody Login login) {
		return loginService.registerUser(login);
	}
}
