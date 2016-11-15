package example;

import java.io.IOException;
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
			Object[] result = null;
			result = am.findAllAnime();
			request.setAttribute("QueryHeader", result[0]);
			request.setAttribute("QueryResult", result[1]);
			request.setAttribute("result", true);
			//request.setAttribute("ResultPageCount", (int)Math.ceil((double)count/page_content_number));
			//request.setAttribute("page_content_number",page_content_number);
			//request.setAttribute("page_idx",page_idx);
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
