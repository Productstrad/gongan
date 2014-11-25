package app.weixin.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.util.MessageUtil;

public class wenhuaproduct {

	//景点门票产品列表
	public static String GetAttractionTicketsProduct(String fromUserName,String toUserName){
		String respMessage = null;  
		// 创建图文消息  
        NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName(fromUserName);  
        newsMessage.setFromUserName(toUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0);  
        
		Article article1 = new Article();  
	    article1.setTitle("大东海百乐潜水基地 岸礁潜水\n 价　　格： ¥130.00 ");  
	    article1.setDescription("");  
	    article1.setPicUrl("http://img01.taobaocdn.com/bao/uploaded/i1/T146raFFxcXXXXXXXX_!!1-item_pic.gif_310x310.jpg");  
	    article1.setUrl("http://item.taobao.com/item.htm?ft=t&spm=a1z10.5.w4011-7041588135.64.8wzUCc&id=38888072841&rn=4d4b82f3ab91cd9ef95bcea90b87e7d5");  
	
	    Article article2 = new Article();  
	    article2.setTitle("三亚蜈支洲岛景点 门票 船票\n 价　　格： ¥168.00 ");  
	    article2.setDescription("");  
	    article2.setPicUrl("http://img03.taobaocdn.com/bao/uploaded/i3/T1kCbaFIRbXXXXXXXX_!!1-item_pic.gif_310x310.jpg");  
	    article2.setUrl("http://item.taobao.com/item.htm?ft=t&spm=a1z10.5.w4011-7041588135.61.8wzUCc&id=38847107301&rn=4d4b82f3ab91cd9ef95bcea90b87e7d5");  
	
	    Article article3 = new Article();  
	    article3.setTitle("三亚天涯海角门票\n 价　　格： ¥82.00 ");  
	    article3.setDescription("");  
	    article3.setPicUrl("http://img04.taobaocdn.com/bao/uploaded/i4/T1d1rjFPNXXXXXXXXX_!!1-item_pic.gif_310x310.jpg");  
	    article3.setUrl("http://item.taobao.com/item.htm?ft=t&spm=a1z10.5.w4011-7041588135.70.8wzUCc&id=38897984683&rn=4d4b82f3ab91cd9ef95bcea90b87e7d5");  
	    
	    List<Article> articleList = new ArrayList<Article>();
	    articleList.add(article1);  
	    articleList.add(article2);  
	    articleList.add(article3);  
	    newsMessage.setArticleCount(articleList.size());  
	    newsMessage.setArticles(articleList);  
	    respMessage = MessageUtil.newsMessageToXml(newsMessage);  
	    return respMessage;
	}
	//线路产品
	public static String GetLinesProduct(String fromUserName,String toUserName){
		String respMessage = null;
		
		// 创建图文消息  
        NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName(fromUserName);  
        newsMessage.setFromUserName(toUserName);  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0); 
        
        Article article1 = new Article();  
	    article1.setTitle("三亚蜈支洲岛一日游\n 价　　格： ¥165.00 ");  
	    article1.setDescription("7：40—8：40沿途接客人，9：30左右乘船登浪漫蜈支洲岛（AAAA，含景点大门票、船票，游览时间约5小时（含往返乘船时间）。岛上可选择适合自己的娱乐项目尽情享受游泳、乘摩托艇、划香蕉船、玩拖伞、潜水，还可乘电瓶车环岛观光；岛上椰风海韵、碧海蓝天、您可沙滩拾贝、海底遨游；下午15：00左右从景区返回市区，散团结束");  
	    article1.setPicUrl("http://gi3.md.alicdn.com/bao/uploaded/i3/T1g2UgFKRbXXXcjaU4_052044.jpg_360x360q90.jpg");  
	    article1.setUrl("http://detail.tmall.com/item.htm?spm=a1z10.5.w4011-7041588135.73.97AiPP&id=39027887512&rn=ac9cbcbf14c704c0d52d64f8ddd7f278");  
	
	    List<Article> articleList = new ArrayList<Article>();
	    articleList.add(article1);  
	    newsMessage.setArticleCount(articleList.size());  
	    newsMessage.setArticles(articleList);  
	    respMessage = MessageUtil.newsMessageToXml(newsMessage); 
		return respMessage;
	}
}
