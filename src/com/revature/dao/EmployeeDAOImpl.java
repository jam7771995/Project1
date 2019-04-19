package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.model.Employee;
import com.revature.util.DAOUitilities;

public class EmployeeDAOImpl implements EmployeeDAO {

	Connection connection = null;   
    Connection connectionPK = null;
    PreparedStatement stmt = null;  
    Statement stmtpk = null;

    @Override
    public List<Employee> getAllEmployees() {

            List<Employee> employees = new ArrayList<>();

            try {
                    connection = DAOUitilities.getConnection();     

                    String sql = "SELECT * FROM EMPLOYEEJMASON";        
                    stmt = connection.prepareStatement(sql);        
                    ResultSet rs = stmt.executeQuery();                     
                    while (rs.next()) {
                                                       Employee employee = new Employee();

                                      employee.setId(rs.getInt("em_id"));
                            employee.setFirstName(rs.getString("em_first_name"));
                            employee.setLastName(rs.getString("em_last_name"));
                            employee.setEmail(rs.getString("em_email"));
                            employee.setManagerId(rs.getInt("manager_id"));


                            employees.add(employee);
                    }
                    rs.close();

            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
           
                  
                    closeResources();
            }

            // return the list
            return employees;
    }

    @Override
    public List<Employee> getEmployeesByFirstName(String firstName) {

                    List<Employee> employees = new ArrayList<>();

                    try {
                            connection = DAOUitilities.getConnection();
                            String sql = "SELECT * FROM EMPLOYEEJMASON WHERE em_name LIKE ?"; //

                            stmt = connection.prepareStatement(sql);

            
                            stmt.setString(1, "%" + firstName + "%");

                            ResultSet rs = stmt.executeQuery();

                            while (rs.next()) {
                                    Employee employee = new Employee();

                                    employee.setId(rs.getInt("em_id"));
                                    employee.setFirstName(rs.getString("em_first_name"));
                                    employee.setLastName(rs.getString("em_last_name"));
                                    employee.setEmail(rs.getString("em_email"));
                                    employee.setManagerId(rs.getInt("manager_id"));

                                    employees.add(employee);
                            }

                    } catch (SQLException e) {
                            e.printStackTrace();
                    } finally {

                            closeResources();
                    }

            return employees;
    }

    @Override
    public List<Employee> getEmployeesByLastName(String lastName) {
            List<Employee> employees = new ArrayList<>();

            try {
                    connection = DAOUitilities.getConnection();
                    String sql = "SELECT * FROM EMPLOYEEJMASON WHERE em_last_name LIKE ?";  // Note

                    stmt = connection.prepareStatement(sql);

                    stmt.setString(1, "%" + lastName + "%");

                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                            Employee employee = new Employee();

                            employee.setId(rs.getInt("em_id"));
                            employee.setFirstName(rs.getString("em_first_name"));
                            employee.setLastName(rs.getString("em_last_name"));
                            employee.setEmail(rs.getString("em_email"));
                            employee.setManagerId(rs.getInt("manager_id"));

                            employees.add(employee);
                    }

            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {

                    closeResources();
            }

            return employees;

    }

    @Override
    public Employee getEmployeeById(int id) {
            Employee employee = null;

            try {
                    connection = DAOUitilities.getConnection();
                    String sql = "SELECT * FROM EMPLOYEEJMASON WHERE em_id = ?";
                    stmt = connection.prepareStatement(sql);

                    stmt.setInt(1, id);

                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                            employee = new Employee();
                            employee.setId(rs.getInt("em_id"));
                            employee.setFirstName(rs.getString("em_first_name"));
                            employee.setLastName(rs.getString("em_last_name"));
                            employee.setEmail(rs.getString("em_email"));
                            employee.setManagerId(rs.getInt("manager_id"));
                    }

            } catch (SQLException e) {
                    e.printStackTrace();
            } finally {
                    closeResources();
            }

            return employee;
    }

    @Override
    public boolean addEmployee(Employee employee) {
            try {
                    connection = DAOUitilities.getConnection();
                    String sql = "INSERT INTO EMPLOYEEJMASON (em_id, em_first_name, em_last_name, em_email, manager_id) "
                                    + "VALUES (?, ?, ?, ?, ?)"; 
                    stmt = connection.prepareStatement(sql);

                    // setting before i execute
                    stmt.setInt(1, getNewPK());
                    stmt.setString(2, employee.getFirstName());
                    stmt.setString(3, employee.getLastName());
                    stmt.setString(4, employee.getEmail());
                    stmt.setInt(5, employee.getManagerId());

                    if (stmt.executeUpdate() != 0)
                            return true;
                    else
                            return false;

            } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
            } finally {
                    closeResources();
            }
    }

    @Override
    public boolean updateEmployee(Employee employee) {
            try {
                    connection = DAOUitilities.getConnection();
                    String sql = "UPDATE EMPLOYEEJMASON SET em_first_name=?, em_last_name=?,manager_id=?, em_email=? WHERE em_id=?";
                    stmt = connection.prepareStatement(sql);

                    stmt.setString(1, employee.getFirstName());
                    stmt.setString(2, employee.getLastName());
                    stmt.setInt(3, employee.getManagerId());
                    stmt.setString(4, employee.getEmail());
                    stmt.setInt(5, employee.getId());

                    System.out.println(stmt);

                    if (stmt.executeUpdate() != 0)
                            return true;
                    else
                            return false;

            } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
            } finally {
                    closeResources();
            }
    }

    @Override
    public boolean deleteEmployeeById(int id) {
            try {
                    connection = DAOUitilities.getConnection();
                    String sql = "DELETE EMPLOYEEJMASON WHERE emp_id=?";
                    stmt = connection.prepareStatement(sql);

                    stmt.setInt(1, id);

                    if (stmt.executeUpdate() != 0)
                            return true;
                    else
                            return false;

            } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
            } finally {
                    closeResources();
            }
    }

    private int getNewPK() throws SQLException {
            ResultSet rs=null;
            connectionPK = DAOUitilities.getConnection();
            String sql = "SELECT max(emp_id) as max FROM EMPLOYEEJMASON";
            int new_pk = 0;
            try {
                    stmtpk = connectionPK.createStatement();
                    rs=stmtpk.executeQuery(sql);
                    while(rs.next()){
                            new_pk = (rs.getInt("max"))+1;
                    }
            }
            catch (SQLException e) {e.printStackTrace();}
          finally{
                 if (stmtpk != null) {stmtpk.close();}
                 if (connectionPK != null) {connectionPK.close();}
        }
            return new_pk;

    }

  
            private void closeResources() {
                    try {
                            if (stmt != null)
                                    stmt.close();
                    } catch (SQLException e) {
                            System.out.println("Could not close statement!");
                            e.printStackTrace();
                    }

                    try {
                            if (connection != null)
                                    connection.close();
                    } catch (SQLException e) {
                            System.out.println("Could not close connection!");
                            e.printStackTrace();
                    }
            }

}

	
