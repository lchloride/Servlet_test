package anime;

import java.sql.SQLException;
import java.util.*;
import java.util.Map.*;

import javax.naming.NamingException;

public class AnimeDA implements Store<Anime>{
	public String sql = null; 
	protected Object[] find(Map<String, String> para) {
		//String sql = null;
		sql = new String("SELECT *");
//		for (Entry<String, String> entry : para.entrySet()) {
//			//System.out.println(entry.getKey()+" "+entry.getValue());
//			if (entry.getKey().contains("Checkbox_") && entry.getValue().equalsIgnoreCase("on")) {
//				sql += entry.getKey().substring(9)+",";
//			}
//			System.out.println(sql);
//		}
//		int last_comma = sql.lastIndexOf(',');
//		if (last_comma == -1)
//			sql += "*";
//		else
//			sql = sql.substring(0,  sql.lastIndexOf(','));
		
		if (para.get("Checkbox_character")=="on")
			sql += " FROM animeView WHERE 1=1 AND ";
		else
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
		Object[] anime_list = DB.execSQL(sql, new AnimeDA());
		return anime_list;
		
	}

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
	
	public int getRowsNumber(String table_name) throws NamingException, SQLException {
		String sql = "select ROWS_NUM from info where TABLE_NAME=\""+table_name+"\";";
		List<Object> result = DB.execSQL(sql);
		return (int) result.get(0);
	}

}
