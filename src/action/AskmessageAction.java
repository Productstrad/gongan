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
import dao.AskmessageDao;
import vo.Askmessage;

import java.util.Date;


@Controller
@RequestMapping("/sys/askmessage")
public class AskmessageAction {
	
	private Logger log = LoggerFactory.getLogger(AskmessageAction.class);		
	private AskmessageDao askmessageDao=AskmessageDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String weixincode=ParamUtil.getStringParameter(request,"weixincode");
		String askcontent=ParamUtil.getStringParameter(request,"askcontent");
		Integer isReply=ParamUtil.getIntegerParameter(request,"isReply");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("weixincode", weixincode);
		params.put("askcontent", askcontent);
		params.put("isReply", isReply);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", askmessageDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(askmessageDao.findCount(params), pageSize, request, model);		
		return "askmessage/list";
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
		Askmessage vo=askmessageDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "askmessage/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "askcontent_set"))){
			paramsMap.put("askcontent_set", ParamUtil.getStringParameter(request, "askcontent_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "isReply_set"))){
			paramsMap.put("isReply_set", ParamUtil.getIntegerParameter(request, "isReply_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "weixincode"))){
			paramsMap.put("weixincode", ParamUtil.getStringParameter(request, "weixincode"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "askcontent"))){
			paramsMap.put("askcontent", ParamUtil.getStringParameter(request, "askcontent"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "isReply"))){
			paramsMap.put("isReply", ParamUtil.getIntegerParameter(request, "isReply"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=askmessageDao.update(paramsMap);
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
		String askcontent=ParamUtil.getStringParameter(request,"askcontent");
		Integer isReply=ParamUtil.getIntegerParameter(request,"isReply");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Askmessage vo=new Askmessage();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setAskcontent(askcontent);
		vo.setIsReply(isReply);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=askmessageDao.update(vo);
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
		return "askmessage/add";
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
		String askcontent=ParamUtil.getStringParameter(request,"askcontent");
		Integer isReply=ParamUtil.getIntegerParameter(request,"isReply");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Askmessage vo=new Askmessage();
		vo.setId(id);
		vo.setWeixincode(weixincode);
		vo.setAskcontent(askcontent);
		vo.setIsReply(isReply);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=askmessageDao.insert(vo);
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
		int effect=askmessageDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
