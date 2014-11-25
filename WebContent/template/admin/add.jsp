<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/easyui/themes/chansy/easyui.css" type="text/css"></link>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/listoperfordopage.js"></script>
</head>
<body>
<div align="center">
<form action="addpost.do" method="post" id="contentForm">
<table align="center" border="0" style="padding-top:15px;">	
	<tr><td>账号名:</td><td><input name="account" id="account" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>密码:</td><td><input name="password" id="password" type="password" size="60" maxlength="50" /></td></tr>
	<tr><td>真实姓名:</td><td><input name="realName" id="realName" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>性别:</td><td><select name="sex" id="sex"><option value="1">男</option><option value="0">女</option></select></tr>
	<tr><td>联系电话:</td><td><input name="tel" id="tel" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>email:</td><td><input name="email" id="email" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>qq:</td><td><input name="qq" id="qq" type="text" size="60" maxlength="50" /></td></tr>
		
	<tr><td colspan="2" align="center"><a class="btn" href="javascript:void(0);" onclick="postForm('contentForm','addpost.do')">确定</a></td></tr>
</table>
</form>
</div>
</body>
</html>