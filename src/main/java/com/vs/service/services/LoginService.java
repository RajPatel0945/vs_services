package com.vs.service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vs.service.daos.LoginDAO;
import com.vs.service.models.Identity;
import com.vs.service.models.Login;

@Service
public class LoginService {

	@Autowired LoginDAO loginDAO;
	
	public String verifyLogin(Login login) {
		List<Login> loginDtls=loginDAO.verifyUser(login.getEmailId());
		if(loginDtls.size()>0) {
			for(int i=0;i<loginDtls.size();i++) {
				Login loginData=loginDtls.get(i);
				if(loginData.getPassword().toLowerCase().equals(login.getPassword().toLowerCase())) {
					return "200";
				}else {
					return "205";
				}
			}
		}else {
			return "204";
		}
		
		return "202";
	}

	public String verifyVoterExist(Identity identity) {
		List<Identity> identities=loginDAO.verifyVoterExist(identity);
		if(identities.size()>0) {
			return "200";
		}else {
			return "201";
		}
	}

	public Map<String,Object> registerUser(Login login) {
		int id=loginDAO.registerUser(login);
		Map<String,Object> resultMap= new HashMap<>();
		if(id>0) {
			
			resultMap.put("id", id);
			resultMap.put("statusCode", "200");
			return resultMap;
		}
		resultMap.put("statusCode", "202");
		return resultMap;
	}

}
