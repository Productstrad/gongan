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
import dao.SurveyDao;
import dao.SurveyquestionDao;
import vo.Surveyquestion;



@Controller
@RequestMapping("/sys/surveyquestion")
public class SurveyquestionAction {
	
	private Logger log = LoggerFactory.getLogger(SurveyquestionAction.class);		
	private SurveyquestionDao surveyquestionDao=SurveyquestionDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String question=ParamUtil.getStringParameter(request,"question");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionType=ParamUtil.getIntegerParameter(request,"questionType");
		Integer order=ParamUtil.getIntegerParameter(request,"order");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("question", question);
		params.put("surveyId", surveyId);
		params.put("questionType", questionType);
		params.put("order", order);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);				
		model.addAttribute("list", surveyquestionDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(surveyquestionDao.findCount(params), pageSize, request, model);	
		//取所属问卷
		model.addAttribute("survey", SurveyDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "surveyId")));
		return "surveyquestion/list";
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
		Surveyquestion vo=surveyquestionDao.findByPK(id);
		model.addAttribute("vo", vo);
		//取所属问卷
		model.addAttribute("survey", SurveyDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "surveyId")));
		return "surveyquestion/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "question_set"))){
			paramsMap.put("question_set", ParamUtil.getStringParameter(request, "question_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "surveyId_set"))){
			paramsMap.put("surveyId_set", ParamUtil.getIntegerParameter(request, "surveyId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionType_set"))){
			paramsMap.put("questionType_set", ParamUtil.getIntegerParameter(request, "questionType_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionOrder_set"))){
			paramsMap.put("questionOrder_set", ParamUtil.getIntegerParameter(request, "questionOrder_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status_set"))){
			paramsMap.put("status_set", ParamUtil.getIntegerParameter(request, "status_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getIntegerParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "question"))){
			paramsMap.put("question", ParamUtil.getStringParameter(request, "question"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "surveyId"))){
			paramsMap.put("surveyId", ParamUtil.getIntegerParameter(request, "surveyId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionType"))){
			paramsMap.put("questionType", ParamUtil.getIntegerParameter(request, "questionType"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "order"))){
			paramsMap.put("order", ParamUtil.getIntegerParameter(request, "order"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=surveyquestionDao.update(paramsMap);
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
		String question=ParamUtil.getStringParameter(request,"question");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionType=ParamUtil.getIntegerParameter(request,"questionType");
		Integer order=ParamUtil.getIntegerParameter(request,"order");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Surveyquestion vo=new Surveyquestion();
		vo.setId(id);
		vo.setQuestion(question);
		vo.setSurveyId(surveyId);
		vo.setQuestionType(questionType);
		vo.setQuestionOrder(order);
		vo.setStatus(status);
				
		int effect=surveyquestionDao.update(vo);
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
		//取所属问卷
		model.addAttribute("survey", SurveyDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "surveyId")));
		return "surveyquestion/add";
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
		String question=ParamUtil.getStringParameter(request,"question");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionType=ParamUtil.getIntegerParameter(request,"questionType");
		Integer questionOrder=ParamUtil.getIntegerParameter(request,"questionOrder");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Surveyquestion vo=new Surveyquestion();
		vo.setId(id);
		vo.setQuestion(question);
		vo.setSurveyId(surveyId);
		vo.setQuestionType(questionType);
		vo.setQuestionOrder(questionOrder);
		vo.setStatus(status);
				
		int effect=surveyquestionDao.insert(vo);
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
		int effect=surveyquestionDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
