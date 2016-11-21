package example;

import java.util.*;
import java.util.Map.Entry;

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
		column_name_trans.put("anime_name", "��������");
		column_name_trans.put("start_time", "��ӳ����");
		column_name_trans.put("company", "������˾");
		column_name_trans.put("writer_name", "ԭ����");
		column_name_trans.put("episode_number", "����");
		column_name_trans.put("director_name", "����/�ල");
		column_name_trans.put("scriptwriter_name", "�籾");
		column_name_trans.put("character", "��ɫ");
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
		for (Entry<String, Object> entry : parameter.entrySet()) {
			para.put(entry.getKey(), entry.getValue().toString());
		}
		para.put("limit_st", String.valueOf(page_content_number * (page_idx - 1)));
		para.put("page_content_number", String.valueOf(page_content_number));

		Object[] query_result = dao.find(para);
		column_name = ((List<String>) query_result[0]);
		//System.out.println(column_name);
		refineColName(column_name, parameter);
		anime_list = (List<Anime>) query_result[1];

		// ���ɱ������
		anime_form = formatResult(anime_list, parameter);
		//
		if (anime_list.size() > 0)
			anime_rows_number = anime_list.get(0).getRowNum();
		else
			anime_rows_number = 0;

		// ���췵��ֵ
		result = new Object[3];
		result[0] = column_name;
		result[1] = anime_form;
		result[2] = dao.sql;
		return result;
	}

	public List<List<Object>> formatResult(List<Anime> anime, Map<String, Object> para) {
		List<List<Object>> db = new ArrayList<List<Object>>();

		// չ����������ݿ�
		for (Anime anime_item : anime) {
			db.add(anime_item.format(para));
		}
		return db;
	}

	protected void refineColName(List<String> src, Map<?, ?> para) {
		for (int i=0; i<src.size(); i++) {
			if (para.containsKey("Checkbox_"+src.get(i)))
				src.set(i, column_name_trans.get(src.get(i)));
			else {
				src.remove(i);
				i--;
			}
		}
	}
}
