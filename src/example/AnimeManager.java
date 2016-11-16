package example;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import javax.naming.NamingException;

public class AnimeManager {
	private static AnimeManager anime_manager = new AnimeManager();
	private Map<String, String> column_name_trans;
	private int page_content_number;
	private int page_index;
	private int anime_rows_number;

	public AnimeManager() {
		// TODO Auto-generated constructor stub
		page_content_number = 5;
		page_index = 1;
		
		column_name_trans = new HashMap<String, String>();
		column_name_trans.put("anime_name", "动漫名称");
		column_name_trans.put("start_time", "上映日期");
		column_name_trans.put("company", "制作公司");
		column_name_trans.put("writer_name", "原作者");
		column_name_trans.put("episode_number", "集数");
		column_name_trans.put("director_name", "导演/监督");
		column_name_trans.put("scriptwriter_name", "剧本");
	}

	public int getPageContentNumber() {
		return page_content_number;
	}

	public void setPageContentNumber(int page_content_number) {
		this.page_content_number = page_content_number;
	}

	public int getPageIndex() {
		return page_index;
	}

	public void setPageIndex(int page_index) {
		this.page_index = page_index;
	}

	public int getAnimeRowsNumber() {
		return anime_rows_number;
	}

	public static AnimeManager getInstance() {
		return anime_manager;
	}

	public Object[] findAllAnime() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		Object[] result = null;
		try {
			// 获取数据库链接并执行SQL查询
			conn = DB.getConn();
			stmt = conn.createStatement();
			String sql;
			sql = genSQL();
			rs = stmt.executeQuery(sql);

			List<Anime> anime_list = null;
			List<String> column_name = null;
			List<List<Object>> anime_form = null;
			
			// 处理获得的数据
			try {
				anime_list = processResult(rs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			// 生成表头
			try {
				column_name = formatMetaData(rs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			// 生成表格主体
			anime_form = formatResult(anime_list);
			
			//获得表的总行数
			getRowsNumber(conn);
			
			// 构造返回值
			result = new Object[2];
			result[0] = column_name;
			result[1] = anime_form;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				DB.closeConn(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Anime> processResult(ResultSet rs) throws SQLException {
		List<Anime> anime_list = new ArrayList<Anime>();
		Anime anime_item;
		while (rs.next()) {
			anime_item = new Anime();
			try {
				anime_item.setAnimeName(rs.getString("anime_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setAnimeName("无效的域");
			}
			try {
				anime_item.setStartTime(rs.getDate("start_time"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
				Date date = null;
				try {
					date = sdf.parse("0000-00-00");
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				anime_item.setStartTime(date);
			}
			try {
				anime_item.setCompany(rs.getString("company"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setCompany("无效的域");
			}
			try {
				anime_item.setWriterName(rs.getString("writer_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setWriterName("无效的域");
			}
			try {
				anime_item.setEpisodeNumber(rs.getInt("episode_number"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setEpisodeNumber(-1);
			}
			try {
				anime_item.setDirectorName(rs.getString("director_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setDirectorName("无效的域");
			}
			try {
				anime_item.setScriptwriterName(rs.getString("scriptwriter_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setScriptwriterName("无效的域");
			}
			anime_list.add(anime_item);
		}
		return anime_list;

	}

	private String genSQL() {
		String sql = null;
		System.out.println(page_content_number+", "+page_index);
		sql = "select * from anime limit "+page_content_number*(page_index-1)+","+page_content_number;
		System.out.println(sql);
		return sql;
	}
	
	private void getRowsNumber(Connection conn) throws NamingException, SQLException {
		if (conn == null)
			conn = DB.getConn();
		Statement stmt = conn.createStatement();
		String sql = "select ROWS_NUM from info where TABLE_NAME=\"anime\";";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next())
			anime_rows_number = rs.getInt("ROWS_NUM");
		else
			anime_rows_number = page_content_number;
	}
	
	public List<String> formatMetaData(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		List<String> column_names = new ArrayList<String>();
		for (int i = 0; i < rsmd.getColumnCount(); i++)
			column_names.add(column_name_trans.get(rsmd.getColumnName(i + 1)));
		return column_names;
	}

	public List<List<Object>> formatResult(List<Anime> anime) {
		List<List<Object>> db = new ArrayList<List<Object>>();
		// List<Object> db_item;

		// 展开结果集数据库
		for (Anime anime_item : anime) {
			// db_item = new ArrayList<Object>();
			db.add(anime_item.format());
			// db_item.add(anime_item.getAnimeName());
			// db_item.add(anime_item.getStartTime());
			// db_item.add(anime_item.getCompany());
			// db_item.add(anime_item.getCompany());
			// db_item.add(anime_item.getWriterName());
			// db_item.add(anime_item.getEpisodeNumber());
			// db_item.add(anime_item.getDirectorName());
			// db_item.add(anime_item.getScriptwriterName());
			// db.add(db_item);
		}
		return db;
	}
	
}
