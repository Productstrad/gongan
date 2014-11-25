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
import dao.MedialibDao;
import vo.Medialib;

import java.util.Date;


@Controller
@RequestMapping("/sys/medialib")
public class MedialibAction {
	
	private Logger log = LoggerFactory.getLogger(MedialibAction.class);		
	private MedialibDao medialibDao=MedialibDao.getInstance();
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String mediaTitle=ParamUtil.getStringParameter(request,"mediaTitle");
		String mediaPath=ParamUtil.getStringParameter(request,"mediaPath");
		Integer mediaType=ParamUtil.getIntegerParameter(request,"mediaType");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("mediaTitle", mediaTitle);
		params.put("mediaPath", mediaPath);
		params.put("mediaType", mediaType);
		params.put("userid", userid);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", medialibDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(medialibDao.findCount(params), pageSize, request, model);		
		return "medialib/list";
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
		Medialib vo=medialibDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "medialib/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaTitle_set"))){
			paramsMap.put("mediaTitle_set", ParamUtil.getStringParameter(request, "mediaTitle_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaPath_set"))){
			paramsMap.put("mediaPath_set", ParamUtil.getStringParameter(request, "mediaPath_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaType_set"))){
			paramsMap.put("mediaType_set", ParamUtil.getIntegerParameter(request, "mediaType_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaTitle"))){
			paramsMap.put("mediaTitle", ParamUtil.getStringParameter(request, "mediaTitle"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaPath"))){
			paramsMap.put("mediaPath", ParamUtil.getStringParameter(request, "mediaPath"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "mediaType"))){
			paramsMap.put("mediaType", ParamUtil.getIntegerParameter(request, "mediaType"));
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
		int effect=medialibDao.update(paramsMap);
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
		String mediaTitle=ParamUtil.getStringParameter(request,"mediaTitle");
		String mediaPath=ParamUtil.getStringParameter(request,"mediaPath");
		Integer mediaType=ParamUtil.getIntegerParameter(request,"mediaType");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Medialib vo=new Medialib();
		vo.setId(id);
		vo.setMediaTitle(mediaTitle);
		vo.setMediaPath(mediaPath);
		vo.setMediaType(mediaType);
		vo.setUserid(userid);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=medialibDao.update(vo);
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
		return "medialib/add";
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
		String mediaTitle=ParamUtil.getStringParameter(request,"mediaTitle");
		String mediaPath=ParamUtil.getStringParameter(request,"mediaPath");
		Integer mediaType=ParamUtil.getIntegerParameter(request,"mediaType");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Medialib vo=new Medialib();
		vo.setId(id);
		vo.setMediaTitle(mediaTitle);
		vo.setMediaPath(mediaPath);
		vo.setMediaType(mediaType);
		vo.setUserid(userid);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=medialibDao.insert(vo);
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
		int effect=medialibDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
