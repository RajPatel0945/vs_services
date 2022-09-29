package com.vs.service.daos;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
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
	
	public List<Login> verifyUser(String emailId) {
		String sql = "SELECT * FROM Login WHERE Email=?";
		List<Login> login = jdbcTemplate.query(sql,new Object[] {emailId.toLowerCase()},BeanPropertyRowMapper.newInstance(Login.class));
		return login;
	}

	public List<Identity> verifyVoterExist(Identity identity) {
		String sql = "SELECT * FROM Voter WHERE DrivingLicense=? OR PassportNumber=?";
		List<Identity> identities = jdbcTemplate.query(sql,new Object[] {identity.getLicenseNo().toLowerCase(),identity.getPassportNo()},BeanPropertyRowMapper.newInstance(Identity.class));
		return identities;
		
	}

	@Transactional
	public int registerUser(Login login) {
		String sql= "INSERT INTO Login (VoterId, Email, Password) VALUES (?,?,?)";
		return jdbcTemplate.update(sql,login.getVoterId(),login.getEmailId(),login.getPassword());
	}
}
