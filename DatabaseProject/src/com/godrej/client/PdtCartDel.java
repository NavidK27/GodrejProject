package com.godrej.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.godrej.util.DbConnection;

/**
 * Servlet that accesses database to delete product from cart
 * @author akashdy
 *
 */
@WebServlet("/PdtCartDel")
public class PdtCartDel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		try{
			if(!(boolean)sess.getAttribute("authenticated")==true || sess == null)
			response.sendRedirect("StartUpLogin");
		}catch(NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("StartUpLogin");
		}
		String x = request.getParameter("productCartDel");
		DbConnection util = new DbConnection();
		Connection conn = util.getConn();
		Statement stmt = null;
		int id =  (int) sess.getAttribute("ID");
		try {
			stmt = conn.createStatement();
			stmt.execute("delete FROM ProductBought WHERE USERID = "+id+"AND PID = "+x);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
