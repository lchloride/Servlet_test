package example;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/Anime")
public class Anime extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	// JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/anime";

	// ���ݿ���û��������룬��Ҫ�����Լ�������
	static final String USER = "guest";
	static final String PASS = "guest";
	HashMap<String, String> column_name_trans;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Anime() {
		super();
		// TODO Auto-generated constructor stub
		column_name_trans =  new HashMap<String, String>();
		column_name_trans.put("anime_name", "��������");
		column_name_trans.put("start_time", "��ӳ����");
		column_name_trans.put("company", "������˾");
		column_name_trans.put("writer_name", "ԭ����");
		column_name_trans.put("episode_number", "����");
		column_name_trans.put("director_name", "����/�ල");
		column_name_trans.put("scriptwriter_name", "�籾");
		//column_name_trans.put("anime_name", "��������");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		// ������Ӧ��������
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter();

		try {
//			// ע�� JDBC ������
//			Class.forName("com.mysql.jdbc.Driver");
//
//			// ��һ������
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		    //��ʼ�����������ռ�
			try {
			    conn = getConn();
			}
			catch (NamingException e){
				e.printStackTrace();
			}catch (SQLException se) {
				se.printStackTrace();
			}
			
			// ִ�� SQL ��ѯ
			stmt = conn.createStatement();
			// request.getAttribute("query");
			String sql;
			sql = "SELECT * FROM anime";
			// sql = (String)request.getParameter("query");
			ResultSet rs = stmt.executeQuery(sql);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			List<String> column_names = new ArrayList<String>();
			for (int i = 0; i < rsmd.getColumnCount(); i++)
				column_names.add(column_name_trans.get(rsmd.getColumnName(i + 1)));
			
			List<List<Object>> db = new ArrayList<List<Object>>();
			List<Object> db_item;
			int count = 0;
			int page_idx = 1;
			int page_content_number = 5; 
			
			try {
				page_idx = Integer.parseInt(request.getParameter("page_idx"));
			}
			catch (NumberFormatException e) 	{
				//��ҳ������page_idx����Ч�����֣���ʱʹ��Ĭ��ֵ����
				System.out.println("page_idx ת��ʧ��");
			}
			try {
				page_content_number = Integer.parseInt(request.getParameter("page_content_number"));
			}
			catch (NumberFormatException e){
				System.out.println("page_content_number ת��ʧ��");
			}
			// չ����������ݿ�
			while (rs.next()) {
				// ͨ���ֶμ���
				if (count >= (page_idx - 1) * page_content_number && count <= page_idx * page_content_number - 1)

				{
					db_item = new ArrayList<Object>();

					for (int i = 0; i < rsmd.getColumnCount(); i++) {
						Object item = rs.getObject(i + 1);
						db_item.add(item);
					}
					db.add(db_item);
				}
				count++;
			}
			System.out.println(count);
			// ��ɺ�ر�
			rs.close();
			stmt.close();
			conn.close();
			request.setAttribute("QueryHeader", column_names);
			request.setAttribute("QueryResult", db);
			request.setAttribute("result", true);
			request.setAttribute("ResultPageCount", (int)Math.ceil((double)count/page_content_number));
			request.setAttribute("page_content_number",page_content_number);
			request.setAttribute("page_idx",page_idx);
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			System.out.println("forward to index.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException se) {
			// ���� JDBC ����
			se.printStackTrace();
		} catch (Exception e) {
			// ���� Class.forName ����
			e.printStackTrace();
			// out.println("���󣺲��ܼ������ݿ�</body></html>");
			request.setAttribute("Error", "���󣺲��ܼ������ݿ�");
		} finally {
			// ��������ڹر���Դ�Ŀ�
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	protected Connection getConn() throws NamingException, SQLException {
		Connection conn = null;
	    Context ctx = new InitialContext();  
	    //����java:/comp/envΪ�̶�·��   
	    Context envContext = (Context)ctx.lookup("java:/comp/env"); 
	    //����jdbc/mysqldsΪ����Դ��JNDI�󶨵�����
	    DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlds"); 
	    conn = ds.getConnection();     
	    //conn.close();     
	    System.out.println(conn.toString()+"<span style='color:red;'>JNDI���Գɹ�<span>");     

		return conn;
	}
	
	/*
	 * Generate sql query via settings from users in html
	 */
	protected String genSQL(HttpServletRequest request) {
		String sql = new String();

		return null;

	}
}
