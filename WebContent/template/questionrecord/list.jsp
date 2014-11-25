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
function manualReply(id,weixincode){
	$("<div><iframe id='dialogIframe' src='manualreply.do?id="+id+"&weixincode="+weixincode+"' width='500px' height='240px' style='border:none;' frameborder='0'></iframe></div>").dialog({ autoOpen: true, modal: true, title: "人工回复" });
}
</script>
<body>
</div>
<div id="mainarea">
  <div class="k itable">
    <div class="hd"><h3>列表信息</h3>
    
    </div>
<div class="bd">
<table>
<tr>
<td>
<form action="/sys/questionrecord/list.do">
	    <select id="searchOption" name="searchOption">
	    	<option value="48noReply" <c:if test="${searchOption=='48noReply' }">selected</c:if>>48小时内未回复的</option>
	    	<option value="all" <c:if test="${searchOption=='all' }">selected</c:if>>全部</option>
	    </select>
	    <input type="text" id="nickname" name="nickname" value="${nickName }"/>
	    <input type="submit" id="search()" value="查询"/>
    </form>
</td>
</tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listtable">
  <tr class="tth">
    <td>id</td><td>提问者</td><td>提问时间</td><td>问题</td><td>回复情况</td>    
    <td >操作</td>  
  </tr>
  <c:forEach items="${list}" var="vo" varStatus="i">
	<c:choose>
		<c:when test="${i.index%2==0 }"><tr class="ttd"></c:when>
		<c:otherwise><tr class="ttd2"></c:otherwise>
	</c:choose>
		<td>${vo.id }</td>
		<td>${vo.nickname }</td>
		<td>${vo.createDate }</td>
		<td>${vo.question }</td>
		<td>${vo.replyState }</td>
		<td align="center">
		 
		<c:if test="${ vo.replyState=='未回复'}">
			<a href="javascript:manualReply('${vo.id }','${vo.weixincode }');">人工回复</a>
		</c:if>
		
		<!-- 
		<c:if test="${ vo.autoAnswerId==null}">
			<a>测试回复内容</a>
		</c:if>
		 -->
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
