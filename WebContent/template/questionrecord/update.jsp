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
<tr><td>id:</td><td><input name="id_set" id="id_set" type="text" size="60" maxlength="50" value="${vo.id }"/></td></tr>
<tr><td>提问者:</td><td><input name="weixincode_set" id="weixincode_set" type="text" size="60" maxlength="50" value="${vo.weixincode }"/></td></tr>
<tr><td>createDate:</td><td><input name="createDate_set" id="createDate_set" type="text" size="60" maxlength="50" value="${vo.createDate }"/></td></tr>
<tr><td>问题:</td><td><input name="question_set" id="question_set" type="text" size="60" maxlength="50" value="${vo.question }"/></td></tr>
<tr><td>自动回复的答案Id(autoanswer表id),为空说明需要人工回复:</td><td><input name="autoAnswerId_set" id="autoAnswerId_set" type="text" size="60" maxlength="50" value="${vo.autoAnswerId }"/></td></tr>
<tr><td>人工回复，备用:</td><td><input name="answer_set" id="answer_set" type="text" size="60" maxlength="50" value="${vo.answer }"/></td></tr>
<tr><td>0=隐藏，-1删除:</td><td><input name="status_set" id="status_set" type="text" size="60" maxlength="50" value="${vo.status }"/></td></tr>

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