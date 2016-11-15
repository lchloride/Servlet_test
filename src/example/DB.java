package example;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DB {
	public static Connection getConn() throws NamingException, SQLException {
		Connection conn = null;
	    Context ctx = new InitialContext();  
	    //参数java:/comp/env为固定路径   
	    Context envContext = (Context)ctx.lookup("java:/comp/env"); 
	    //参数jdbc/mysqlds为数据源和JNDI绑定的名字
	    DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlds"); 
	    conn = ds.getConnection();     
	    System.out.println(conn.toString()+"<span style='color:red;'>JNDI测试成功<span>");     
	    
		return conn;
	}
	
	public static void closeConn(Connection conn) throws SQLException {
		conn.close();
	}
	
	
}
