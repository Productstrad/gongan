package action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.LoginService;
import service.NewsService;
import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.ItemiDao;
import dao.NewsDao;
import vo.Itemi;
import vo.News;

import java.util.Date;


@Controller
@RequestMapping("/sys/news")
public class NewsAction {
	
	private Logger log = LoggerFactory.getLogger(NewsAction.class);		
	private NewsDao newsDao=NewsDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
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
		model.addAttribute("list", newsDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(newsDao.findCount(params), pageSize, request, model);		
		return "news/list";
	 }	
	
	/**
	 * update vo详细信息显示页
	 * @param request
	 * @param response
	 * @param model
	 * @return	 
	 */
	@RequestMapping(value = "/update.do")
	public String update(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request, "id");
		News vo=newsDao.findByPK(id);
		//取一级栏目
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status", 1);
		List<Itemi> itemis=ItemiDao.getInstance().find(paramsMap, 1, Integer.MAX_VALUE);
		model.addAttribute("itemis", itemis);
		model.addAttribute("vo", vo);
		return "news/update";
	 }
	 
	 /**
	 * 提交更新，根据参数更新对象
	 * @param request
	 * @param response
	 * @param id
	 * @param model
	 * @author mengdz
	 */
	@RequestMapping(value = "/updatepost.do")	
	public void updatepost(HttpServletRequest request,HttpServletResponse response
			,Model model) {		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id_set"))){
			paramsMap.put("id_set", ParamUtil.getIntegerParameter(request, "id_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "title_set"))){
			paramsMap.put("title_set", ParamUtil.getStringParameter(request, "title_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemI_set"))){
			paramsMap.put("itemI_set", ParamUtil.getIntegerParameter(request, "itemI_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemII_set"))){
			paramsMap.put("itemII_set", ParamUtil.getIntegerParameter(request, "itemII_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "content_set"))){
			paramsMap.put("content_set", ParamUtil.getStringParameter(request, "content_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "picpath_set"))){
			paramsMap.put("picpath_set", ParamUtil.getStringParameter(request, "picpath_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediapath_set"))){
			paramsMap.put("mediapath_set", ParamUtil.getStringParameter(request, "mediapath_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "jumpURL_set"))){
			paramsMap.put("jumpURL_set", ParamUtil.getStringParameter(request, "jumpURL_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid_set"))){
			paramsMap.put("userid_set", ParamUtil.getIntegerParameter(request, "userid_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "clicks_set"))){
			paramsMap.put("clicks_set", ParamUtil.getIntegerParameter(request, "clicks_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate_set"))){
			paramsMap.put("createDate_set", ParamUtil.getDateParameter(request, "createDate_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "newsType_set"))){
			paramsMap.put("newsType_set", ParamUtil.getIntegerParameter(request, "newsType_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status_set"))){
			paramsMap.put("status_set", ParamUtil.getIntegerParameter(request, "status_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getIntegerParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "title"))){
			paramsMap.put("title", ParamUtil.getStringParameter(request, "title"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemI"))){
			paramsMap.put("itemI", ParamUtil.getIntegerParameter(request, "itemI"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemII"))){
			paramsMap.put("itemII", ParamUtil.getIntegerParameter(request, "itemII"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "content"))){
			paramsMap.put("content", ParamUtil.getStringParameter(request, "content"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "picpath"))){
			paramsMap.put("picpath", ParamUtil.getStringParameter(request, "picpath"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "jumpURL"))){
			paramsMap.put("jumpURL", ParamUtil.getStringParameter(request, "jumpURL"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid"))){
			paramsMap.put("userid", ParamUtil.getIntegerParameter(request, "userid"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "clicks"))){
			paramsMap.put("clicks", ParamUtil.getIntegerParameter(request, "clicks"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "newsType"))){
			paramsMap.put("newsType", ParamUtil.getIntegerParameter(request, "newsType"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=newsDao.update(paramsMap);
		MessageKit.displayJsonResult(response, effect, null, null, null);		
	}
	 
	 /**
	 * update 提交
	 * @param request
	 * @param response
	 * @param model	 
	 */
	@RequestMapping(value = "/updatevopost.do")
	public void updatevopost(HttpServletRequest request,HttpServletResponse response			
			,Model model) {		
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
			
		News vo=new News();
		vo.setId(id);
		vo.setTitle(title);
		vo.setItemI(itemI);
		vo.setItemII(itemII);
		vo.setContent(content);
		vo.setPicpath(picpath);
		vo.setJumpURL(jumpURL);
		vo.setUserid(userid);
		vo.setClicks(clicks);
		vo.setCreateDate(createDate);
		vo.setNewsType(newsType);
		vo.setStatus(status);
				
		int effect=newsDao.update(vo);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }	
	
	/**
	 * add vo详细信息显示页
	 * @param request
	 * @param response
	 * @param model
	 * @return	
	 */
	@RequestMapping(value = "/add.do")
	public String add(HttpServletRequest request,HttpServletResponse response			
			,Model model) {		
		//取一级栏目
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		paramsMap.put("status", 1);
		List<Itemi> itemis=ItemiDao.getInstance().find(paramsMap, 1, Integer.MAX_VALUE);
		model.addAttribute("itemis", itemis);
		return "news/add";
	 }
	 
	 /**
	 * add 提交
	 * @param request
	 * @param response
	 * @param model	 
	 */
	@RequestMapping(value = "/addpost.do")
	public void addpost(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String title=ParamUtil.getStringParameter(request,"title");
		Integer itemI=ParamUtil.getIntegerParameter(request,"itemI");
		Integer itemII=ParamUtil.getIntegerParameter(request,"itemII");
		String content=ParamUtil.getStringParameter(request,"content");
		String picpath=ParamUtil.getStringParameter(request,"picpath");
		String mediapath=ParamUtil.getStringParameter(request,"mediapath");
		String jumpURL=ParamUtil.getStringParameter(request,"jumpURL");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Integer clicks=ParamUtil.getIntegerParameter(request,"clicks");
		
		Integer newsType=ParamUtil.getIntegerParameter(request,"newsType");
		Integer status=ParamUtil.getIntegerParameter(request,"status",0);
			
		News vo=new News();		
		vo.setTitle(title);
		vo.setItemI(itemI);
		vo.setItemII(itemII);
		vo.setContent(content);
		vo.setPicpath(picpath);
		vo.setMediapath(mediapath);
		vo.setJumpURL(jumpURL);
		vo.setUserid(LoginService.getLoginUserId(request));
		vo.setClicks(0);
		vo.setCreateDate(new Date());
		vo.setNewsType(newsType);
		vo.setStatus(status);
				
		int effect=newsDao.insert(vo);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }	 
	/**
	 * 批量删除 
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		String[] ids=request.getParameterValues("ids[]");
		int effect=newsDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
	
	/**
	 * 推送新闻
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/send.do")
	public void send(HttpServletRequest request,HttpServletResponse response			
			,Model model) {			
		int id=ParamUtil.getIntegerParameter(request, "id");
		News n=newsDao.findByPK(id);
		List<News> list=new ArrayList<News>();
		list.add(n);
		String result=NewsService.getInstance().sendNews(list);
		MessageKit.displayMessage(response, result);
	 }
}
