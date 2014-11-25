<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理后台</title>
</head>
<FRAMESET rows=50,* cols=* bordercolor='#006699' border=0 >
 <FRAME id=topFrame name=topFrame src="top.jsp" noResize scrolling=no>
       <FRAMESET rows=* cols=190,*>
          <FRAME name=left src="left.jsp" target="main">
           <FRAME name=main src="/sys/news/list.do">
     </FRAMESET>
 <NOFRAMES>
 </NOFRAMES>
</FRAMESET>
</html>