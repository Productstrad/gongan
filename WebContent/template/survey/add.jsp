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
<script language="javascript" type="text/javascript" src="/My97DatePicker/WdatePicker.js"></script>
<link href="/My97DatePicker/skin/default/datepicker.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div align="center">
<form action="addpost.do" method="post" id="contentForm">
<table align="center" border="0" style="padding-top:15px;">		
	<tr><td>调查名称:</td><td><input name="title" id="title" type="text" size="60" maxlength="50" /></td></tr>	
	<tr><td>调查开始时间:</td>
	<td>	
	<input type="text" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',maxDate:'#F{$dp.$D(\'endDate\')}'})" class="Wdate" style="width:91px;" id="startDate" name="startDate">
	</td></tr>
	<tr><td>调查结束时间:</td>
	<td>
	<input type="text" onfocus="WdatePicker({doubleCalendar:true,dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" class="Wdate" style="width:91px;" name="endDate" id="endDate">
	</td></tr>		
	<tr><td colspan="2" align="center"><a class="btn" href="javascript:void(0);" onclick="postForm('contentForm','addpost.do')">确定</a></td></tr>
</table>
</form>
</div>
</body>
</html>