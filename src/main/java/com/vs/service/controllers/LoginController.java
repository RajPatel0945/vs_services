package com.vs.service.controllers;

import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vs.service.models.Identity;
import com.vs.service.models.Login;
import com.vs.service.services.LoginService;

@RestController
public class LoginController {

	@Autowired LoginService loginService;
	
	
	@RequestMapping("login")
	public @ResponseBody Map<String,Object> getLogin(@RequestParam("emailId") String emailId,@RequestParam("pass") String pass) {
		Login login=new Login();
		login.setEmailId(emailId);
		login.setPassword(pass);
		return loginService.verifyLogin(login);
	}
	
	@GetMapping("verify-identity")
	public @ResponseBody Map<String,Object> verifyVoterExist(@RequestParam("licenseNo") String licenseNo,@RequestParam("passportNo") String passportNo) {
		Identity identity=new Identity();
		identity.setLicenseNo(licenseNo);
		identity.setPassportNo(passportNo);
		return loginService.verifyVoterExist(identity);
	}
	
	@GetMapping("register-user")
	public @ResponseBody Map<String,Object> registerUser(@RequestParam("voterId") String voterId,@RequestParam("emailId") String emailId,@RequestParam("pass") String pass,@RequestParam("confirmPass") String confirmPass) {
		Login login=new Login();
		if(voterId!=null && !voterId.isEmpty()) {
			login.setVoterId(Integer.parseInt(voterId));
		}
		login.setEmailId(emailId);
		login.setPass(pass);
		login.setConfirmPass(confirmPass);
		return loginService.registerUser(login);
	}
	
	@GetMapping("get-election-dtls")
	public @ResponseBody List<Map<String,Object>> getElectionDtls(@RequestParam("voterId") int voterId){
		return loginService.getElectionDtls(voterId);
	}
	
	@GetMapping("get-candidate-dtls")
	public @ResponseBody List<Map<String,Object>> getCandidateDtls(@RequestParam("voterId") int voterId){
		return loginService.getCandidateDtls(voterId);
	}
	
	@GetMapping("save-ballot")
	public @ResponseBody Map<String,Object> saveBallot(@RequestParam("vote") String vote, @RequestParam("voterId") String voterId) throws ParseException {
		
		return loginService.saveBallot(vote,voterId);
	}
}
