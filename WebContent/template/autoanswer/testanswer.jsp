<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试问题答案</title>
<script type="text/javascript" src="/js/jquery-1.7.1.min.js"></script>
</head>
<script type="text/javascript">
function test(id){		
		$.ajax({
				type : "POST",
				url : "/sys/autoanswer/testanswerpost.do",
				data : "question="+encodeURI($("#question").val()),
				dataType : "text",
				success : function(result) {
					var jsonObj=eval("("+result+")");
					var htmlStr="";
					try{
						for(var i=0;i<1;i++){						
							htmlStr+="<div style='text-align:center;'>"+jsonObj[i].title+"</div>";
							htmlStr+="<div style='text-align:left;'>"+jsonObj[i].content+"</div>";
						}
					}catch(e){					
						htmlStr="没有对应答案，请添加对应答案";
					}
					$("#answer").html(htmlStr);
				},
				error : function(request, error) {
					alert(error);
				}
		});		
}
</script>
<body>
<table>
	<tr>
		<td>问题:</td>
		<td><input type="text" id="question" />&nbsp;<input type="button" value="测试" onclick="test()"/></td>
	</tr>
	<tr>
		<td>智能答案:</td>
		<td id="answer"></td>
	</tr>	
</table>
</body>
</html>