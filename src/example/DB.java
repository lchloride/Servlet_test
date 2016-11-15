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
	    //����java:/comp/envΪ�̶�·��   
	    Context envContext = (Context)ctx.lookup("java:/comp/env"); 
	    //����jdbc/mysqldsΪ����Դ��JNDI�󶨵�����
	    DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlds"); 
	    conn = ds.getConnection();     
	    System.out.println(conn.toString()+"<span style='color:red;'>JNDI���Գɹ�<span>");     
	    
		return conn;
	}
	
	public static void closeConn(Connection conn) throws SQLException {
		conn.close();
	}
	
	
}
