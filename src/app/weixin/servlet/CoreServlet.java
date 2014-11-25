package app.weixin.servlet;

import java.io.IOException;  
import java.io.PrintWriter;  
  


import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  


import org.apache.log4j.Logger;

import util.HttpRequstUtil;

import app.weixin.util.SignUtil;  
import app.weixin.service.*; 
/** 
 * 核心请求处理类 
 *  
 * @author xsy 
 * @date 2014-05-12 
 */  
public class CoreServlet extends HttpServlet {  
	
    private static final long serialVersionUID = 4440739483644821986L;  
    private static Logger logger = Logger.getLogger(CoreServlet.class);
    
    /** 
     * 确认请求来自微信服务器 
     */  
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	 request.setCharacterEncoding("UTF-8");  
         response.setCharacterEncoding("UTF-8"); 
        // 微信加密签名  
        String signature = request.getParameter("signature");  
        // 时间戳  
        String timestamp = request.getParameter("timestamp");  
        // 随机数  
        String nonce = request.getParameter("nonce");  
        // 随机字符串  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = response.getWriter();  
//         通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }          
        //模拟测试调用核心业务类接收消息、处理消息         
//        String respMessage = CoreService.processRequest(request);         
//        out.print(respMessage);         
        
        out.close();  
        out = null;  
    }  
  
    /** 
     * 处理微信服务器发来的消息 
     */  
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        // TODO 消息的接收、处理、响应  
    	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        
        //记录请求信息
        //logger.error("weixin-requestParams:"+HttpRequstUtil.getRequestParams(request));
        
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(request);  
          
        // 响应消息  
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();  
    }  
  
}