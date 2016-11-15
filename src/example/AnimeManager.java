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

	public AnimeManager() {
		// TODO Auto-generated constructor stub
		column_name_trans = new HashMap<String, String>();
		column_name_trans.put("anime_name", "��������");
		column_name_trans.put("start_time", "��ӳ����");
		column_name_trans.put("company", "������˾");
		column_name_trans.put("writer_name", "ԭ����");
		column_name_trans.put("episode_number", "����");
		column_name_trans.put("director_name", "����/�ල");
		column_name_trans.put("scriptwriter_name", "�籾");
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
			// ��ȡ���ݿ����Ӳ�ִ��SQL��ѯ
			conn = DB.getConn();
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM anime";
			rs = stmt.executeQuery(sql);

			List<Anime> anime_list = null;
			List<String> column_name = null;
			List<List<Object>> anime_form = null;
			
			// �����õ�����
			try {
				anime_list = processResult(rs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			// ���ɱ�ͷ
			try {
				column_name = formatMetaData(rs);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			// ���ɱ������
			anime_form = formatResult(anime_list);
			
			// ���췵��ֵ
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
				anime_item.setAnimeName("��Ч����");
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
				anime_item.setCompany("��Ч����");
			}
			try {
				anime_item.setWriterName(rs.getString("writer_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setWriterName("��Ч����");
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
				anime_item.setDirectorName("��Ч����");
			}
			try {
				anime_item.setScriptwriterName(rs.getString("scriptwriter_name"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				anime_item.setScriptwriterName("��Ч����");
			}
			anime_list.add(anime_item);
		}
		return anime_list;

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

		// չ����������ݿ�
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
