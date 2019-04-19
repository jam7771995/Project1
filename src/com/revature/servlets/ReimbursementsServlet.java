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

import java.io.BufferedReader;

import java.util.ArrayList;

@WebServlet("/api/reimbursement-requests/*")
public class ReimbursementsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReimbursementsServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ReimbursementDAO dao = DAOUitilities.getReimbursementRequestDAO();
		String path = request.getRequestURI().substring(request.getContextPath().length());

		if (path.equals("/api/reimbursement-requests/my/pending")) {
			List<ReimbursementRequest> requestsPending = dao.findPendingReimbursementRequest();
			response.getWriter().append(requestsPending.toString());
    
		} else if (path.equals("/api/reimbursement-requests/my/resolved")) {
			List<ReimbursementRequest> requestsResolved = dao.findResolvedReimbursementRequest();
			response.getWriter().append(requestsResolved.toString());
		} else if ((request.getParameter("reim_id") != null) && (path.equals("/api/reimbursement-requests/approve"))) {
			String appcode = request.getParameter("appcode");
			String reim_id = request.getParameter("rem_em_id");
			int int_reim_id = Integer.parseInt(reim_id);
			ReimbursementRequest rreq = dao.findReimbursementRequestByReimbursementId(int_reim_id);

			if (appcode.equals("1")) {

				if (dao.updateReimbursementRequest(rreq, 1)) {
					response.getWriter().append("Approved: ").append(rreq.toString());
				}
			} else if (appcode.equals("0")) {

				dao.updateReimbursementRequest(rreq, 0);
				response.getWriter().append("Denied: ").append(rreq.toString());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getRequestURI().substring(request.getContextPath().length());
		// create(request): ReimbursementRequest
		if (path.equals("/api/reimbursement-requests")) {
			ReimbursementRequest rreq = new Gson().fromJson(request.getReader(), ReimbursementRequest.class);
			DAOUitilities.getReimbursementRequestDAO().addReimbursementRequest(rreq);
			response.getWriter().append("New RREQ: ").append(rreq.toString());
		}

	}

}
