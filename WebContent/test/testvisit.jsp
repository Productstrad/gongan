<%@page import="app.weixin.util.WeixinUtil"%>
<%@page import="app.weixin.pojo.AccessToken"%>
<%@page import="service.MembersService"%>
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
AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);
	MembersService.getInstance().visitRecord("o5l4es4rADwi-T2bYglmZHEO38W8",at.getToken());
%>
</body>
</html>