<%@page import="service.LoginService"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
</head>
<%
	if(LoginService.onlineCheck(request, response)){
		response.sendRedirect("frame.jsp");
		return;
	}
%>
<script>
function login(){
	$.ajax({
		type : "POST",
		url : "/sys/login.do",
		data : {
			'account' : $("#account").val(),
			'passWord':$("#passWord").val(),
			'authImgContent':$("#authImgContent").val(),
			'auto':$("#auto").val()
		},
		dataType : "text",
		success : function(result) {			
			if($.trim(result)=='1'){				
				window.location.href = '/sys/frame.jsp';				
			}else{
				alert(result);
			}	
		},
		error : function(request, error) {
			alert(error);
		}
	});
}

function getAuthImg(){
	$("#authImg").attr('src','/authimg/getauthimg.do?rnd='+Math.random()); 
}
</script>
<body>
<div id="headarea">
  <div class="case">
    <div class="top">
      <div class="logoarea">
        <h1>用户登录</h1>
      </div>
    </div>
    </div></div>
    <div class="head_fd"></div>
<div class="case loginadbg">
<div class="log_l"></div>
<div class="log_r">
<div class="loginarea">
<div class="bg"></div>
<div class="w"><img src="/images/loginw.gif"/></div>
<div class="k" style="boder-top:null;">

<div class="col">
<input name="account" id="account" type="text" class="inp" value="请输入用户名" onclick="this.value=''"/></div>
<div class="col">
<input name="passWord" id="passWord" type="password" class="inp" value="请输入密码" onclick="this.value=''"/></div>
<div class="col"><input name="authImgContent" id="authImgContent" type="text" class="inp1" value="请输入验证码" onclick="this.value=''"/>&nbsp;<img id="authImg" src="/authimg/getauthimg.do?rnd=<%=new Date() %>" onclick="getAuthImg()"/></div>
<div class="col">
<a href="javascript:void(0);" class="btn_login" onclick="login()">登 录</a>
</div>
<div class="col">
<input id="auto" type="checkbox" checked="checked" value="1" name="auto">两周内自动登录
</div>
<!-- <div class="col"><span><a href="#">忘记密码</a>？</span></div> -->
</div>
</div>
</div>
</div>
<script type="text/javascript" src="js/foot.js"></script>
</body>
</html>
