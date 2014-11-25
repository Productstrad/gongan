<%@page import="java.net.URLEncoder"%>
<%@page import="app.weixin.util.MessageUtil"%>
<%@page import="service.QuestionrecordService"%>
<%@page import="util.ParamUtil"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
	String q=ParamUtil.getParameter(request, "q","户口丢了怎么补办？");
	out.print(URLEncoder.encode(q, "utf-8"));
	out.print(new QuestionrecordService().autoAnswer(q, "sdfs", "fgdf", MessageUtil.RESP_MESSAGE_TYPE_TEXT));
%>
</body>
</html>