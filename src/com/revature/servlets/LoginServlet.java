package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.dao.LoginDAO;
import com.revature.dao.LoginDAOImpl;
import com.revature.model.LoginModel;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WebContent.index.html").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LoginDAO login_dao= new LoginDAOImpl();
		LoginModel logger = login_dao.getLoginByUser(username);
		
		PrintWriter pass = response.getWriter();
		response.setContentType("text/html");
		
		HttpSession ses = request.getSession();
				
				if (username.equals(logger.getUsername()) && pass.equals(logger.getPassword())) {
					ses.setAttribute("em_username", username);
					ses.setAttribute("em_id", logger.getId());
					response.sendRedirect("choose?id=" + logger.getId());
				} else {
					response.sendRedirect("loginfail");
				}
			}

{}

}

	


