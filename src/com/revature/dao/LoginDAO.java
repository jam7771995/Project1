package com.revature.dao;

import java.util.List;

import com.revature.model.LoginModel;

public interface LoginDAO {
	
	public LoginModel getLoginByUser(String user);
	public List<LoginModel> getYourLogins();

}
