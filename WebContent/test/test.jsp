<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="javax.xml.xpath.XPathConstants"%>
<%@page import="javax.xml.xpath.XPathExpression"%>
<%@page import="javax.xml.xpath.XPath"%>
<%@page import="javax.xml.xpath.XPathFactory"%>
<%@page import="javax.xml.parsers.DocumentBuilder"%>
<%@page import="javax.xml.parsers.DocumentBuilderFactory"%>
<%@page import="common.Config"%>
<%@page import="java.util.HashMap"%>
<%@page import="dao.AdminDao"%>
<%@page import="vo.Admin"%>
<%@page import="java.util.List"%>
<%@page import="common.Constant"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%
String path=Config.class.getClassLoader().getResource("").getPath().substring(1)+"config/config.xml";
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
factory.setNamespaceAware(false);  
DocumentBuilder builder = factory.newDocumentBuilder();  
Document doc = builder.parse(path);
XPathFactory xFactory = XPathFactory.newInstance();  
XPath xpath = xFactory.newXPath();  
XPathExpression expr = xpath  
        .compile("config/domain/text()");  
Object result = expr.evaluate(doc, XPathConstants.NODESET);  
NodeList nodes = (NodeList) result;  
String value="";
if(nodes.getLength()>0){		        	 
	value=nodes.item(0).getNodeValue();		        	
}
if(value==null){
	value="testfdsf";
}

out.print(value+"<br/>");
	out.print(Constant.domain+"<br/>");
	List<Admin> list=AdminDao.getInstance().find(new HashMap(), 1, 100);
	for(Admin a:list){
		out.print(a.getAccount());
	}
%>
</body>
</html>