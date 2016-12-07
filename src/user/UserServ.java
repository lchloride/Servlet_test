package user;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
// @WebServlet("/UserServ")
public class UserServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int DEFAULT_PARA_COUNT = 2;//DEFAULT_PARA_COUNT indicates the number of default parameters obtained by system

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServ() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Create a session if it does not exist
		HttpSession session = request.getSession(true);
		//Object that manages user
		UserManager um = UserManager.getInstance();
		//Parameters sent to UserManager
		Map<String, String> para = new HashMap<String, String>();
		//Name list that get from front side
		Enumeration<?> paraNames = request.getParameterNames();
		//Default diapatching URL
		String dispatchURL = "loginsucc.jsp";

		Locale loc = request.getLocale();
		// If user assign display language, just load matched one, not display
		// in default language
		String[] request_url = request.getRequestURI().split("/");
		for (int i = 0; i < request_url.length; i++) {
			if (request_url[i].matches("[a-zA-Z]{2,8}_([a-zA-Z]{2}|[0-9]{3})")) {
				String[] lang = request_url[i].split("_");
				loc = new Locale(lang[0], lang[1]);
				break;
			}
		}

		// Put all parameters from request into a Map transferred to
		// UserManager
		while (paraNames.hasMoreElements()) {
			String paraName = (String) paraNames.nextElement();
			String[] paraValues = request.getParameterValues(paraName);
			String paraValue = paraValues[0];
			para.put(paraName, new String(paraValue.getBytes("iso8859-1"), "UTF-8"));
		}
		System.out.println(para);

		// Set language of request to parameter in order to load proper language
		// resources. Language must be one of English(America),
		// Chinese(Simplified) and Japanese(Japan). If it cannot be matched,
		// default language is English(America).
		if (loc.toString().matches("en_US|zh_CN|ja_JP"))
			para.put("location", loc.toString());
		else
			para.put("location", "en_US");

		// Transfer parameters to UserManager
		um.setParameter(para);

		Map<String, Object> result = new HashMap<>();

		// Obtain operation string
		String query_url = request.getServletPath();
		query_url = query_url.substring(query_url.lastIndexOf('/')+1);
		
		if (query_url.contentEquals("login")) {//Login operation
			System.out.println("login " + session.getAttribute("username") + " " + session.getAttribute("password")
					+ " " + session.getId());
			
			//Do nothing but redirect to successful login page if the user has login the site
			if (!session.isNew()
					&& UserManager.checkLogin(session.getAttribute("username"), session.getAttribute("password"))) {
				result.put("DispatchURL", "loginsucc.jsp");
			} else { 
				//Check whether username and password input by user are matched 
				if (para.size() > DEFAULT_PARA_COUNT) {
					result = um.login();
					
					//Set username and password to a session if login is accepted
					if (result.containsKey("username") && result.containsKey("password")) {
						session.setAttribute("username", result.get("username"));
						session.setAttribute("password", result.get("password"));
						System.out.println("set session");
					}

					// Set query result as several attributes to response
					for (Entry<String, Object> entry : result.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
				} else { // It is the first time to display login page, just forward to it without any process
					result.put("DispatchURL", "login.jsp");
				}
			}
		} else if (query_url.contentEquals("logout")) { // Logout operation, disable its session and redirect
			session.invalidate();
			response.sendRedirect(request.getHeader("Referer")==null?"index.jsp":request.getHeader("Referer"));
			return;
		} else {//wrong parameter
			return;
		}

		// Forward result to assigned page
		dispatchURL = (String) result.get("DispatchURL");
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
