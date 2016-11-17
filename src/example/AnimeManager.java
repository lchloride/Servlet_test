package example;

import java.util.*;

public class AnimeManager {
	private static AnimeManager anime_manager = new AnimeManager();
	private Map<String, String> column_name_trans;
	private Map<String, Object> parameter = null;
	private int page_content_number;
	private int page_idx;
	private int anime_rows_number;
	private AnimeDA dao = null;

	public AnimeManager() {
		// TODO Auto-generated constructor stub
		page_content_number = 5;
		page_idx = 1;
		dao = new AnimeDA();

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

	public int getPageIndex() {
		return page_idx;
	}

	public int getAnimeRowsNumber() {
		return anime_rows_number;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
		page_content_number = (int) parameter.get("page_content_number");
		page_idx = (int) parameter.get("page_idx");
	}

	public static AnimeManager getInstance() {
		return anime_manager;
	}

	@SuppressWarnings("unchecked")
	public Object[] findAllAnime() {
		Object[] result = null;

		List<Anime> anime_list = null;
		List<String> column_name = null;
		List<List<Object>> anime_form = null;
		
		Map<String, String> para = new HashMap<>();
		para.put("anime_name", (String) parameter.get("anime_name"));
		para.put("company", (String) parameter.get("product_company"));
		para.put("writer_name", (String) parameter.get("writer_name"));
		para.put("limit_st", String.valueOf(page_content_number * (page_idx - 1)));
		para.put("page_content_number", String.valueOf(page_content_number));
		
		Object[] query_result = dao.find(para);
		column_name = (List<String>) query_result[0];
		anime_list = (List<Anime>) query_result[1];
		
		// 生成表格主体
		anime_form = formatResult(anime_list);
		
		anime_rows_number = anime_list.size();

		// 构造返回值
		result = new Object[2];
		result[0] = column_name;
		result[1] = anime_form;

		return result;
	}

	public List<List<Object>> formatResult(List<Anime> anime) {
		List<List<Object>> db = new ArrayList<List<Object>>();
		// List<Object> db_item;

		// 展开结果集数据库
		for (Anime anime_item : anime) {
			// db_item = new ArrayList<Object>();
			db.add(anime_item.format());
		}
		return db;
	}
}
