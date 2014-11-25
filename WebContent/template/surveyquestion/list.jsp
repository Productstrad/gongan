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
	$("<div><iframe id='dialogIframe' src='add.do?surveyId=${survey.id}' width='500px' height='200px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "添加" });	
}
function edit(id){	
	$("<div><iframe id='dialogIframe' src='update.do?id="+id+"' width='500px' height='160px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "编辑" });	
}
</script>
<body>
</div>
<div id="mainarea">
  <div class="k itable">
    <div class="hd"><h3>列表信息&nbsp;&nbsp;所属问卷：${survey.title }</h3><a class="operButton" href="javascript:add();">添加问题</a></div>
<div class="bd">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listtable">
  <tr class="tth">
    <td>id</td><td>调查问题</td><td>问题类型</td><td>问题排序(值越小排越前面)</td>     
    <td >操作</td>  
  </tr>
  <c:forEach items="${list}" var="vo" varStatus="i">
	<c:choose>
		<c:when test="${i.index%2==0 }"><tr class="ttd"></c:when>
		<c:otherwise><tr class="ttd2"></c:otherwise>
	</c:choose>
		<td>${vo.id }</td>
		<td>${vo.question }</td>		
		<td>
			<c:if test="${vo.questionType==0 }">单选题</c:if>
			<c:if test="${vo.questionType==1 }">多选题</c:if>
			<c:if test="${vo.questionType==2 }">问答题</c:if>
			<c:if test="${vo.questionType==3 }">意见与建议</c:if>		
		</td>
		<td>${vo.questionOrder }</td>
				
		<td align="center">
		<c:if test="${vo.questionType==0 || vo.questionType==1}">
			<a href="/sys/surveyquestionoption/list.do?questionId=${vo.id }">问题答案管理</a>&nbsp;
		</c:if>
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
