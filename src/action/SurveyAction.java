package action;

import java.util.HashMap;
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
import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.SurveyDao;
import vo.Survey;

import java.util.Date;


@Controller
@RequestMapping("/sys/survey")
public class SurveyAction {
	
	private Logger log = LoggerFactory.getLogger(SurveyAction.class);		
	private SurveyDao surveyDao=SurveyDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String title=ParamUtil.getStringParameter(request,"title");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date startDate=ParamUtil.getDateParameter(request,"startDate");
		Date endDate=ParamUtil.getDateParameter(request,"endDate");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("title", title);
		params.put("userid", userid);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", surveyDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(surveyDao.findCount(params), pageSize, request, model);		
		return "survey/list";
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
		Survey vo=surveyDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "survey/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid_set"))){
			paramsMap.put("userid_set", ParamUtil.getIntegerParameter(request, "userid_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "startDate_set"))){
			paramsMap.put("startDate_set", ParamUtil.getDateParameter(request, "startDate_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "endDate_set"))){
			paramsMap.put("endDate_set", ParamUtil.getDateParameter(request, "endDate_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "userid"))){
			paramsMap.put("userid", ParamUtil.getIntegerParameter(request, "userid"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "startDate"))){
			paramsMap.put("startDate", ParamUtil.getDateParameter(request, "startDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "endDate"))){
			paramsMap.put("endDate", ParamUtil.getDateParameter(request, "endDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=surveyDao.update(paramsMap);
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
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date startDate=ParamUtil.getDateParameter(request,"startDate");
		Date endDate=ParamUtil.getDateParameter(request,"endDate");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Survey vo=new Survey();
		vo.setId(id);
		vo.setTitle(title);
		vo.setUserid(userid);
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=surveyDao.update(vo);
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
		return "survey/add";
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
		String title=ParamUtil.getStringParameter(request,"title");		
		Date startDate=ParamUtil.getDateParameter(request,"startDate");
		Date endDate=ParamUtil.getDateParameter(request,"endDate");		
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
			
		Survey vo=new Survey();		
		vo.setTitle(title);
		vo.setUserid(LoginService.getLoginUserId(request));
		vo.setStartDate(startDate);
		vo.setEndDate(endDate);
		vo.setCreateDate(new Date());
		vo.setStatus(status);
				
		int effect=surveyDao.insert(vo);
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
		int effect=surveyDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
