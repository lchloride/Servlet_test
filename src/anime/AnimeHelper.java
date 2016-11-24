package anime;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DatabaseAccess
 */
// @WebServlet("/AnimeHelper/*")
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
		Map<String, Object> para = new HashMap<String, Object>();
		Enumeration<?> paraNames = request.getParameterNames();
		String dispatchURL = "en_US/index.jsp";;
		
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String[] paraValues = request.getParameterValues(paraName);
			String paraValue = paraValues[0];
			para.put(paraName, new String(paraValue.getBytes("iso8859-1"), "UTF-8"));
		}
		System.out.println(para);
		if (para.size() > 0) {
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
				para.put("page_idx", 1);
			}
			am.setParameter(para);
			System.out.println(para);

			Object[] result = null;
			result = am.findAllAnime();
			for (Entry<String, Object> entry : para.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}

			request.setAttribute("QueryHeader", result[0]);
			request.setAttribute("QueryResult", result[1]);
			request.setAttribute("result", true);
			request.setAttribute("ResultPageCount",
					(int) Math.ceil((double) am.getAnimeRowsNumber() / am.getPageContentNumber()));
			request.setAttribute("SQL", result[2]);
		} else {
			request.setAttribute("result", false);
			Locale loc = request.getLocale();
			System.out.println(loc.toString()+" match="+loc.toString().matches("en_US|zh_CN|ja_JP"));
			if (loc.toString().matches("en_US|zh_CN|ja_JP"))
				dispatchURL = loc.toString()+"/index.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchURL);
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
