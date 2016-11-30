<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>AnimeHelper</title>
</head>
<body>

	<%
		Locale loc = request.getLocale();
		//out.println(loc.toString());
		if (loc.toString().compareTo("zh_CN") == 0) {
			out.println("检测到语言为中文，重定向至中文页面......");
			String site = new String("zh_CN");
			// response.setStatus(response.SC_MOVED_TEMPORARILY);
			//response.setHeader("Location", site);
			response.sendRedirect(site);
		} else if (loc.toString().compareTo("ja_JP") == 0) {
			out.println("日本語の言葉を検出、日本語ページまでリダイレクトします......");
			String site = new String("ja_JP");
			// response.setStatus(response.SC_MOVED_TEMPORARILY);
			//response.setHeader("Location", site);
			response.sendRedirect(site);
		} else {
			out.println("Detect English as language, redirect to English page......");
			String site = new String("en_US");
			String contextPath = request.getContextPath() + site;
			//request.setAttribute("contextPath", contextPath);
			//System.out.println(contextPath);
			//response.setStatus(response.SC_MOVED_TEMPORARILY);
			//response.setHeader("Location", site);
			response.sendRedirect(site);
		}
	%>
</body>
</html>