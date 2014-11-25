<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/easyui/themes/chansy/easyui.css" type="text/css"></link>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/listoperfordopage.js"></script>
</head>

<body>
<div align="center">
<form action="updatepost.do" method="post" id="contentForm">
<table align="center" border="0" style="padding-top:15px;">
<tr><td>账号名:</td><td><input name="account_set" id="account_set" type="text" size="60" maxlength="50" value="${vo.account }"/></td></tr>
<tr><td>真实姓名:</td><td><input name="realName_set" id="realName_set" type="text" size="60" maxlength="50" value="${vo.realName }"/></td></tr>
<tr><td>性别:</td><td>
<select name="sex_set" id="sex_set">
	<option value="1" <c:if test="${vo.sex=='1'}">selected</c:if>>男</option>
	<option value="0" <c:if test="${vo.sex=='0'}">selected</c:if>>女</option>
</select>
</td></tr>
<tr><td>联系电话:</td><td><input name="tel_set" id="tel_set" type="text" size="60" maxlength="50" value="${vo.tel }"/></td></tr>
<tr><td>email:</td><td><input name="email_set" id="email_set" type="text" size="60" maxlength="50" value="${vo.email }"/></td></tr>
<tr><td>qq:</td><td><input name="qq_set" id="qq_set" type="text" size="60" maxlength="50" value="${vo.qq }"/></td></tr>
<tr><td>创建时间:</td><td><label>${vo.createDate }</label></td></tr>
<tr><td>最后登录时间:</td><td><label>${vo.lastLoginTime }</label></td></tr>
<tr><td>密码（修改才填）:</td><td><input name="password_set" id="password_set" type="password" size="60" maxlength="50"/></td></tr>
<!-- <tr><td>1正常0隐藏-1删除:</td><td><input name="status_set" id="status_set" type="text" size="60" maxlength="50" value="${vo.status }"/></td></tr>
 -->
<tr>
	<td colspan="2" align="center">
		<input type="hidden" id="id" name="id" value="${vo.id}">
		<a class="btn" href="javascript:void(0);" onclick="postForm('contentForm','updatepost.do')">确定</a>
	</td>
</tr>
</table>
</form>
</div>
</body>
</html>