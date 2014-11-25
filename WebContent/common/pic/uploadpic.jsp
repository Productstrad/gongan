<%@page import="common.Config"%>
<%@page import="java.util.Map"%>
<%@page import="dao.MedialibDao"%>
<%@page import="java.util.Date"%><%@page import="service.LoginService"%><%@page import="vo.Medialib"%><%@page import="util.UploadFile"%><%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%
    		
    String result=UploadFile.getInstance().uploadPic(request, 0, Config.get("config/uploadPicConfig/dealMethod/text()",
			"original")).replace(";", "");
    if(result.indexOf("error:")>=0){
    	out.print(result+",<a href=\"javascript:history.go(-1);\">重新上传</a>");
    	return;
    }
    /*
    //取文件form参数
    Map map=UploadFile.getInstance().getParmas(request);
    //插入用户媒体库
    Medialib vo=new Medialib();
    vo.setMediaPath(result);
    vo.setMediaTitle(map.get("mediaTitle")!=null?map.get("mediaTitle").toString():"");
    vo.setMediaType(1);
    vo.setUserid(LoginService.getLoginUserId(request));
    vo.setCreateDate(new Date());
    vo.setStatus(1);
    MedialibDao.getInstance().insert(vo);
    */
    //输出js    
	out.print("<script>try{parent.picpath_set.value='"+result+"';}catch(e){};try{parent.picpath.value='"+result+"';}catch(e){};parent.upPicDialog.dialog('close');</script>");
%>