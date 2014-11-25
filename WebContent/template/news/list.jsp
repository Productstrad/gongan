<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="/easyui/themes/chansy/easyui.css" type="text/css"></link>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/js/listoperfordopage.js"></script>
</head>
<script type="text/javascript">
function add(){	
	$("<div><iframe id='dialogIframe' src='add.do' width='500px' height='350px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "添加" });	
}
function edit(id){	
	$("<div><iframe id='dialogIframe' src='update.do?id="+id+"' width='500px' height='350px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "编辑" });	
}
function sendNews(id){	
	if(confirm("您每月只能发推送4条新闻,确定推送？")){
		$.ajax({
				type : "POST",
				url : "/sys/news/send.do",
				data : "id="+id,
				dataType : "text",
				success : function(result) {
					alert(result);		
				},
				error : function(request, error) {
					alert(error);
				}
		});	
	}
}
</script>
<body>
</div>
<div id="mainarea">
  <div class="k itable">
    <div class="hd"><h3>列表信息</h3><a class="operButton" href="add.do">发布新闻</a></div>
<div class="bd">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listtable">
  <tr class="tth">
    <td>新闻id</td><td>标题</td><td>一级栏目</td><td>二级栏目</td><td>发布人账号id</td><td>状态</td><td>发布时间</td><td>点击数</td>     
    <td >操作</td>  
  </tr>
  <c:forEach items="${list}" var="vo" varStatus="i">
	<c:choose>
		<c:when test="${i.index%2==0 }"><tr class="ttd"></c:when>
		<c:otherwise><tr class="ttd2"></c:otherwise>
	</c:choose>
		<td>${vo.id }</td>
		<td><a href="/news/read.do?id=${vo.id }" target="_blank">${vo.title }</a></td>
		<td>${vo.itemIName }</td>
		<td>${vo.itemIIName }</td>		
		<td>${vo.account }</td>
		<td>
			<c:if test="${vo.status==1 }">已发布</c:if>
			<c:if test="${vo.status==0 }">待审核</c:if>
		</td>
		<td>${vo.createDate }</td>		
		<td>${vo.clicks }</td>		
		<td align="center">
		<a href="update.do?id=${vo.id}" class="table_edit">编辑</a>&nbsp;
		<c:if test="${vo.tuisongFlag!=1 }">
			<a href="javascript:sendNews(${vo.id });" class="table_edit">推送</a>&nbsp;
		</c:if>
		<a href="javascript:doAction('delete','${vo.id }');">删除</a>
		</td>	
	</tr>	
	</c:forEach>  
</table>
<%@ include file="/template/common/page.jsp" %>
</div>
<div class="f bg"></div>
</div>
</div>
</body>
</html>
