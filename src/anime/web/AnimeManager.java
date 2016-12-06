package anime.web;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import anime.Anime;
import anime.dao.AnimeDAO;
import tool.PropertyOper;

public class AnimeManager {
	private static AnimeManager anime_manager = new AnimeManager();
	private Map<String, String> column_name_trans;
	private Map<String, String> parameter = null;
	private AnimeDAO dao = null;
	final int BASIC_PARA_COUNT = 2;
	final int DEFAULT_PAGE_CONTENT_NUMBER = 5;
	final int DEFAULT_PAGE_IDX = 1;

	public AnimeManager() {
		// TODO Auto-generated constructor stub
		dao = new AnimeDAO();
	}

	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public static AnimeManager getInstance() {
		return anime_manager;
	}

	@SuppressWarnings("unchecked")
	// Find all anime core operation, all its logic and processing methods are
	// declared here
	public Map<String, Object> findAllAnime() {
		List<Anime> anime_list = null;
		List<String> column_name = null;
		List<List<Object>> anime_form = null;
		boolean isLogin = false;
		if (parameter.get("isLogin").contentEquals("True"))
			isLogin = true;
		
		// Condition that para.size() is large than 0 means there are several
		// parameters transferred from index.jsp
		// and project should process this query; if para.size() is equal to 0,
		// it means that there are no valid parameters
		// from index.jsp, which is usually under the circumstance of first file
		// loading process
		if (parameter.size() > BASIC_PARA_COUNT) {
			Map<String, String> para = new HashMap<>();
			int page_content_number = DEFAULT_PAGE_CONTENT_NUMBER, page_idx = DEFAULT_PAGE_IDX;

			// Form a new parameter list to DAO
			for (Entry<String, String> entry : parameter.entrySet()) {
				para.put(entry.getKey(), entry.getValue().toString());
			}

			try {
				page_content_number = Integer.parseInt(parameter.get("page_content_number"));
			} catch (NumberFormatException e) {
				// TODO: handle exception
				page_content_number = DEFAULT_PAGE_CONTENT_NUMBER;
			}
			try {
				page_idx = Integer.parseInt(parameter.get("page_idx"));
			} catch (NumberFormatException e) {
				// TODO: handle exception
				page_idx = DEFAULT_PAGE_IDX;
			}
			para.put("limit_st", String.valueOf(page_content_number * (page_idx - 1)));
			para.put("page_content_number", String.valueOf(page_content_number));

			// Call DAO to process finding operation with proper parameters
			Object[] query_result = dao.find(para);

			// Process query results
			// Format names of each column in specific language
			column_name = ((List<String>) query_result[0]);
			refineColName(column_name, parameter, parameter.get("location"));

			// Format displaying result content
			anime_list = (List<Anime>) query_result[1];
			anime_form = formatResult(anime_list, parameter);

			// Obtain total row number of query process
			int anime_rows_number = 0;
			if (anime_list.size() > 0)
				anime_rows_number = anime_list.get(0).getRowNum();
			else
				anime_rows_number = 0;

			// Format a map to store all parameters sending back to index.jsp
			return formatFindAllResult(true, "index.jsp", column_name, anime_form,
					(int) Math.ceil((double) anime_rows_number / page_content_number), page_content_number, page_idx,
					isLogin, dao.sql);
		} else {
			// Format a map to store all parameters sending back to
			// "loc+index.jsp", when first calling this method
			return formatFindAllResult(false, (String) parameter.get("location") + "/index.jsp", isLogin);
		}
	}

	public List<List<Object>> formatResult(List<Anime> anime, Map<String, String> para) {
		List<List<Object>> db = new ArrayList<List<Object>>();

		// 展开结果集数据库
		for (Anime anime_item : anime) {
			db.add(anime_item.format(para));
		}
		return db;
	}

	/*
	 * This method aims to format a proper column names in specific language
	 * 
	 * @param src means original column names from database query result
	 * 
	 * @param loc is a language string that is satisfied the rules of ISO639-1
	 * 
	 * @return void
	 */
	protected void refineColName(List<String> src, Map<?, ?> para, String loc) {
		try {
			column_name_trans = PropertyOper.GetAllProperties("StringTable_" + loc + ".properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < src.size(); i++) {
			if (para.containsKey("Checkbox_" + src.get(i)))
				src.set(i, column_name_trans.get(src.get(i)));
			else {
				src.remove(i);
				i--;
			}
		}
	}

	/*
	 * This method is to put useful parameters into a map for sending back to
	 * controller which is responsible to send them to front side
	 * 
	 * @param result_flag is an indicator of whether there is a series of query
	 * results or not
	 * 
	 * @param dispatchURL points out the front page that will display the result
	 * 
	 * @param col_name is the column names of result form. Note that col_name
	 * should always have the same amount of column of result form
	 * 
	 * @param query_result stores the content of result form
	 * 
	 * @param page_result_count indicates the number of query result
	 * 
	 * @param page_content and page_idx help front side to display result in
	 * several pages
	 * 
	 * @param sql represents the query sentence, only for debug
	 * 
	 * @return a map, storing all the parameters that will be sent to front side
	 */
	private Map<String, Object> formatFindAllResult(Boolean result_flag, String dispatchURL, List<String> col_name,
			List<List<Object>> query_result, int page_result_count, int page_content, int page_idx, boolean isLogin, String sql) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		result.put("DispatchURL", dispatchURL);
		result.put("QueryHeader", col_name);
		result.put("QueryResult", query_result);
		result.put("ResultPageCount", page_result_count);
		result.put("page_content_number", page_content);
		result.put("page_idx", page_idx);
		result.put("isLogin", isLogin);
		result.put("SQL", sql);
		return result;
	}

	/*
	 * This method is an overload of previous method, and with same function
	 * Their difference is that this method is called when there is no valid
	 * parameters obtained from front side
	 * 
	 * @param result_flag is an indicator of whether there is a series of query
	 * results or not. Although it accepts user's assignment, it should be false
	 * 
	 * @param dispatchURL points out the front page that will display the result
	 */
	private Map<String, Object> formatFindAllResult(Boolean result_flag, String dispatchURL, boolean isLogin) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", false);
		result.put("DispatchURL", dispatchURL);
		result.put("ResultPageCount", 0);
		result.put("isLogin", isLogin);
		return result;
	}
}
