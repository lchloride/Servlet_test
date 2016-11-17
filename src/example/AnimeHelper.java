package example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/AnimeHelper")
public class AnimeHelper extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnimeHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			AnimeManager am = AnimeManager.getInstance();
//			List<String> column_name = null;
//			List<List<Object>> db = null;
			Map<String, Object> para = new HashMap<String, Object>();
			para.put("anime_name", new String(request.getParameter("anime_name").getBytes("iso8859-1"), "UTF-8"));
			para.put("product_company", new String(request.getParameter("product_company").getBytes(), "UTF-8"));
			para.put("writer_name", new String(request.getParameter("writer_name").getBytes(), "UTF-8"));
			
			try {
				para.put("page_content_number", Integer.parseInt(request.getParameter("page_content_number"))); 				
			} catch (NumberFormatException e) {
				// TODO: handle exception
				para.put("page_content_number", 5);
			}
			try {
				para.put("page_idx", Integer.parseInt(request.getParameter("page_idx"))); 
			} catch (NumberFormatException e) {
				// TODO: handle exception
				para.put("page_idx",1);
			}
			am.setParameter(para);
			
			Object[] result = null;
			result = am.findAllAnime();
			request.setAttribute("QueryHeader", result[0]);
			request.setAttribute("QueryResult", result[1]);
			request.setAttribute("result", true);
			request.setAttribute("ResultPageCount", (int)Math.ceil((double)am.getAnimeRowsNumber()/am.getPageContentNumber()));
			request.setAttribute("page_content_number",am.getPageContentNumber());
			request.setAttribute("page_idx",am.getPageIndex());
			RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
			System.out.println("forward to index.jsp");
			dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
