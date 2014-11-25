package app.weixin.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import service.MembersService;
import service.NewsService;
import service.QuestionrecordService;
import service.SurveyService;
import util.StringUtil;
import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.message.resp.TextMessage;
import app.weixin.pojo.AccessToken;
import app.weixin.pojo.MenuMessageType;
import app.weixin.pojo.MessageType;
import app.weixin.product.wenhuaproduct;
import app.weixin.util.MessageUtil;
import app.weixin.util.WeixinUtil;
  
/** 
 * 核心服务类 
 *  
 * @author xsy 
 * @date 2014-05-12 
 */  
public class CoreService {  
	
	private static Logger logger = Logger.getLogger(CoreService.class);
    /** 
     * 处理微信发来的请求 
     *  
     * @param request 
     * @return 
     */  
    public static String processRequest(HttpServletRequest request) {  
        String respMessage = null; 
        String fromUserName=null,toUserName=null;
        try {          	
            // 默认返回的文本消息内容  
            String respContent = "请求处理异常，请稍候尝试！";   
            // xml请求解析  
            Map<String, String> requestMap = MessageUtil.parseXml(request);              
            //模拟测试数据  调查按钮事件：msgtype=event&event=click&eventkey=m32 调查回复:msgtype=text&content=
            //取新闻:msgtype=event&event=click&eventkey=m11
//            Map<String, String> requestMap =new HashMap<String, String>();
//        	requestMap.put("FromUserName", "mengdz");
//        	requestMap.put("ToUserName", "testweinxincode");
//        	requestMap.put("MsgType", request.getParameter("msgtype"));
//        	requestMap.put("Event", request.getParameter("event"));
//        	requestMap.put("EventKey", request.getParameter("eventkey"));
//        	requestMap.put("Content", request.getParameter("content"));
        	
            // 发送方帐号（open_id）  
            fromUserName = requestMap.get("FromUserName");  
            // 公众帐号  
            toUserName = requestMap.get("ToUserName");  
            // 消息类型  
            String msgType = requestMap.get("MsgType");  
            //用户回复的内容
            String content=requestMap.get("Content")!=null?requestMap.get("Content"):"";
            //记录用户关注、最后访问时间
            MembersService.getInstance().visitRecord(fromUserName);
            //日志记录请求参数
//            logger.error("weixin-reqestXml:"+requestMap.toString());        
         
            TextMessage textMessage = new TextMessage();             
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            //消息处理
            switch(MessageType.getMessageType(msgType)){
            // 文本消息
            case text:            	
	            	String isSurveyFormat=SurveyService.getInstance().isSurveyFormat(content,fromUserName);
	            	if("1".equals(isSurveyFormat)){//调查回复的题目选项
	            		respMessage = SurveyService.getInstance().dealOptionSelect(content, toUserName, fromUserName, MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	            	}else if("-1".equals(isSurveyFormat)){
	            		respContent = "系统异常,请点击调查按钮重新继续答题！"; 
		                textMessage.setContent(respContent);  
		                // 将文本消息对象转换成xml字符串  
		                respMessage = MessageUtil.textMessageToXml(textMessage); 
//		                logger.error("-----------------"+respMessage);
	            	}else if("-2".equals(isSurveyFormat)){
	            		respContent = "没有您回复的备选项！"; 
		                textMessage.setContent(respContent);  
		                // 将文本消息对象转换成xml字符串  
		                respMessage = MessageUtil.textMessageToXml(textMessage); 
	            	}else{//互动咨询	//	            	
	            		respMessage=new QuestionrecordService().autoAnswer(content, toUserName, fromUserName, MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	            	}            	
                break;
            case image:
            	respContent = "您发送的是图片消息！";  
                textMessage.setContent(respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            	break;
            case link:
            	respContent = "您发送的是链接消息！"; 
                textMessage.setContent(respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            	break;
            case location:
            	respContent = "您发送的是地理位置消息！";  
                textMessage.setContent(respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            	break;
            case voice:
            	respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage);
            	break;
            case event:// 事件类型 
            	String eventType = requestMap.get("Event");
            	switch(MessageType.getMessageType(eventType))
            	{
            	case subscribe:// 订阅  
            		// 回复单图文消息  
                    NewsMessage newsMessage = new NewsMessage();  
                    newsMessage.setToUserName(fromUserName);  
                    newsMessage.setFromUserName(toUserName);  
                    newsMessage.setCreateTime(new Date().getTime());  
                    newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
                    newsMessage.setFuncFlag(0);
                    List<Article> articleList = new ArrayList<Article>();
                	Article article = new Article();  
                    article.setTitle("贵定县公安");  
                    article.setDescription("感谢您关注贵定县公安，欢迎咨询与公安相关的业务，如有急情、险情，请拨打110！！");  
                    article.setPicUrl("http://pic12.nipic.com/20110105/818468_092001002389_2.jpg");  
//                    article.setUrl("http://hnwhguolv.tmall.com/");  
                    articleList.add(article);  
                    // 设置图文消息个数  
                    newsMessage.setArticleCount(articleList.size());  
                    // 设置图文消息包含的图文集合  
                    newsMessage.setArticles(articleList);  
                    // 将图文消息对象转换成xml字符串  
                    respMessage = MessageUtil.newsMessageToXml(newsMessage);
            		break;
            	case unsubscribe:// 取消订阅  
            		break;
            	case click:// 自定义菜单点击事件  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");  
                    Map<String, Object> params=new HashMap<String, Object>();                    
                    switch(MenuMessageType.getMenuMessageType(eventKey)){
	                    case m11://公安动态	                    	
	                    	params.put("itemI", 6);
	                		params.put("itemII", 4);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                        break;
	                    case m12://公安发布
	                    	params.put("itemI", 6);
	                		params.put("itemII", 5);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;                    
	                    case m21://交通业务
	                    	params.put("itemI", 7);
	                		params.put("itemII", 8);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;
	                    case m22://治安业务
	                    	params.put("itemI", 7);
	                		params.put("itemII", 6);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;
	                    case m23://出入境业务
	                    	params.put("itemI", 7);
	                		params.put("itemII", 7);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;
	                    case m24://户籍业务
	                    	params.put("itemI", 7);
	                		params.put("itemII", 9);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;
	                    case m31://联系方式
	                    	params.put("itemI", 8);
	                		params.put("itemII", 10);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;
	                    case m32://满意度调查	                    	
	                        respMessage = SurveyService.getInstance().getTopSurvey(toUserName, fromUserName, MessageUtil.RESP_MESSAGE_TYPE_TEXT);
	                    	break;
	                    case m33://线索征集
	                    	params.put("itemI", 8);
	                		params.put("itemII", 12);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break; 
	                    case m34://失物招领
	                    	params.put("itemI", 8);
	                		params.put("itemII", 13);
	                        respMessage = NewsService.getInstance().getXmlNewsList(params,toUserName,fromUserName, 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	                    	break;                    
                    }
                	break;
            	}
            	break;
            
            }
            
            
        } catch (Exception e) {  
           logger.error("",e);  
        }  
//        logger.error("resMessage:"+respMessage);
        if(!StringUtil.isNotNullorEmpty(respMessage)){
        	// 默认回复此文本消息  
            TextMessage textMessage = new TextMessage();  
            textMessage.setToUserName(fromUserName);  
            textMessage.setFromUserName(toUserName);  
            textMessage.setCreateTime(new Date().getTime());  
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
            textMessage.setFuncFlag(0);  
            // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
            //textMessage.setContent("欢迎访问<a href=\"http://hnwhguolv.tmall.com/\">欢迎您访问贵定县公安局微信公众号</a>! msgType="+msgType);  
            textMessage.setContent("欢迎您访问贵定县公安局微信公众号"); 
            // 将文本消息对象转换成xml字符串  
            respMessage = MessageUtil.textMessageToXml(textMessage);         	
        }
        return respMessage;  
    }  
} 
