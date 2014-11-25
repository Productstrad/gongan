<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
</head>
<script>
function postForm(formId,postUrl){	
	$.ajax({
			type : "POST",
			url : postUrl,
			data : $('#'+formId).serialize(),
			dataType : "text",
			success : function(result) {
				var jsonObj=eval("("+result+")");
				if(jsonObj.suc>0){
					alert('操作成功');					
				}else{
					if(jsonObj.msg==null){
						alert('操作失败');
					}else{
						alert('操作失败:'+jsonObj.msg);
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
<form id="mediaForm" action='/common/media/uploadmedia.jsp' method='post' enctype='multipart/form-data'>
	<table align="center" border="0" style="padding-top:15px;">
		<tr>
			<td>音/视频名称：</td>
			<td>
			<input name="mediaTitle" size="20" id="mediaTitle" type="text" />			
			</td>
		</tr>
		<tr>
			<td>音/视频：</td>
			<td>
			<input name="uploadImg1" size="60" id="uploadImg1" type="file" />
			<!-- <input type="button" value="上传" onclick="postForm('picForm','/common/pic/uploadpic.jsp')"/> -->			
			</td>
		</tr>
		<tr><td colspan="2" align="center"><a class="btn" href="javascript:document.getElementById('mediaForm').submit();" >上传</a></td></tr>
	</table>
</form>
</body>
</html>