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
	
	<tr><td>调查问题:</td><td><input name="question" id="question" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>所属调查问卷:</td>
	<td>
	<label>${survey.title }</label>
	<input name="surveyId" id="surveyId" type="hidden" size="60" maxlength="50" value="${survey.id }"/>
	</td></tr>
	<tr><td>问题类型:</td><td>
		<select name="questionType" id="questionType">
			<option value="0">单选题</option>
			<option value="1">多选题</option>
			<option value="2">问答题</option>
			<option value="3">意见与建议</option>
		</select>	
	</td></tr>
	<tr><td>问题排序(值越小排越前面):</td><td><input name="questionOrder" id="questionOrder" type="text"  /></td></tr>
	
		
	<tr><td colspan="2" align="center"><a class="btn" href="javascript:void(0);" onclick="postForm('contentForm','addpost.do')">确定</a></td></tr>
</table>
</form>
</div>
</body>
</html>