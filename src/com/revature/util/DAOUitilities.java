package com.revature.util;



	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.SQLException;

	import com.revature.dao.EmployeeDAO;
	import com.revature.dao.EmployeeDAOImpl;
	import com.revature.dao.ReimbursementDAO;
	import com.revature.dao.ReimbursementDAOImpl;

	public class DAOUitilities {

	        private static final String CONNECTION_USERNAME = "JMason";
	        private static final String CONNECTION_PASSWORD = "1234567";
	        private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	        private static Connection conn;

	        public static synchronized Connection getConnection() throws SQLException {
	                if (conn== null) {
	                        try {
	                                Class.forName("oracle.jdbc.driver.OracleDriver");
	                        } catch (ClassNotFoundException e) {
	                                System.out.println("Could not register driver!");
	                                e.printStackTrace();
	                        }
	                        conn = DriverManager.getConnection(URL, CONNECTION_USERNAME,
	CONNECTION_PASSWORD);
	                }

	                //If connection was closed then retrieve a new connection
	                if (conn.isClosed()){
	                        System.out.println("Opening new connection...");
	                        conn = DriverManager.getConnection(URL, CONNECTION_USERNAME,
	CONNECTION_PASSWORD);
	                }
	                return conn;
	        }

	        public static EmployeeDAO getEmployeeDAO() {
	                return new EmployeeDAOImpl();
	        }


	        public static ReimbursementDAO getReimbursementRequestDAO() {
	                return new ReimbursementDAOImpl();
	        }

	}


