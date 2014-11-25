<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
</head>
<script type="text/javascript">
function send(){
	$.ajax({
		type : "POST",
		url : "manualreplypost.do",
		data : {
			'content' : encodeURI($("#content").val()),
			'id':$("#id").val(),
			'touser':$("#weixincode").val()
		},
		dataType : "text",
		success : function(result) {
			var jsonObj=eval("("+result+")");
			if(jsonObj.errcode==0){
				alert("回复成功");
			}else{
				if(jsonObj.errcode==45015){
					alert("已超过48小时，不能回复");
				}else{
					alert(jsonObj.errmsg);
				}
			}
		},
		error : function(request, error) {
			alert(error);
		}
	});
}
</script>
<body>
<table>
<tr>
<td>内容</td><td><textarea name="content" id="content" type="text" cols="50" rows="10"></textarea></td>
</tr>
<tr>
<td colspan="2" style="text-align:center">
<input type="hidden" id="id" value="${id }"/>
<input type="hidden" id="weixincode" value="${weixincode }" />
<input type="button" onclick="send()" value="回复"/>
</td>
</tr>
</table>
</body>
</html>