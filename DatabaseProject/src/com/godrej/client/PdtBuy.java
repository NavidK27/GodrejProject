  package com.godrej.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.godrej.util.DbConnection;
/**
 * Servlet that accesses database to insert products into cart
 * @author akashdy
 *
 */
@WebServlet("/PdtBuy")
public class PdtBuy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id;		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		HttpSession sess = request.getSession();
		try{
			if(!(boolean)sess.getAttribute("authenticated")==true || sess == null)
			response.sendRedirect("StartUpLogin");
		}catch(NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("StartUpLogin");
		}
		id = Integer.parseInt(request.getParameter("PID"));
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("insert into PRODUCTBOUGHT values(?,?)");
			ps.setInt(1, (Integer)request.getSession().getAttribute("ID"));
			ps.setInt(2, id);
			ps.executeUpdate();
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
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

