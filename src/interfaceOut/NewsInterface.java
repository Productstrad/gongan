package interfaceOut;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.util.MessageUtil;
import service.NewsService;
import util.MessageKit;
import util.Page;
import util.ParamUtil;
import vo.News;
import dao.NewsDao;


/**
 * @author mendz
 *	2014年10月28日
 */
@Controller
@RequestMapping("/interfaceOut/news")
public class NewsInterface {

	private Logger log = LoggerFactory.getLogger(NewsInterface.class);
	private NewsDao newsDao=NewsDao.getInstance();
	
	@RequestMapping(value = "/test.do")
	public void test(HttpServletRequest request,HttpServletResponse response			
			,Model model) throws IOException {
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String title=ParamUtil.getStringParameter(request,"title");
		Integer itemI=ParamUtil.getIntegerParameter(request,"itemI");
		Integer itemII=ParamUtil.getIntegerParameter(request,"itemII");
		String content=ParamUtil.getStringParameter(request,"content");
		String picpath=ParamUtil.getStringParameter(request,"picpath");
		String jumpURL=ParamUtil.getStringParameter(request,"jumpURL");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Integer clicks=ParamUtil.getIntegerParameter(request,"clicks");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer newsType=ParamUtil.getIntegerParameter(request,"newsType");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("title", title);
		params.put("itemI", itemI);
		params.put("itemII", itemII);
		params.put("content", content);
		params.put("picpath", picpath);
		params.put("jumpURL", jumpURL);
		params.put("userid", userid);
		params.put("clicks", clicks);
		params.put("createDate", createDate);
		params.put("newsType", newsType);
		params.put("status", status);
		params.put("notDel", 1);
		
		String respMessage = NewsService.getXmlNewsList(params, "", "", 1, 10, MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		MessageKit.displayMessage(response, respMessage);
	}
	@RequestMapping(value = "/get.do")
	public void get(HttpServletRequest request,HttpServletResponse response			
			,Model model) throws IOException {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String title=ParamUtil.getStringParameter(request,"title");
		Integer itemI=ParamUtil.getIntegerParameter(request,"itemI");
		Integer itemII=ParamUtil.getIntegerParameter(request,"itemII");
		String content=ParamUtil.getStringParameter(request,"content");
		String picpath=ParamUtil.getStringParameter(request,"picpath");
		String jumpURL=ParamUtil.getStringParameter(request,"jumpURL");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Integer clicks=ParamUtil.getIntegerParameter(request,"clicks");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer newsType=ParamUtil.getIntegerParameter(request,"newsType");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("title", title);
		params.put("itemI", itemI);
		params.put("itemII", itemII);
		params.put("content", content);
		params.put("picpath", picpath);
		params.put("jumpURL", jumpURL);
		params.put("userid", userid);
		params.put("clicks", clicks);
		params.put("createDate", createDate);
		params.put("newsType", newsType);
		params.put("status", status);
		params.put("notDel", 1);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);
		List<News> list=newsDao.find(params,pageNo,pageSize);
		int count=newsDao.findCount(params);
		
		List<Article> articleList = new ArrayList<Article>();
		for (News n : list) {
			Article a = new Article();
			a.setTitle(n.getTitle());  
		    a.setDescription("");  
		    a.setPicUrl(n.getCompletePicPath());  
		    a.setUrl(n.getCompleteNewsUrl());
		    articleList.add(a);
		}
		// 创建图文消息  
        NewsMessage newsMessage = new NewsMessage();  
        newsMessage.setToUserName("");  
        newsMessage.setFromUserName("");  
        newsMessage.setCreateTime(new Date().getTime());  
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
        newsMessage.setFuncFlag(0); 
        newsMessage.setArticleCount(count);  
	    newsMessage.setArticles(articleList);  
	    String respMessage = MessageUtil.newsMessageToXml(newsMessage);  
        MessageKit.displayMessage(response, respMessage);
	}
	
	
}
