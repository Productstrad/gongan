<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>

<script>
function openUploadPicDialog(){	
	$("<div><iframe id='dialogIframe' src='/common/pic/uploadpicpage.jsp' width='428px' height='250px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "上传图片" });	
}
</script>
<body>
<input type="text" id="pic" />
<input type="button" onclick="openUploadPicDialog()" value="上传图片"/>
</body>
</html>