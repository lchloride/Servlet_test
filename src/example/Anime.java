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

	
	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/anime";

	// 数据库的用户名与密码，需要根据自己的设置
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
		column_name_trans.put("anime_name", "动漫名称");
		column_name_trans.put("start_time", "上映日期");
		column_name_trans.put("company", "制作公司");
		column_name_trans.put("writer_name", "原作者");
		column_name_trans.put("episode_number", "集数");
		column_name_trans.put("director_name", "导演/监督");
		column_name_trans.put("scriptwriter_name", "剧本");
		//column_name_trans.put("anime_name", "动漫名称");
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection conn = null;
		Statement stmt = null;
		// 设置响应内容类型
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter();

		try {
//			// 注册 JDBC 驱动器
//			Class.forName("com.mysql.jdbc.Driver");
//
//			// 打开一个连接
//			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		    //初始化查找命名空间
			try {
			    conn = getConn();
			}
			catch (NamingException e){
				e.printStackTrace();
			}catch (SQLException se) {
				se.printStackTrace();
			}
			
			// 执行 SQL 查询
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
				//网页传来的page_idx是无效的数字，此时使用默认值即可
				System.out.println("page_idx 转换失败");
			}
			try {
				page_content_number = Integer.parseInt(request.getParameter("page_content_number"));
			}
			catch (NumberFormatException e){
				System.out.println("page_content_number 转换失败");
			}
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
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
			// 完成后关闭
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
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
			// out.println("错误：不能加载数据库</body></html>");
			request.setAttribute("Error", "错误：不能加载数据库");
		} finally {
			// 最后是用于关闭资源的块
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
	    //参数java:/comp/env为固定路径   
	    Context envContext = (Context)ctx.lookup("java:/comp/env"); 
	    //参数jdbc/mysqlds为数据源和JNDI绑定的名字
	    DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlds"); 
	    conn = ds.getConnection();     
	    //conn.close();     
	    System.out.println(conn.toString()+"<span style='color:red;'>JNDI测试成功<span>");     

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
