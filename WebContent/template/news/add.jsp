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
<script charset="utf-8" src="/editor4/kindeditor.js"></script>
<script charset="utf-8" src="/editor4/lang/zh_CN.js"></script>
</head>
<script>
function addNews(formId,postUrl){	
	$.ajax({
			type : "POST",
			url : postUrl,
			data : $('#'+formId).serialize(),
			dataType : "text",
			success : function(result) {
				var jsonObj=eval("("+result+")");
				if(jsonObj.suc>0){
					alert('发布成功');
					window.location.href = window.location.href;
				}else{
					if(jsonObj.msg==null){
						alert('发布失败');
					}else{
						alert('发布失败:'+jsonObj.msg);
					}					
				}			
			},
			error : function(request, error) {
				alert(error);
			}
	});	
}
function selectItemi(itemIId){
	$.ajax({ url: "/sys/itemii/getitemII.do?itemIId="+itemIId, context: document.body, success: function(result){		
		if($.trim(result)==''){
			alert('请先发布该一级 栏目下的二级栏目');
		}
		$('#itemII').html(result);
      }});	
}
function uploadPic(){
	upPicDialog=$("<div><iframe id='dialogIframe' src='/common/pic/singlepicpage.jsp' width='350px' height='120px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "上传图片" });
}

function uploadMedia(){
	upMediaDialog=$("<div><iframe id='dialogIframe' src='/common/media/singlemediapage.jsp' width='350px' height='120px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "上传音/视频" });
}

</script>
<body>
<div align="center">
<form action="addpost.do" method="post" id="contentForm">
<table align="center" border="0" style="padding-top:15px;">		
	<tr><td>标题:</td><td><input name="title" id="title" type="text" size="60" maxlength="50" /></td></tr>
	<tr><td>一级栏目:</td>
	<td>
	<select name="itemI" id="itemI" onchange="selectItemi(this.value)">
		<c:forEach items="${itemis}" var="item">
			<option value="${item.id }">${item.itemIname }</option>
		</c:forEach>
	</select>	
	</td></tr>
	<tr><td>二级栏目:</td>
	<td>
	<select name="itemII" id="itemII">
	</select>
	</td></tr>
	<tr><td>新闻内容:</td>
	<td>
		<textarea id="content" name="content" style="width:815px;height:334px;"></textarea>
		<script>    
		    KindEditor.ready(function(K) {
		            window.editor = K.create('#content');
		    });
		</script>
	</td></tr>
	<tr><td>图片地址:</td>
	<td>
		<input name="picpath" id="picpath" type="text" size="70" />		
		<input name="uploadPicBtn" id="uploadPicBtn" type="button" value="上传图片" onclick="uploadPic()" />
	</td></tr>
	<tr><td>音/视频地址:</td>
	<td>
		<input name="mediapath" id="mediapath" type="text" size="70"  />
		<input name="uploadMediaBtn" id="uploadMediaBtn" type="button" value="上传音/视频" onclick="uploadMedia()" />
	</td></tr>
	<tr><td>新闻跳转地址:</td><td><input name="jumpURL" id="jumpURL" type="text" size="60" maxlength="50" /></td></tr>		
	<tr><td>新闻类型:</td>
	<td>
	<select name="newsType" id="newsType">
		<option value="1">文本</option>
		<option value="2">图片</option>
		<option value="3">语音</option>
		<option value="4">视频</option>
	</select>	
	</td></tr>	
		
	<tr><td colspan="2" align="center"><a class="btn" href="javascript:void(0);" onclick="editor.sync();addNews('contentForm','addpost.do')">确定</a></td></tr>
</table>
</form>
</div>
</body>
</html>