package com.vs.service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vs.service.daos.LoginDAO;
import com.vs.service.models.Identity;
import com.vs.service.models.Login;

@Service
public class LoginService {

	@Autowired LoginDAO loginDAO;
	
	public Map<String,Object> verifyLogin(Login login) {
		List<Map<String,Object>> resultList= loginDAO.verifyUser(login.getEmailId(),login.getPassword());
		Map<String,Object> result= new HashMap<>();
		if(resultList.size()>0) {
			for(int i=0;i<resultList.size();i++) {
				Map<String,Object> loginData=resultList.get(i);
				System.out.print("voterId:"+loginData.get("voterId"));
				if(loginData.get("password").toString().toLowerCase().equals(login.getPassword().toLowerCase())) {
					result.put("status", "200");
					result.put("voterId", loginData.get("voterId"));
					return result;
				}else {
					result.put("status", "205");
					return result;
				}
			}
		}else {
			result.put("status", "204");
			return result;
		}
		result.put("status", "202");
		return result;
	}

	public Map<String,Object> verifyVoterExist(Identity identity) {
		List<Map<String,Object>> identities=loginDAO.verifyVoterExist(identity);
		
		if(identities.size()>0) {
			Map<String,Object> result= new HashMap<>();
			result.put("status", "200");
			result.put("id", identities.get(0).get("Id"));
			return result;
		}else {
			Map<String,Object> result= new HashMap<>();
			result.put("status", "201");
			return result;
		}
	}

	public Map<String,Object> registerUser(Login login) {
		Map<String,Object> resultMap= new HashMap<>();
		if(login.getPass().equals(login.getConfirmPass())) {
			int id=loginDAO.registerUser(login);
			
			
			if(id>0) {
				resultMap.put("id", id);
				resultMap.put("statusCode", "200");
				return resultMap;
			}
			resultMap.put("statusCode", "202");
			return resultMap;
		}else {
			resultMap.put("statusCode", "203");
			return resultMap;
		}
		
	}

	public List<Map<String, Object>> getElectionDtls(int voterId) {
		Map<String,Object> electionList=loginDAO.getElectionDtls(voterId);
		System.out.println("electionList:"+electionList.toString());
		List<Map<String,Object>> resultSet2=(List<Map<String, Object>>) electionList.get("#result-set-2");
		
		if(resultSet2.get(0).get("Code").equals("200")) {
			List<Map<String,Object>> resultSet1=(List<Map<String, Object>>) electionList.get("#result-set-1");
			return resultSet1;
		}
		return resultSet2;
	}

	public List<Map<String, Object>> getCandidateDtls(int voterId) {
		Map<String,Object> candidateList=loginDAO.getCandidateDtls(voterId);
		System.out.println("candidateList:"+candidateList.toString());
		List<Map<String,Object>> resultSet2=(List<Map<String, Object>>) candidateList.get("#result-set-1");
		
		if(resultSet2.get(0).get("Code").equals("200")) {
			List<Map<String,Object>> resultSet1=(List<Map<String, Object>>) candidateList.get("#result-set-2");
			return resultSet1;
		}
		return resultSet2;

	}

	public Map<String,Object> saveBallot(String vote, String voterId) throws ParseException {
		JSONParser parser = new JSONParser();  
		JSONObject json = (JSONObject) parser.parse(vote);
		JSONArray preference=(JSONArray) json.get("preference");
		JSONArray candidateId=(JSONArray)json.get("candidateId");
		int cntMatch=0;
		boolean donkeyVote=false;
		for(int i=0;i<preference.size();i++) {
			if(Integer.parseInt(preference.get(i).toString())==(i+1)) {
				cntMatch++;
			}
		}
		
		for(int i=preference.size()-1;i>=1;i--) {
			if(Integer.parseInt(preference.get(i).toString())==(i+1)) {
				cntMatch++;
			}
		}
		if(cntMatch==preference.size()) {
			donkeyVote=true;
		}
		String submissionvote="";
		for(int i=0;i<preference.size();i++) {
			submissionvote=submissionvote+candidateId.get(i)+"#"+preference.get(i);
			if(preference.size()-1!=i)
				submissionvote=submissionvote+",";
		}
		System.out.print(submissionvote);
		return loginDAO.saveallot(voterId,!donkeyVote,donkeyVote,submissionvote);
	}

}
