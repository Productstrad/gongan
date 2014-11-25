package action;

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

import service.AutoanswerService;
import service.LoginService;
import service.QuestionrecordService;
import util.JsonUtil;
import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.AutoanswerDao;
import vo.Autoanswer;

import java.util.Date;


@Controller
@RequestMapping("/sys/autoanswer")
public class AutoanswerAction {
	
	private Logger log = LoggerFactory.getLogger(AutoanswerAction.class);		
	private AutoanswerDao autoanswerDao=AutoanswerDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String title=ParamUtil.getStringParameter(request,"title");
		String content=ParamUtil.getStringParameter(request,"content");
		String keyWord=ParamUtil.getStringParameter(request,"keyWord");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("title", title);
		params.put("content", content);
		params.put("keyWord", keyWord);
		params.put("userid", userid);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", autoanswerDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(autoanswerDao.findCount(params), pageSize, request, model);		
		return "autoanswer/list";
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
		Autoanswer vo=autoanswerDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "autoanswer/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "content_set"))){
			paramsMap.put("content_set", ParamUtil.getStringParameter(request, "content_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "keyWord_set"))){
			paramsMap.put("keyWord_set", ParamUtil.getStringParameter(request, "keyWord_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid_set"))){
			paramsMap.put("userid_set", ParamUtil.getIntegerParameter(request, "userid_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate_set"))){
			paramsMap.put("createDate_set", ParamUtil.getDateParameter(request, "createDate_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "content"))){
			paramsMap.put("content", ParamUtil.getStringParameter(request, "content"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "keyWord"))){
			paramsMap.put("keyWord", ParamUtil.getStringParameter(request, "keyWord"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid"))){
			paramsMap.put("userid", ParamUtil.getIntegerParameter(request, "userid"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=autoanswerDao.update(paramsMap);
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
		String content=ParamUtil.getStringParameter(request,"content");
		String keyWord=ParamUtil.getStringParameter(request,"keyWord");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Autoanswer vo=new Autoanswer();
		vo.setId(id);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setKeyWord(keyWord);
		vo.setUserid(userid);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=autoanswerDao.update(vo);
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
		return "autoanswer/add";
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
		String content=ParamUtil.getStringParameter(request,"content");
		String keyWord=ParamUtil.getStringParameter(request,"keyWord");
		
		
			
		Autoanswer vo=new Autoanswer();
		vo.setId(id);
		vo.setTitle(title);
		vo.setContent(content);
		vo.setKeyWord(keyWord);
		vo.setUserid(LoginService.getLoginUserId(request));
		vo.setCreateDate(new Date());
		vo.setStatus(1);
				
		int effect=autoanswerDao.insert(vo);
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
		int effect=autoanswerDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
	
	@RequestMapping(value = "/testanswer.do")
	public String testanswer(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		return "autoanswer/testanswer";
	}
	@RequestMapping(value = "/testanswerpost.do")
	public void testanswerpost(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		String question=ParamUtil.getParameter(request, "question");
		AutoanswerService.getInstance().delKeyWordsList();//清除答案缓存
		List<Autoanswer> list=new QuestionrecordService().getAutoAnswer(question);
		MessageKit.displayMessage(response, JsonUtil.toJson(list));		
	}
}
