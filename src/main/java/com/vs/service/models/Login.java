package com.vs.service.models;


public class Login {

	private int VoterId;
	private String emailId;
	private String password;
	private String confirmPass;
	private String pass;
	
	public int getVoterId() {
		return VoterId;
	}
	public void setVoterId(int voterId) {
		this.VoterId = voterId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
