<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<title></title>
</head>
<body>
<form>
<table>
	<tr>
		<td>
		您喜欢上网吗？
		<input type="ratio" name="testOption" value="1"/>喜欢 <input type="ratio" name="testOption" value="0"/>不喜欢 
		</td>
	</tr>
	<input type="hidden" value="1" name="surveyId" id="surveyId" />
	<input type="submit" value="提交" />
</table>
</form>
</body>
</html>