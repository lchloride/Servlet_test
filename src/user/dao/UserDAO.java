package user.dao;

import java.util.List;
import java.util.Map;

import db.DB;

public class UserDAO {
	public  List<Object> login(Map<String, String> para) {
		String sql = "select count(*) from user where username = '";
		sql  += para.get("Text_username");
		sql += "' and password = '";
		sql += para.get("Text_password");
		sql += "'";
		System.out.println(sql);
		return DB.execSQL(sql).get(0);
	}
}
