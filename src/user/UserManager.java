package user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.dao.UserDAO;

public class UserManager {
	private static UserManager user_manager = new UserManager();
	private Map<String, String> parameter = null;
	private static UserDAO dao = new UserDAO();

	public UserManager() {
		// TODO Auto-generated constructor stub
		// dao = new UserDAO();
	}

	public void setParameter(Map<String, String> parameter) {
		this.parameter = parameter;
	}

	public static UserManager getInstance() {
		return user_manager;
	}

	/*
	 * This method is to process user's login operation
	 * 
	 * @return parameters map that ready to send back to front side
	 */
	public Map<String, Object> login() {
		Map<String, Object> result = new HashMap<>();
		List<Object> query_result = dao.login(parameter);
		if ((long) query_result.get(0) == 1) { // Login successfully
			result.put("isLogin", true);
			result.put("username", parameter.get("Text_username"));
			result.put("password", parameter.get("Text_password"));
			result.put("LastURL", parameter.get("LastURL"));
			result.put("DispatchURL", "loginsucc.jsp");
		} else { // Login refused
			result.put("isLogin", false);
			result.remove("username");
			result.remove("password");
			result.put("DispatchURL", "login.jsp");
		}

		return result;
	}

	/*
	 * A static tool method to check whether specified username and password are
	 * matched or not. This method will be called at each operation in this
	 * website
	 * 
	 * @param username is the one to be checked
	 * 
	 * @param password is the one to be checked
	 * 
	 * @return the result of checking
	 */
	public static boolean checkLogin(String username, String password) {
		Map<String, String> para = new HashMap<>();
		para.put("Text_username", username);
		para.put("Text_password", password);
		List<Object> query_result = dao.login(para);
		if ((long) query_result.get(0) == 1)
			return true;
		else
			return false;
	}

	/*
	 * This method is the overriding of the above one, with the same mechanism.
	 * 	@param username is the one to be checked
	 * 
	 * @param password is the one to be checked
	 * 
	 * @return the result of checking
	 */
	public static boolean checkLogin(Object username, Object password) {
		Map<String, String> para = new HashMap<>();
		if (username == null || password == null)
			return false;
		else {
			para.put("Text_username", (String) username);
			para.put("Text_password", (String) password);
			List<Object> query_result = dao.login(para);
			if ((long) query_result.get(0) == 1)
				return true;
			else
				return false;
		}
	}
}
