package com.vs.service.daos;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vs.service.models.Identity;
import com.vs.service.models.Login;
import com.vs.service.models.Voter;

@Repository
public class LoginDAO {
 
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private SimpleJdbcCall simpleJdbcCall;
	
	public List<Map<String,Object>> verifyUser(String emailId, String password) {
		String sql = "SELECT Email,VoterId, Password FROM Login WHERE Email=?";
		List<Map<String,Object>> resultList= jdbcTemplate.queryForList(sql,new Object[] {emailId.toLowerCase()});
		return resultList;
	}

	public List<Map<String,Object>> verifyVoterExist(Identity identity) {
		String sql = "SELECT Id FROM Voter WHERE DrivingLicense=? OR PassportNumber=?";
		List<Map<String,Object>> resultList= jdbcTemplate.queryForList(sql,new Object[] {identity.getLicenseNo().toLowerCase(),identity.getPassportNo()});
		return resultList;
		
	}

	@Transactional
	public int registerUser(Login login) {
		String sql= "INSERT INTO Login (VoterId, Email, Password) VALUES (?,?,?)";
		return jdbcTemplate.update(sql,login.getVoterId(),login.getEmailId(),login.getPass());
	}

	public Map<String,Object> getElectionDtls(int voterId) {
		 simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("FetchElectionListByVoterId");
		 Map<String, Object> out = simpleJdbcCall.execute(
			        new MapSqlParameterSource("VoterId", voterId));
		 return out;
		
	}

	public Map<String, Object> getCandidateDtls(int voterId) {
		 simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("GetCandidatesByVoter");
		 Map<String, Object> out = simpleJdbcCall.execute(
			        new MapSqlParameterSource("VoterId", voterId));
		 return out;
	}

	public Map<String,Object> saveallot(String voterId, boolean validVote, boolean donkeyVote, String submissionvote) {
		 simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
			        .withProcedureName("SaveBallot");
		 Map<String, Object> inParamMap = new HashMap();
		 inParamMap.put("VoterId", voterId);
		 inParamMap.put("ValidVote", validVote);
		 inParamMap.put("DonkeyVote", donkeyVote);
		 inParamMap.put("Preference", submissionvote);
		 Map<String, Object> out = simpleJdbcCall.execute(inParamMap);
		 return out;
	}
}
