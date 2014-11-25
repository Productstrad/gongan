<%@page import="service.LoginService"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
</head>
<script>
function logout(){
	$.ajax({
		type : "POST",
		url : "/sys/logout.do",			
		dataType : "text",
		success : function(result) {			
			if($.trim(result)=='1'){				
				window.top.location.href = '/sys/login.jsp';				
			}else{
				alert('系统错误');
			}	
		},
		error : function(request, error) {
			alert(error);
		}
	});
}
</script>
<body class="topp" >
<div id="headarea">
<div class="heard-logo fl"><h1>管理后台</h1></div>
<div class="heard-id fl">
<div class="idpic" style=" background:url(images/309f79052d21a.jpg)"><!-- <img src="/images/309f79052d21a.jpg"  /> --></div><h3><%=LoginService.getLoginAccount(request) %></h3></div>
<div class="heard-rig fr">上次登录时间：<%=LoginService.getLastLoginTime(request) %>   &nbsp;|&nbsp; <a href="javascript:void(0);" onclick="logout()">退出</a></div>
</div>
</body>
</html>