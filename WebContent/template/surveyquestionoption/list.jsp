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
	$("<div><iframe id='dialogIframe' src='add.do?questionId=${question.id}' width='500px' height='200px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "添加" });	
}
function edit(id){	
	$("<div><iframe id='dialogIframe' src='update.do?id="+id+"' width='500px' height='220px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "编辑" });	
}
</script>
<body>
</div>
<div id="mainarea">
  <div class="k itable">
    <div class="hd"><h3>列表信息&nbsp;&nbsp;所属问题：${question.question }</h3><a class="operButton" href="/sys/surveyquestion/list.do?surveyId=${question.surveyId }">返回问题列表</a><a class="operButton" href="javascript:add();">添加答案</a></div>
<div class="bd">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listtable">
  <tr class="tth">
    <td>id</td><td>问题答案</td><td>答案分值</td><td>答案排序(值越小排越前面)</td>     
    <td >操作</td>  
  </tr>
  <c:forEach items="${list}" var="vo" varStatus="i">
	<c:choose>
		<c:when test="${i.index%2==0 }"><tr class="ttd"></c:when>
		<c:otherwise><tr class="ttd2"></c:otherwise>
	</c:choose>
		<td>${vo.id }</td>
		<td>${vo.optionCotent }</td>
		<td>${vo.optionScore }</td>		
		<td>${vo.optionOrder }</td>		
				
		<td align="center">
		<a href="javascript:edit(${vo.id});" class="table_edit">编辑</a>&nbsp;
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
