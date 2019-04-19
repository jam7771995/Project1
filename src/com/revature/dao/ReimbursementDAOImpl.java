package com.revature.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.model.ReimbursementRequest;
import com.revature.util.DAOUitilities;

public class ReimbursementDAOImpl implements ReimbursementDAO {

        Connection connection = null;
        Connection connectionPK = null;
        PreparedStatement stmt = null;  

        Statement stmtpk = null;

        @Override
        public List<ReimbursementRequest> findAllReimbursementRequest() {
                List<ReimbursementRequest> rreq_list = new ArrayList<>();

                try {
                        connection = DAOUitilities.getConnection();      

                        String sql = "SELECT * FROM REQUSTJMASON";                    
                        stmt = connection.prepareStatement(sql);       


                        ResultSet rs = stmt.executeQuery();                     


                        while (rs.next()) {
                               

                                ReimbursementRequest rreq = new ReimbursementRequest();
                                int i = rs.getInt("rem_approved");

                                

                                rreq.setId(rs.getInt("rem_id"));
                                rreq.setName(rs.getString("rem_name"));
                                rreq.setDescription(rs.getString("rem_description"));
                                rreq.setAmount(rs.getDouble("rem_amount"));
                                rreq.setDate(rs.getDate("rem_date"));
                                        if (i==0) {
                                                rreq.setApproved(false);
                                        } else {
                                                rreq.setApproved(true);
                                        }
                                rreq.setEmpId(rs.getInt("rem_em_id"));
                     
                                rreq_list.add(rreq);
                        }
                        rs.close();

                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {

                        closeResources();
                }
                
                return rreq_list;
        }

        @Override
        public boolean addReimbursementRequest(ReimbursementRequest
reimbursementRequest) {
                try {
                        connection = DAOUitilities.getConnection();

                        String sql = "INSERT INTO REQUESTJMASON (rem_id, rem_name, rem_description, rem_amount, rem_date, rem_approved, rem_emp_id) "
                                        + "VALUES (?, ?, ?, ?, ?, ?, ?)"; 

                        stmt = connection.prepareStatement(sql);
                     
                        stmt.setInt(1, getNewPK());
                        stmt.setString(2, reimbursementRequest.getName());
                        stmt.setString(3, reimbursementRequest.getDescription());
                        stmt.setDouble(4, reimbursementRequest.getAmount());
                        java.sql.Date sql_StartDate = new java.sql.Date(
reimbursementRequest.getDate().getTime());
                        stmt.setDate(5, sql_StartDate);
                        if(reimbursementRequest.isApproved()==false) {
                                stmt.setInt(6, 0);
                        } else {
                                stmt.setInt(6, 1);
                        }
                        stmt.setInt(7, reimbursementRequest.getEmpId());



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
        public boolean updateReimbursementRequest(ReimbursementRequest
reimbursementRequest, int i) {

                try {
                        connection = DAOUitilities.getConnection();
                        String sql = "UPDATE REQUESTJMASON SET rem_name=?, rem_description=?,rem_amount=?, rem_date=?, rem_approved=?, rem_em_id=? WHERE rem_em_id=?";
                        stmt = connection.prepareStatement(sql);

                        stmt.setString(1, reimbursementRequest.getName());
                        stmt.setString(2, reimbursementRequest.getDescription());
                        stmt.setDouble(3, reimbursementRequest.getAmount());
                        stmt.setDate(4, (Date) reimbursementRequest.getDate());
                        stmt.setInt(5,  i);
                        stmt.setInt(6, reimbursementRequest.getEmpId());
                        stmt.setInt(7, reimbursementRequest.getId());

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
        public boolean deleteReimbursementRequestId(int id) {
                try {
                        connection = DAOUitilities.getConnection();
                        String sql = "DELETE REQUESTJMASON WHERE reim_id=?";
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

        @Override
        public List<ReimbursementRequest>
findReimbursementRequestByEmployeeId(int empId) {

                List<ReimbursementRequest> rreq_list = new ArrayList<>();

                try {
                        connection = DAOUitilities.getConnection();
                        String sql = "SELECT * FROM REQUESTJMASON WHERE rem_em_id LIKE ?";        //

                        stmt = connection.prepareStatement(sql);

                     
                        stmt.setString(1, Integer.toString(empId));

                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                                ReimbursementRequest rreq = new ReimbursementRequest();
                                int i = rs.getInt("approved");

                                
                                rreq.setId(rs.getInt("rem_id"));
                                rreq.setName(rs.getString("rem_name"));
                                rreq.setDescription(rs.getString("rem_description"));
                                rreq.setAmount(rs.getDouble("rem_amount"));
                                rreq.setDate(rs.getDate("rem_date"));
                                        if (i==0) {
                                                rreq.setApproved(false);
                                        } else {
                                                rreq.setApproved(true);
                                        }
                                rreq.setId(rs.getInt("rem_em_id"));
                                
                                rreq_list.add(rreq);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {

                        closeResources();
                }
                return rreq_list;
        }

        @Override
        public ReimbursementRequest findReimbursementRequestByReimbursementId(int
id) {

                ReimbursementRequest rreq = null;

                        try {
                                connection = DAOUitilities.getConnection();
                                String sql = "SELECT * FROM REQUESTJMASON WHERE rem_em_id = ?";
                                stmt = connection.prepareStatement(sql);

                                stmt.setString(1, Integer.toString(id));

                                ResultSet rs = stmt.executeQuery();

                                if (rs.next()) {
                                        int i = rs.getInt("rem_approved");
                                        rreq = new ReimbursementRequest();
                                        rreq.setId(rs.getInt("rem_id"));
                                        rreq.setName(rs.getString("rem_name"));
                                        rreq.setDescription(rs.getString("rem_description"));
                                        rreq.setAmount(rs.getDouble("rem_amount"));
                                        rreq.setDate(rs.getDate("rem_date"));
                                        rreq.setEmpId(rs.getInt("rem_em_id"));
                                        if (i==0) {
                                                rreq.setApproved(false);
                                        } else {
                                                rreq.setApproved(true);
                                        }
                                }

                        } catch (SQLException e) {
                                e.printStackTrace();
                        } finally {
                                closeResources();
                        }

                        return rreq;
                }



        @Override
        public List<ReimbursementRequest> findPendingReimbursementRequest() {

                List<ReimbursementRequest> rreq_list = new ArrayList<>();

                try {
                        connection = DAOUitilities.getConnection();
                        String sql = "SELECT * FROM REQUESTJMASON WHERE rem_approved LIKE ?";      //

                        stmt = connection.prepareStatement(sql);

                     
                        stmt.setString(1, Integer.toString(0));

                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                                ReimbursementRequest rreq = new ReimbursementRequest();
                                int i = rs.getInt("rem_approved");

                             
                                rreq.setId(rs.getInt("rem_id"));
                                rreq.setName(rs.getString("rem_name"));
                                rreq.setDescription(rs.getString("rem_description"));
                                rreq.setAmount(rs.getDouble("rem_amount"));
                                rreq.setDate(rs.getDate("rem_em_date"));
                                        if (i==0) {
                                                rreq.setApproved(false);
                                        } else {
                                                rreq.setApproved(true);
                                        }
                                rreq.setEmpId(rs.getInt("rem_em_id"));
                                
                                rreq_list.add(rreq);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {

                        closeResources();
                }
                return rreq_list;
        }

        @Override
        public List<ReimbursementRequest> findResolvedReimbursementRequest() {
                List<ReimbursementRequest> rreq_list = new ArrayList<>();

                try {
                        connection = DAOUitilities.getConnection();
                        String sql = "SELECT * FROM REQUESTJMASON WHERE rem_approved LIKE ?";      //

                        stmt = connection.prepareStatement(sql);

                        stmt.setString(1, Integer.toString(1));

                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {
                                ReimbursementRequest rreq = new ReimbursementRequest();
                                int i = rs.getInt("rem_approved");

                                
                                rreq.setId(rs.getInt("rem_id"));
                                rreq.setName(rs.getString("rem_name"));
                                rreq.setDescription(rs.getString("rem_description"));
                                rreq.setAmount(rs.getDouble("rem_amount"));
                                rreq.setDate(rs.getDate("rem_date"));
                                        if (i==0) {
                                                rreq.setApproved(false);
                                        } else {
                                                rreq.setApproved(true);
                                        }
                                rreq.setEmpId(rs.getInt("rem_em_id"));
                                
                                rreq_list.add(rreq);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                } finally {

                        closeResources();
                }
                return rreq_list;
        }

        private int getNewPK() throws SQLException {
                ResultSet rs=null;
                connectionPK = DAOUitilities.getConnection();
                String sql = "SELECT max(rem_id) as max FROM REQUESTJMASON";
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
                        if (connectionPK != null) {connectionPK.close();}
                        if (connection != null)
                                connection.close();
                } catch (SQLException e) {
                        System.out.println("Could not close connection!");
                        e.printStackTrace();
                }
        }

}



	