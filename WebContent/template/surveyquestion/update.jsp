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

<tr><td>调查问题:</td><td><input name="question_set" id="question_set" type="text" size="60" maxlength="50" value="${vo.question }"/></td></tr>

<tr><td>问题类型:</td>
<td>
	<select name="questionType_set" id="questionType_set">
			<option value="0" <c:if test="${vo.questionType==0 }">selected</c:if>>单选题</option>
			<option value="1" <c:if test="${vo.questionType==1 }">selected</c:if>>多选题</option>
			<option value="2" <c:if test="${vo.questionType==2 }">selected</c:if>>问答题</option>
			<option value="3" <c:if test="${vo.questionType==3 }">selected</c:if>>意见与建议</option>
		</select>
</td></tr>
<tr><td>问题排序(值越小排越前面):</td><td><input name="questionOrder_set" id="questionOrder_set" type="text" value="${vo.questionOrder }"/></td></tr>


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