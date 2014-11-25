<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/easyui/themes/chansy/easyui.css" type="text/css"></link>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/listoperfordopage.js"></script>
<script charset="utf-8" src="/editor4/kindeditor.js"></script>
<script charset="utf-8" src="/editor4/lang/zh_CN.js"></script>
</head>
<script>
function selectItemi(itemIId){
	$.ajax({ url: "/sys/itemii/getitemII.do?itemIId="+itemIId, context: document.body, success: function(result){
		$('#itemII_set').html(result);
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
	<tr><td>标题:</td><td><input name="title_set" id="title_set" type="text" size="60" maxlength="50" value="${vo.title }"/></td></tr>
	<tr><td>一级栏目:</td>
	<td>
	<select name="itemI_set" id="itemI_set" onchange="selectItemi(this.value)">
		<c:forEach items="${itemis}" var="item">
			<option value="${item.id }" <c:if test="${item.id==vo.itemI }">selected</c:if>>${item.itemIname }</option>
		</c:forEach>
	</select>	
	</td></tr>
	<tr><td>二级栏目:</td>
	<td>
	<select name="itemII_set" id="itemII_set">
		<option value="${vo.itemII }">${vo.itemIIName }</option>
	</select>
	</td></tr>
	<tr><td>新闻内容:</td>
	<td>
		<textarea id="content_set" name="content_set" style="width:815px;height:334px;">${vo.content }</textarea>
		<script>    
		    KindEditor.ready(function(K) {
		            window.editor = K.create('#content_set');
		    });
		</script>
	</td></tr>
	<tr><td>图片地址:</td>
	<td>
		<input name="picpath_set" id="picpath_set" type="text" size="70"  value="${vo.picpath }"/>
		<input name="uploadPicBtn" id="uploadPicBtn" type="button" value="上传图片" onclick="uploadPic()" />
	</td></tr>
	<tr><td>音/视频地址:</td>
	<td>
		<input name="mediapath_set" id="mediapath_set" type="text" size="70"  value="${vo.mediapath }"/>
		<input name="uploadMediaBtn" id="uploadMediaBtn" type="button" value="上传音/视频" onclick="uploadMedia()" />
	</td></tr>
	<tr><td>新闻跳转地址:</td><td><input name="jumpURL_set" id="jumpURL_set" type="text" size="60" maxlength="50" value="${vo.jumpURL }" /></td></tr>		
	<tr><td>新闻类型:</td>
	<td>
	<select name="newsType_set" id="newsType_set">
		<option value="1" <c:if test="${vo.newsType==1 }">selected</c:if>>文本</option>
		<option value="2" <c:if test="${vo.newsType==2 }">selected</c:if>>图片</option>
		<option value="3" <c:if test="${vo.newsType==3 }">selected</c:if>>语音</option>
		<option value="4" <c:if test="${vo.newsType==4 }">selected</c:if>>视频</option>
	</select>	
	</td></tr>	
	<tr><td>状态:</td>
	<td>
	<select name="status_set" id="status_set">
		<option value="0" <c:if test="${vo.status==0 }">selected</c:if>>待审核</option>
		<option value="1" <c:if test="${vo.status==1 }">selected</c:if>>已发布</option>		
	</select>	
	</td></tr>	
	<tr><td colspan="2" align="center">
	<input type="hidden" id="id" name="id" value="${vo.id}">
	<a class="btn" href="javascript:void(0);" onclick="editor.sync();postFormNofresh('contentForm','updatepost.do')">确定</a></td></tr>
</table>
</form>
</div>
</body>
</html>