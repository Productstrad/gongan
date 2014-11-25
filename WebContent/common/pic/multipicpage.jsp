<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
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
function TajaxFileUpload()
{
	if(Tnum<TfileUploadNum+1)
	{
	//准备提交处理
	("#uploadImgState"+Tnum).html("<img src=../images/loading.gif />");
	//开始提交
	$.ajax
	({
	type: "POST",
	url:"http://localhost/ajaxText2/Handler1.ashx",
	data:{upfile:("#uploadImg"+Tnum).val(),category:("#pcategory").val()},
	success:function (data, status)
	{
	//alert(data);
	var stringArray = data.split("|");
	
	if(stringArray[0]=="1")
	{
	//stringArray[0] 成功状态(1为成功，0为失败)
	//stringArray[1] 上传成功的文件名
	//stringArray[2] 消息提示
	("#uploadImgState"+Tnum).html("<img src=../images/note_ok.gif />");//+stringArray[1]+"|"+stringArray[2]);
	} 
	else
	{
	//上传出错
	("#uploadImgState"+Tnum).html("<img src=../images/note_error.gif />"+stringArray[2]);//+stringArray[2]+"");
	}
	Tnum++;
	setTimeout("TSubmitUploadImageFile()",0);
	}
	}); 
	}
}
</script>
<body>
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