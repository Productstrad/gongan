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
import dao.ItemiDao;
import vo.Itemi;

import java.util.Date;


@Controller
@RequestMapping("/sys/itemi")
public class ItemiAction {
	
	private Logger log = LoggerFactory.getLogger(ItemiAction.class);	
	
	private ItemiDao itemiDao=ItemiDao.getInstance();	
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String itemIname=ParamUtil.getStringParameter(request,"itemIname");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("itemIname", itemIname);
		params.put("userid", userid);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", itemiDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(itemiDao.findCount(params), pageSize, request, model);		
		return "itemi/list";
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
		Itemi vo=itemiDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "itemi/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIname_set"))){
			paramsMap.put("itemIname_set", ParamUtil.getStringParameter(request, "itemIname_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIname"))){
			paramsMap.put("itemIname", ParamUtil.getStringParameter(request, "itemIname"));
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
		int effect=itemiDao.update(paramsMap);
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
		String itemIname=ParamUtil.getStringParameter(request,"itemIname");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Itemi vo=new Itemi();
		vo.setId(id);
		vo.setItemIname(itemIname);
		vo.setUserid(userid);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=itemiDao.update(vo);
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
		return "itemi/add";
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
		String itemIname=ParamUtil.getStringParameter(request,"itemIname");
		if(!StringUtil.isNotNullorEmpty(itemIname)){
			MessageKit.displayJsonResult(response, 0, null, null, "栏目名称不能为空");
			return;
		}				
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
			
		Itemi vo=new Itemi();		
		vo.setItemIname(itemIname);
		vo.setUserid(LoginService.getLoginUserId(request));
		vo.setCreateDate(new Date());
		vo.setStatus(status);
				
		int effect=itemiDao.insert(vo);
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
		int effect=itemiDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
