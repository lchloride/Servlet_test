package anime.dao;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.*;

import javax.naming.NamingException;

import anime.Anime;
import db.DB;

public class AnimeDAO implements Store<Anime>{
	public String sql = null; 
	/*
	 * This method is use to generate proper SQL query sentences and calls executing method in DB class
	 * @param para is parameters obtained from AnimeManager
	 * @return an object array which storing query column names, query result content and query SQL
	 * SQL is formed with following parts:
	 *     "SELECT *, FOUND_ROWS() FROM (" +
	 *     "SELECT * FROM anime WHERE 1=1 AND "+
	 *     each parameter's key + " like " + "%" + each parameter's value + "%" +
	 *     ") T" +
	 *     "limit " + start index + ", " + page content number
	 */
	public Object[] find(Map<String, String> para) {
		
		sql = new String("SELECT *");
		sql += " FROM anime WHERE 1=1 AND ";
		for (Entry<String, String> entry  : para.entrySet()) {
			if (entry.getKey().contains("Text_"))
				sql += entry.getKey().substring("Text_".length()) + " like '%" + entry.getValue() + "%' AND ";
		}
		sql = sql.substring(0, sql.lastIndexOf("AND"));	
		sql = "SELECT *, FOUND_ROWS() FROM ("+sql+") T ";
		sql += "limit "+para.get("limit_st")+", "+para.get("page_content_number");
		System.out.println("querySQL:"+sql);

		// TODO add exec query sentence
		Object[] anime_list = DB.execSQL(sql, new AnimeDAO());
		return anime_list;
		
	}

	/*
	 * (non-Javadoc)
	 * @see anime.dao.Store#format(java.util.List)
	 * This method is the implementation of format interface, which formats query result from DB as a specific object  
	 */
	@Override
	public Anime format(List<Object> list) {
		// TODO Auto-generated method stub
		Anime anime = new Anime();
		anime.setAnimeName((String) list.get(0));
		anime.setStartTime((Date) list.get(1));
		anime.setCompany((String) list.get(2));
		anime.setWriterName((String) list.get(3));
		anime.setEpisodeNumber((int) list.get(4));
		anime.setDirectorName((String) list.get(5));
		anime.setScriptwriterName((String) list.get(6));
		anime.setRowNum(((Long)list.get(7)).intValue());
		return anime;
	}
	
	/*
	 * This method can get the total number of anime table, however, it has not been used yet.
	 */
	public int getRowsNumber(String table_name) throws NamingException, SQLException {
		String sql = "select ROWS_NUM from info where TABLE_NAME=\""+table_name+"\";";
		List<Object> result = DB.execSQL(sql);
		return (int) result.get(0);
	}

}
