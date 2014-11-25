<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
var TfileUploadNum=1; //记录图片选择框个数
var Tnum=1; //ajax上传图片时索引
function TAddFileUpload()
{
	var idnum = TfileUploadNum+1;
	var str="<tr><td class=’tdClass’>图片"+idnum+"：</td>";
	str += "<td class=’tdClass’><input name=’’ size=’60’ id=’uploadImg"+idnum+"’ type=’file’ /><span id=’uploadImgState"+idnum+"’>";
	str += "</span></td></tr>";
	("#imgTable").append(str);
	TfileUploadNum += 1;
}
function TSubmitUploadImageFile()
{
	M("SubUpload").disabled=true;
	M("CancelUpload").disabled=true;
	M("AddUpload").disabled=true;
	setTimeout("TajaxFileUpload()",1000);//此为关键代码
}
</script>
<body>

<form action='/common/pic/uploadpic.jsp' method='post' enctype='multipart/form-data'>
<%-- 类型enctype用multipart/form-data，这样可以把文件中的数据作为流式数据上传，不管是什么文件类型，均可上传。--%>
请选择要上传的文件<input type='file' name='upfile' size='50'>
<input type='submit' value='提交'>


</form>



<table>
<tr><td class="tdClass">
图片1：
</td><td class="tdClass">
<input name="" size="60" id="uploadImg1" type="file" />
<span id="uploadImgState1"></span>
</td></tr>
</table>
<button id="SubUpload" class="ManagerButton" onClick="TSubmitUploadImageFile();return false;">确定上传</button>&nbsp;&nbsp;
<button id="CancelUpload" class="ManagerButton" onClick="javascript:history.go(-1);">取消</button>&nbsp;&nbsp;
<button id="AddUpload" class="ManagerButton" onClick="TAddFileUpload();return false;">增加</button>
</body>
</html>