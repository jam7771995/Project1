package com.revature.servlets;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.revature.dao.EmployeeDAO;
import com.revature.dao.ReimbursementDAO;
import com.revature.model.Employee;
import com.revature.model.ReimbursementRequest;
import com.revature.util.DAOUitilities;

/**
 * Servlet implementation class EmployeesServlet
 */

@WebServlet("/api/employees/*")
public class EmployeesServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

        /**
         * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
response)
         */
        protected void doGet(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
                EmployeeDAO dao = DAOUitilities.getEmployeeDAO();
                String path =
request.getRequestURI().substring(request.getContextPath().length());
//              getAll(): List<EmployeeDTO>
                if (path.equals("/api/employees")) {
                        List<Employee> allEmployees = dao.getAllEmployees();
                        response.getWriter().append(allEmployees.toString());
                } else if ((path.equals("/api/employees/id")) &&
(request.getParameter("emp_id") != null)) {
                        String emp_id = request.getParameter("emp_id");
                        int int_emp_id = Integer.parseInt(emp_id);
                        Employee employee = dao.getEmployeeById(int_emp_id);
                        response.getWriter().append("Found Employee:").append(employee.toString());
                }

        }

        /**
         * @see HttpServlet#doPost(HttpServletRequest request,
HttpServletResponse response)
         */
        protected void doPost(HttpServletRequest request, HttpServletResponse
response) throws ServletException, IOException {
//              put(employee): EmployeeDTO
                String path =
request.getRequestURI().substring(request.getContextPath().length());
                EmployeeDAO dao = DAOUitilities.getEmployeeDAO();
                //          create(employee): Employee
                if (path.equals("/api/employees/add")) {
                        Employee employee = new Gson().fromJson(request.getReader(),
Employee.class);
                        dao.addEmployee(employee);
                        response.getWriter().append("New Employee: ").append(employee.toString());
                }
        }

}