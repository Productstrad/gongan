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
import dao.SurveyrecordDao;
import vo.Surveyrecord;

import java.util.Date;


@Controller
@RequestMapping("/sys/surveyrecord")
public class SurveyrecordAction {
	
	private Logger log = LoggerFactory.getLogger(SurveyrecordAction.class);		
	private SurveyrecordDao surveyrecordDao=SurveyrecordDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Long id=ParamUtil.getLongParameter(request,"id");
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer optionId=ParamUtil.getIntegerParameter(request,"optionId");
		String answer=ParamUtil.getStringParameter(request,"answer");
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("weixincode", weixincode);
		params.put("createDate", createDate);
		params.put("surveyId", surveyId);
		params.put("questionId", questionId);
		params.put("optionId", optionId);
		params.put("answer", answer);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", surveyrecordDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(surveyrecordDao.findCount(params), pageSize, request, model);		
		return "surveyrecord/list";
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
		Surveyrecord vo=surveyrecordDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "surveyrecord/update";
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
			paramsMap.put("id_set", ParamUtil.getLongParameter(request, "id_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "weixincode_set"))){
			paramsMap.put("weixincode_set", ParamUtil.getStringParameter(request, "weixincode_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate_set"))){
			paramsMap.put("createDate_set", ParamUtil.getDateParameter(request, "createDate_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "surveyId_set"))){
			paramsMap.put("surveyId_set", ParamUtil.getIntegerParameter(request, "surveyId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionId_set"))){
			paramsMap.put("questionId_set", ParamUtil.getIntegerParameter(request, "questionId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionId_set"))){
			paramsMap.put("optionId_set", ParamUtil.getIntegerParameter(request, "optionId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "answer_set"))){
			paramsMap.put("answer_set", ParamUtil.getStringParameter(request, "answer_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getLongParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "weixincode"))){
			paramsMap.put("weixincode", ParamUtil.getStringParameter(request, "weixincode"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "surveyId"))){
			paramsMap.put("surveyId", ParamUtil.getIntegerParameter(request, "surveyId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "questionId"))){
			paramsMap.put("questionId", ParamUtil.getIntegerParameter(request, "questionId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "optionId"))){
			paramsMap.put("optionId", ParamUtil.getIntegerParameter(request, "optionId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "answer"))){
			paramsMap.put("answer", ParamUtil.getStringParameter(request, "answer"));
		}
		int effect=surveyrecordDao.update(paramsMap);
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
		Long id=ParamUtil.getLongParameter(request,"id");
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer optionId=ParamUtil.getIntegerParameter(request,"optionId");
		String answer=ParamUtil.getStringParameter(request,"answer");
			
		Surveyrecord vo=new Surveyrecord();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setCreateDate(createDate);
		vo.setSurveyId(surveyId);
		vo.setQuestionId(questionId);
		vo.setOptionId(optionId);
		vo.setAnswer(answer);
				
		int effect=surveyrecordDao.update(vo);
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
		return "surveyrecord/add";
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
		Long id=ParamUtil.getLongParameter(request,"id");
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer surveyId=ParamUtil.getIntegerParameter(request,"surveyId");
		Integer questionId=ParamUtil.getIntegerParameter(request,"questionId");
		Integer optionId=ParamUtil.getIntegerParameter(request,"optionId");
		String answer=ParamUtil.getStringParameter(request,"answer");
			
		Surveyrecord vo=new Surveyrecord();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setCreateDate(createDate);
		vo.setSurveyId(surveyId);
		vo.setQuestionId(questionId);
		vo.setOptionId(optionId);
		vo.setAnswer(answer);
				
		int effect=surveyrecordDao.insert(vo);
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
		int effect=surveyrecordDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
