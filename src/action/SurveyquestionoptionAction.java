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

import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.SurveyquestionDao;
import dao.SurveyquestionoptionDao;
import vo.Surveyquestionoption;



@Controller
@RequestMapping("/sys/surveyquestionoption")
public class SurveyquestionoptionAction {
	
	private Logger log = LoggerFactory.getLogger(SurveyquestionoptionAction.class);		
	private SurveyquestionoptionDao surveyquestionoptionDao=SurveyquestionoptionDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String optionCotent=ParamUtil.getStringParameter(request,"optionCotent");
		String optionScore=ParamUtil.getStringParameter(request,"optionScore");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer order=ParamUtil.getIntegerParameter(request,"order");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("optionCotent", optionCotent);
		params.put("optionScore", optionScore);
		params.put("questionId", questionId);
		params.put("order", order);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", surveyquestionoptionDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(surveyquestionoptionDao.findCount(params), pageSize, request, model);	
		//所属问题
		model.addAttribute("question", SurveyquestionDao.getInstance().findByPK(questionId));
		return "surveyquestionoption/list";
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
		Surveyquestionoption vo=surveyquestionoptionDao.findByPK(id);
		model.addAttribute("vo", vo);
		//所属问题
		model.addAttribute("question", SurveyquestionDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "questionId")));
		return "surveyquestionoption/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionCotent_set"))){
			paramsMap.put("optionCotent_set", ParamUtil.getStringParameter(request, "optionCotent_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionScore_set"))){
			paramsMap.put("optionScore_set", ParamUtil.getStringParameter(request, "optionScore_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionId_set"))){
			paramsMap.put("questionId_set", ParamUtil.getIntegerParameter(request, "questionId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionOrder_set"))){
			paramsMap.put("optionOrder_set", ParamUtil.getIntegerParameter(request, "optionOrder_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status_set"))){
			paramsMap.put("status_set", ParamUtil.getIntegerParameter(request, "status_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getIntegerParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionCotent"))){
			paramsMap.put("optionCotent", ParamUtil.getStringParameter(request, "optionCotent"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionScore"))){
			paramsMap.put("optionScore", ParamUtil.getStringParameter(request, "optionScore"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionId"))){
			paramsMap.put("questionId", ParamUtil.getIntegerParameter(request, "questionId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionOrder"))){
			paramsMap.put("optionOrder", ParamUtil.getIntegerParameter(request, "optionOrder"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=surveyquestionoptionDao.update(paramsMap);
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
		String optionCotent=ParamUtil.getStringParameter(request,"optionCotent");
		String optionScore=ParamUtil.getStringParameter(request,"optionScore");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer optionOrder=ParamUtil.getIntegerParameter(request,"optionOrder");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Surveyquestionoption vo=new Surveyquestionoption();
		vo.setId(id);
		vo.setOptionCotent(optionCotent);
		if(StringUtil.isNotNullorEmpty(optionScore)){
			vo.setOptionScore(Float.valueOf(optionScore));
		}
		vo.setQuestionId(questionId);
		vo.setOptionOrder(optionOrder);
		vo.setStatus(status);
				
		int effect=surveyquestionoptionDao.update(vo);
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
		//所属问题
		model.addAttribute("question", SurveyquestionDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "questionId")));
		return "surveyquestionoption/add";
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
		String optionCotent=ParamUtil.getStringParameter(request,"optionCotent");
		String optionScore=ParamUtil.getStringParameter(request,"optionScore");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer optionOrder=ParamUtil.getIntegerParameter(request,"optionOrder");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
			
		Surveyquestionoption vo=new Surveyquestionoption();
		vo.setId(id);
		vo.setOptionCotent(optionCotent);
		if(StringUtil.isNotNullorEmpty(optionScore)){
			vo.setOptionScore(Float.valueOf(optionScore));
		}
		vo.setQuestionId(questionId);
		vo.setOptionOrder(optionOrder);
		vo.setStatus(status);
				
		int effect=surveyquestionoptionDao.insert(vo);
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
		int effect=surveyquestionoptionDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
