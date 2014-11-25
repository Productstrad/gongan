<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="page_num_tab">
<c:choose>
	<c:when test="${pagecount>1 }">
			<c:choose>
		  		<c:when test="${page==1 }"></c:when>
		  		<c:otherwise><a class="page_numb" href="${pageUrl }?pageNo=${page-1 }${pageParams}">上一页</a> | </c:otherwise>
		  	</c:choose>
			<c:forEach items="${pagenum}"  var="pagenum">
			  	<c:choose>
			  		<c:when test="${pagenum.page==page }"><span class='pageCurrent'>${pagenum.page }</span></c:when>
			  		<c:otherwise><a class="page_numb" href="${pageUrl }?pageNo=${pagenum.page }${pageParams}"> ${pagenum.page }  </a></c:otherwise>
			  	</c:choose>
			  </c:forEach>
			<c:choose>
		  		<c:when test="${page==pagecount }"></c:when>
		  		<c:otherwise> | <a class="page_numb" href="${pageUrl }?pageNo=${page+1 }${pageParams}">下一页</a></c:otherwise>
		  	</c:choose>	
	</c:when>	  	
</c:choose>
</div>
		