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

import service.LoginService;
import util.JsonUtil;
import util.ParamUtil;
import util.MessageKit;
import util.Page;
import util.StringUtil;
import dao.ItemiiDao;
import vo.Itemii;

import java.util.Date;


@Controller
@RequestMapping("/sys/itemii")
public class ItemiiAction {
	
	private Logger log = LoggerFactory.getLogger(ItemiiAction.class);	
	
	private ItemiiDao itemiiDao=ItemiiDao.getInstance();	
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		Integer itemIId=ParamUtil.getIntegerParameter(request,"itemIId");
		String itemIIname=ParamUtil.getStringParameter(request,"itemIIname");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("itemIId", itemIId);
		params.put("itemIIname", itemIIname);
		params.put("userid", userid);
		params.put("createDate", createDate);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("itemIId", itemIId);	
		model.addAttribute("list", itemiiDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(itemiiDao.findCount(params), pageSize, request, model);		
		return "itemii/list";
	 }	
	
	@RequestMapping(value = "/getitemII.do")
	public void getitemII(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		Integer itemIId=ParamUtil.getIntegerParameter(request,"itemIId");
		if(itemIId==null){
			MessageKit.displayMessage(response, "一级栏目id为空");
			return;
		}
		String itemIIname=ParamUtil.getStringParameter(request,"itemIIname");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();		
		params.put("itemIId", itemIId);				
		params.put("status", 1);
			
		List<Itemii> list=itemiiDao.find(params,1,Integer.MAX_VALUE);	
		StringBuilder options=new StringBuilder();
		if(list==null||list.size()==0){
			options.append("<option value=\"0\">无二级栏目</option><option value=\"0\">无二级栏目</option>");
			MessageKit.displayMessage(response, options.toString());
			return;
		}
		
		for (Itemii item : list) {
			options.append("<option value=\"").append(item.getId()).append("\">").append(item.getItemIIname()).append("</option>");
		}
		
		MessageKit.displayMessage(response, options.toString());
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
		Itemii vo=itemiiDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "itemii/update";
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIId_set"))){
			paramsMap.put("itemIId_set", ParamUtil.getIntegerParameter(request, "itemIId_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIIname_set"))){
			paramsMap.put("itemIIname_set", ParamUtil.getStringParameter(request, "itemIIname_set"));
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
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIId"))){
			paramsMap.put("itemIId", ParamUtil.getIntegerParameter(request, "itemIId"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "itemIIname"))){
			paramsMap.put("itemIIname", ParamUtil.getStringParameter(request, "itemIIname"));
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
		int effect=itemiiDao.update(paramsMap);
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
		Integer itemIId=ParamUtil.getIntegerParameter(request,"itemIId");
		String itemIIname=ParamUtil.getStringParameter(request,"itemIIname");
		Integer userid=ParamUtil.getIntegerParameter(request,"userid");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Itemii vo=new Itemii();
		vo.setId(id);
		vo.setItemIId(itemIId);
		vo.setItemIIname(itemIIname);
		vo.setUserid(userid);
		vo.setCreateDate(createDate);
		vo.setStatus(status);
				
		int effect=itemiiDao.update(vo);
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
		Integer itemIId=ParamUtil.getIntegerParameter(request,"itemIId");
		if(itemIId==null){
			MessageKit.displayJsonResult(response, 0, null, null, "一级栏目不能为空");
			return null;
		}
		model.addAttribute("itemIId", itemIId);
		return "itemii/add";
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
		Integer itemIId=ParamUtil.getIntegerParameter(request,"itemIId");
		if(itemIId==null){
			MessageKit.displayJsonResult(response, 0, null, null, "一级栏目不能为空");
			return;
		}
		String itemIIname=ParamUtil.getStringParameter(request,"itemIIname");
		if(!StringUtil.isNotNullorEmpty(itemIIname)){
			MessageKit.displayJsonResult(response, 0, null, null, "栏目名称不能为空");
			return;
		}			
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
			
		Itemii vo=new Itemii();		
		vo.setItemIId(itemIId);
		vo.setItemIIname(itemIIname);
		vo.setUserid(LoginService.getLoginUserId(request));
		vo.setCreateDate(new Date());
		vo.setStatus(status);
				
		int effect=itemiiDao.insert(vo);
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
		int effect=itemiiDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
