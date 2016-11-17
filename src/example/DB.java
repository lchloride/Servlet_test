package example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {
	public static Connection getConn() throws NamingException, SQLException {
		Connection conn = null;
		Context ctx = new InitialContext();
		// 参数java:/comp/env为固定路径
		Context envContext = (Context) ctx.lookup("java:/comp/env");
		// 参数jdbc/mysqlds为数据源和JNDI绑定的名字
		DataSource ds = (DataSource) envContext.lookup("jdbc/mysqlds");
		conn = ds.getConnection();
		System.out.println(conn.toString() + "<span style='color:red;'>JNDI测试成功<span>");

		return conn;
	}

	public static void closeConn(Connection conn) throws SQLException {
		conn.close();
	}

	public static <T> Object[] execSQL(String sql, Store<T> method) {
		List<T> form = new ArrayList<T>();
		List<String> col_name = new ArrayList<>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (method == null)
				throw new Exception("Invalid Format Method in DB.java");
			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				col_name.add(rsmd.getColumnName(i));

			while (rs.next()) {
				List<Object> item = new ArrayList<>();
				T item_obj;
				for (int i = 1; i <= rsmd.getColumnCount(); i++)
					item.add(rs.getObject(i));
				item_obj = method.format(item);
				form.add(item_obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Object[] result = new Object[2];
		result[0] = col_name;
		result[1] = form;
		return result;
	}

	// 只查询一个向量
	public static List<Object> execSQL(String sql) {
		List<Object> result = new ArrayList<Object>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConn();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			ResultSetMetaData rsmd = rs.getMetaData();

			rs.next();
			for (int i = 1; i <= rsmd.getColumnCount(); i++)
				result.add(rs.getObject(i));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

}
