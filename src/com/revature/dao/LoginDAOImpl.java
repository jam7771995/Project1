package com.revature.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.revature.model.LoginModel;
import com.revature.util.DAOUitilities;

public class LoginDAOImpl implements LoginDAO{

	public LoginModel gettingLoginByUsername(String username) {
		LoginModel j= new LoginModel();
		
		String sqlJ= "SELECT * FROM EMPLOYEEJMASON WHERE EM_USERNAME = ?";
		ResultSet res = null;
		
		try(Connection c = DAOUitilities.getConnection ();
		
			PreparedStatement pre = c.prepareStatement(sqlJ)){
				pre.setString(1, username);
				
				res=pre.executeQuery();
				
				while(res.next()) {
					int emId = res.getInt("EM_ID");
					j.setId(emId);
					
					String username1= res.getString("EM_USERNAME");
					j.setUsername(username1);
					
					String password = res.getString("EM_PASSWORD");
				}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		if(res !=null) {
			try {
				res.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	} return j;
	}
	
	@Override
	public List<LoginModel> getYourLogins() {
		List<LoginModel> ListOfLogins = new ArrayList<LoginModel>();
		
		String sql = "SELECT * FROM EMPLOYEEJMASON";
		
		try (Connection con = DAOUitilities.getConnection();
				Statement stm = con.createStatement();
				ResultSet res = stm.executeQuery(sql)){
			
			while(res.next()) {
				LoginModel j = new LoginModel();
				int emId = res.getInt("EM_ID");
				j.setId(emId);
				
				String user = res.getString("EM_USERNAME");
				j.setUsername(user);
				
				String pwsd = res.getString("EM_PASSWORD");
				j.setPassword(pwsd);
				
				LoginModel.add(j);
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
		return ListOfLogins;
	}

	@Override
	public LoginModel getLoginByUser(String user) {
		// TODO Auto-generated method stub
		return null;
	}

}



