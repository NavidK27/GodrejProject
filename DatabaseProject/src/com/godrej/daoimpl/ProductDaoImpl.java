package com.godrej.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.godrej.dao.ProductDao;
import com.godrej.model.Product;
import com.godrej.util.DbConnection;

public class ProductDaoImpl implements ProductDao{

	private static List<Product> pdtList = new ArrayList<Product>();
	private List<Product> tempList = new ArrayList<Product>();
	public List<Product> getPdtList() {
		return pdtList;
	}
	@Override
	public List<Product> search(int ch, String s) {
		tempList.clear();
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			if(stmt.executeUpdate("select count(1) where exists (select * from Product)") == 1)
				System.out.println("\nList Empty");
			else {
				int flag1=0;
				ResultSet rs;
				switch (ch) {
				case 1:
					rs = stmt.executeQuery("select * from Users Where EMAIL ='"+s+"'");
					while(rs.next()) {
						Product product = new Product();
						product.setProduct_Id(rs.getInt("PID"));
						product.setProduct_Name(rs.getString("NAME"));
						product.setProduct_Cat(rs.getString("CATEGORY"));
						product.setProduct_Price(rs.getInt("PRICE"));
						tempList.add(product);
						flag1=1;
					}
					break;
					//				for(Product product:pdtList)
					//					if(product.getProduct_Name().contains(s)) {
					//						tempList.add(product);
					//						ctr=1;
					//					}
				case 2:
					rs = stmt.executeQuery("select * from Users Where CATEGORY ='"+s+"'");
					while(rs.next()) {
						Product product = new Product();
						product.setProduct_Id(rs.getInt("PID"));
						product.setProduct_Name(rs.getString("NAME"));
						product.setProduct_Cat(rs.getString("CATEGORY"));
						product.setProduct_Price(rs.getInt("PRICE"));
						tempList.add(product);
						flag1=1;
					}
					break;
					//				for(Product product:pdtList)
					//					if(product.getProduct_Cat()==s) {
					//						tempList.add(product);
					//						ctr=1;
					//					}
				case 3:
					rs = stmt.executeQuery("select * from Users Where PRICE ='"+Integer.parseInt(s)+"'");
					while(rs.next()) {
						Product product = new Product();
						product.setProduct_Id(rs.getInt("PID"));
						product.setProduct_Name(rs.getString("NAME"));
						product.setProduct_Cat(rs.getString("CATEGORY"));
						product.setProduct_Price(rs.getInt("PRICE"));
						tempList.add(product);
						flag1=1;
					}
					break;
					//				for(Product product:pdtList)
					//					if(product.getProduct_Price()==Integer.parseInt(s)) {
					//						tempList.add(product);
					//						ctr=1;
					//					}
				default: System.out.println("\nNot Found");
				}
				if(flag1==0)
					System.out.println("\nNo record found");
				else
					return tempList;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				/*
				 * if(rs != null) { rs.close(); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public boolean insert(Product product) {
		int check;
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			stmt = null;
			stmt = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("insert into product values(seq_pdt.nextval,?,?,?)");
			if(stmt.executeUpdate("Select * from Product WHERE name = '"+ product.getProduct_Name()+"'")==0) {
				ps.setString(1, product.getProduct_Name());
				ps.setString(2, product.getProduct_Cat());
				ps.setInt(3, product.getProduct_Price());
				check = ps.executeUpdate();
				if(check==1)
					return true;
				else
					return false;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally {
			try {
				if(conn != null) {
					conn.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				/*
				 * if(rs != null) { rs.close(); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public List<Product> update(int id, String s, int option) {
		tempList.clear();
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			if(stmt.executeUpdate("select * from Product") >0) {
				PreparedStatement ps = null;
				switch(option)
				{
				case 1:
					ps = conn.prepareStatement("UPDATE PRODUCT SET NAME = ? WHERE PID = ?");
					ps.setString(1, s);
//					product.setProduct_Name(s);
					break;
				case 2:
					ps = conn.prepareStatement("UPDATE PRODUCT SET CATEGORY = ? WHERE PID = ?");
					ps.setString(1, s);
//					product.setProduct_Cat(s);
					break;
				case 3:
					ps = conn.prepareStatement("UPDATE PRODUCT SET PRICE = ? WHERE PID = ?");
					ps.setInt(1, Integer.parseInt(s));
//					product.setProduct_Price(Integer.parseInt(s));
					break;
				}
				ps.setInt(2,id);
				ps.executeQuery();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(conn != null && option == 5) {
					conn.close();
				}
				if(stmt != null && option == 5) {
					stmt.close();
				}
				/*
				 * if(rs != null) { rs.close(); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public boolean delete(int id) {
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			String sql = "DELETE from PRODUCT where PID ="+id;
			int i = stmt.executeUpdate(sql);
			if(i == 1)
				return true;
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
				/*
				 * if(rs != null) { rs.close(); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	@Override
	public List<Product> display() {
		tempList.clear();
		Connection conn = null;
		Statement stmt = null;
		try {
			DbConnection util = new DbConnection();
			conn = util.getConn();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from PRODUCT");
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_Id(rs.getInt("PID"));
				product.setProduct_Name(rs.getString("NAME"));
				product.setProduct_Cat(rs.getString("CATEGORY"));
				product.setProduct_Price(rs.getInt("PRICE"));
				tempList.add(product);
			}
			if(tempList.isEmpty())
				return null;
			else
				return tempList;
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
				/*
				 * if(rs != null) { rs.close(); }
				 */
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
