package action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import app.weixin.pojo.AccessToken;
import app.weixin.util.WeixinUtil;
import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.MembersDao;
import dao.QuestionrecordDao;
import vo.Members;
import vo.Questionrecord;

import java.util.Date;


@Controller
@RequestMapping("/sys/questionrecord")
public class QuestionrecordAction {
	
	private Logger log = LoggerFactory.getLogger(QuestionrecordAction.class);		
	private QuestionrecordDao questionrecordDao=QuestionrecordDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		String question=ParamUtil.getStringParameter(request,"question");
		Integer autoAnswerId=ParamUtil.getIntegerParameter(request,"autoAnswerId");
		String answer=ParamUtil.getStringParameter(request,"answer");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
		String searchOption=ParamUtil.getParameter(request, "searchOption", "48noReply");
		String nickName=ParamUtil.getParameter(request,"nickname");
		
		Map params = new HashMap();
		params.put("id", id);
//		params.put("weixincode", weixincode);
		params.put("createDate", createDate);
		params.put("question", question);
		params.put("autoAnswerId", autoAnswerId);
		params.put("answer", answer);
		params.put("status", status);
		if(StringUtil.isNotNullorEmpty(nickName)){
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("nickname", nickName);
			paramsMap.put("status", 1);
			Members m=MembersDao.getInstance().findSingle(paramsMap);
			if(m!=null){
				params.put("weixincode", m.getWeixincode());
			}
		}
		params.put("searchOption", searchOption);	
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", questionrecordDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(questionrecordDao.findCount(params), pageSize, request, model);
		model.addAttribute("searchOption", searchOption);
		model.addAttribute("nickName", nickName);
		return "questionrecord/list";
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
		Questionrecord vo=questionrecordDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "questionrecord/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "weixincode_set"))){
			paramsMap.put("weixincode_set", ParamUtil.getStringParameter(request, "weixincode_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate_set"))){
			paramsMap.put("createDate_set", ParamUtil.getDateParameter(request, "createDate_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "question_set"))){
			paramsMap.put("question_set", ParamUtil.getStringParameter(request, "question_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "autoAnswerId_set"))){
			paramsMap.put("autoAnswerId_set", ParamUtil.getIntegerParameter(request, "autoAnswerId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "answer_set"))){
			paramsMap.put("answer_set", ParamUtil.getStringParameter(request, "answer_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status_set"))){
			paramsMap.put("status_set", ParamUtil.getIntegerParameter(request, "status_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getIntegerParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "weixincode"))){
			paramsMap.put("weixincode", ParamUtil.getStringParameter(request, "weixincode"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "question"))){
			paramsMap.put("question", ParamUtil.getStringParameter(request, "question"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "autoAnswerId"))){
			paramsMap.put("autoAnswerId", ParamUtil.getIntegerParameter(request, "autoAnswerId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "answer"))){
			paramsMap.put("answer", ParamUtil.getStringParameter(request, "answer"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=questionrecordDao.update(paramsMap);
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
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		String question=ParamUtil.getStringParameter(request,"question");
		Integer autoAnswerId=ParamUtil.getIntegerParameter(request,"autoAnswerId");
		String answer=ParamUtil.getStringParameter(request,"answer");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Questionrecord vo=new Questionrecord();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setCreateDate(createDate);
		vo.setQuestion(question);
		vo.setAutoAnswerId(autoAnswerId);
		vo.setAnswer(answer);
		vo.setStatus(status);
				
		int effect=questionrecordDao.update(vo);
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
		return "questionrecord/add";
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
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		String question=ParamUtil.getStringParameter(request,"question");
		Integer autoAnswerId=ParamUtil.getIntegerParameter(request,"autoAnswerId");
		String answer=ParamUtil.getStringParameter(request,"answer");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Questionrecord vo=new Questionrecord();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setCreateDate(createDate);
		vo.setQuestion(question);
		vo.setAutoAnswerId(autoAnswerId);
		vo.setAnswer(answer);
		vo.setStatus(status);
				
		int effect=questionrecordDao.insert(vo);
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
		int effect=questionrecordDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
	/**
	 * 人工回复界面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @author mengdz
	 * 2014年11月24日
	 */
	@RequestMapping(value = "/manualreply.do")
	public String manualreply(HttpServletRequest request,HttpServletResponse response			
			,Model model) {
		model.addAttribute("id", ParamUtil.getIntegerParameter(request, "id"));
		model.addAttribute("weixincode", ParamUtil.getParameter(request, "weixincode"));
		return "questionrecord/manualreply";
	}
	/**
	 * 人工回复
	 * @param request
	 * @param response
	 * @param model
	 * @author mengdz
	 * 2014年11月24日
	 */
	@RequestMapping(value = "/manualreplypost.do")
	public void manualreplypost(HttpServletRequest request,HttpServletResponse response			
			,Model model) {
		String touser=ParamUtil.getParameter(request, "touser");
		String content=null;
		try {
			content = URLDecoder.decode(ParamUtil.getParameter(request, "content"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}		
		Integer id=ParamUtil.getIntegerParameter(request, "id");
		AccessToken at =WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);
		JSONObject jo=WeixinUtil.sendTextInfo(touser, content, at.getToken());
		if(jo!=null&&jo.getInt("errcode")==0){
			//本地记录回复内容
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("answer_set", content);
			map.put("id", id);
			questionrecordDao.getInstance().update(map);			
		}
		MessageKit.displayMessage(response, jo.toString());
	}
}
