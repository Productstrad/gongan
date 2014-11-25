package action;

import java.io.IOException;
import java.util.Date;
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

import common.Config;
import common.Constant;
import service.LoginService;
import util.Encryption;
import util.MessageKit;
import util.Page;
import util.ParamUtil;
import util.StringUtil;
import vo.Admin;
import dao.AdminDao;


@Controller
@RequestMapping("/sys/admin")
public class AdminAction {
	
	private Logger log = LoggerFactory.getLogger(AdminAction.class);	
	
	private AdminDao adminDao=AdminDao.getInstance();	
	
	@RequestMapping(value = "/list.do")
	public String list(HttpServletRequest request,HttpServletResponse response			
			,Model model) throws IOException {	
		if(Config.get("config/superManager/text()", "").indexOf(LoginService.getLoginAccount(request))<0){
			MessageKit.displayMessage(response, "你不是超级管理员");
			return null; 
		}
		
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String account=ParamUtil.getStringParameter(request,"account");
		String password=ParamUtil.getStringParameter(request,"password");
		String realName=ParamUtil.getStringParameter(request,"realName");
		Integer sex=ParamUtil.getIntegerParameter(request,"sex");
		String tel=ParamUtil.getStringParameter(request,"tel");
		String email=ParamUtil.getStringParameter(request,"email");
		String qq=ParamUtil.getStringParameter(request,"qq");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Date lastLoginTime=ParamUtil.getDateParameter(request,"lastLoginTime");
		Integer status=ParamUtil.getIntegerParameter(request,"status",1);
		
		Map params = new HashMap();
		params.put("id", id);
		params.put("account", account);
		params.put("password", password);
		params.put("realName", realName);
		params.put("sex", sex);
		params.put("tel", tel);
		params.put("email", email);
		params.put("qq", qq);
		params.put("createDate", createDate);
		params.put("lastLoginTime", lastLoginTime);
		params.put("status", status);
			
		int pageNo=Page.getCurrentPage(request);
		int pageSize=Page.getPageSize(request,20);		
		model.addAttribute("list", adminDao.find(params,pageNo,pageSize));			   
		Page.setPageBeans(adminDao.findCount(params), pageSize, request, model);		
		return "admin/list";
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
		Admin vo=adminDao.findByPK(id);
		model.addAttribute("vo", vo);
		return "admin/update";
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
			,Model model)throws Exception {		
		Map<String, Object> paramsMap=new HashMap<String, Object>();
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id_set"))){
			paramsMap.put("id_set", ParamUtil.getIntegerParameter(request, "id_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "account_set"))){
			paramsMap.put("account_set", ParamUtil.getStringParameter(request, "account_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "password_set"))){
			String password=Encryption.encode(ParamUtil.getStringParameter(request, "password_set")+Constant.EncryptionSalt, "SHA1");
			paramsMap.put("password_set", password);
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "realName_set"))){
			paramsMap.put("realName_set", ParamUtil.getStringParameter(request, "realName_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "sex_set"))){
			paramsMap.put("sex_set", ParamUtil.getIntegerParameter(request, "sex_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "tel_set"))){
			paramsMap.put("tel_set", ParamUtil.getStringParameter(request, "tel_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "email_set"))){
			paramsMap.put("email_set", ParamUtil.getStringParameter(request, "email_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "qq_set"))){
			paramsMap.put("qq_set", ParamUtil.getStringParameter(request, "qq_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate_set"))){
			paramsMap.put("createDate_set", ParamUtil.getDateParameter(request, "createDate_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "lastLoginTime_set"))){
			paramsMap.put("lastLoginTime_set", ParamUtil.getDateParameter(request, "lastLoginTime_set"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status_set"))){
			paramsMap.put("status_set", ParamUtil.getIntegerParameter(request, "status_set"));
		}
		
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "id"))){
			paramsMap.put("id", ParamUtil.getIntegerParameter(request, "id"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "account"))){
			paramsMap.put("account", ParamUtil.getStringParameter(request, "account"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "password"))){
			paramsMap.put("password", ParamUtil.getStringParameter(request, "password"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "realName"))){
			paramsMap.put("realName", ParamUtil.getStringParameter(request, "realName"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "sex"))){
			paramsMap.put("sex", ParamUtil.getIntegerParameter(request, "sex"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "tel"))){
			paramsMap.put("tel", ParamUtil.getStringParameter(request, "tel"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "email"))){
			paramsMap.put("email", ParamUtil.getStringParameter(request, "email"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "qq"))){
			paramsMap.put("qq", ParamUtil.getStringParameter(request, "qq"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "createDate"))){
			paramsMap.put("createDate", ParamUtil.getDateParameter(request, "createDate"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "lastLoginTime"))){
			paramsMap.put("lastLoginTime", ParamUtil.getDateParameter(request, "lastLoginTime"));
		}
		if(StringUtil.isNotNullorEmpty(ParamUtil.getStringParameter(request, "status"))){
			paramsMap.put("status", ParamUtil.getIntegerParameter(request, "status"));
		}
		int effect=adminDao.update(paramsMap);
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
		String account=ParamUtil.getStringParameter(request,"account");
		String password=ParamUtil.getStringParameter(request,"password");
		String realName=ParamUtil.getStringParameter(request,"realName");
		Integer sex=ParamUtil.getIntegerParameter(request,"sex");
		String tel=ParamUtil.getStringParameter(request,"tel");
		String email=ParamUtil.getStringParameter(request,"email");
		String qq=ParamUtil.getStringParameter(request,"qq");
		Date createDate=ParamUtil.getDateParameter(request,"createDate");
		Date lastLoginTime=ParamUtil.getDateParameter(request,"lastLoginTime");
		Integer status=ParamUtil.getIntegerParameter(request,"status");
			
		Admin vo=new Admin();
		vo.setId(id);
		vo.setAccount(account);
		vo.setPassword(password);
		vo.setRealName(realName);
		vo.setSex(sex);
		vo.setTel(tel);
		vo.setEmail(email);
		vo.setQq(qq);
		vo.setCreateDate(createDate);
		vo.setLastLoginTime(lastLoginTime);
		vo.setStatus(status);
				
		int effect=adminDao.update(vo);
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
		return "admin/add";
	 }
	 
	 /**
	 * add 提交
	 * @param request
	 * @param response
	 * @param model	 
	 */
	@RequestMapping(value = "/addpost.do")
	public void addpost(HttpServletRequest request,HttpServletResponse response			
			,Model model)throws Exception {	
		Integer id=ParamUtil.getIntegerParameter(request,"id");
		String account=ParamUtil.getStringParameter(request,"account");
		if(!StringUtil.isNotNullorEmpty(account)){
			MessageKit.displayJsonResult(response, 0, null, null, "账户不能为空");
			return;
		}
		String password=ParamUtil.getStringParameter(request,"password");
		if(!StringUtil.isNotNullorEmpty(password)){
			MessageKit.displayJsonResult(response, 0, null, null, "密码不能为空");
			return;
		}
		password=Encryption.encode(password+Constant.EncryptionSalt, "SHA1");
		String realName=ParamUtil.getStringParameter(request,"realName");
		Integer sex=ParamUtil.getIntegerParameter(request,"sex");
		String tel=ParamUtil.getStringParameter(request,"tel");
		String email=ParamUtil.getStringParameter(request,"email");
		String qq=ParamUtil.getStringParameter(request,"qq");
		Date createDate=new Date();
		Date lastLoginTime=createDate;
		Integer status=ParamUtil.getIntegerParameter(request,"status");
		//检测是否已有账户
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("account", account);
		if(adminDao.findSingle(params)!=null){
			MessageKit.displayJsonResult(response, 0, null, null, "已有该账户名");
			return;
		}
		
		Admin vo=new Admin();
		vo.setId(id);
		vo.setAccount(account);
		vo.setPassword(password);
		vo.setRealName(realName);
		vo.setSex(sex);
		vo.setTel(tel);
		vo.setEmail(email);
		vo.setQq(qq);
		vo.setCreateDate(createDate);
		vo.setLastLoginTime(lastLoginTime);
		vo.setStatus(status);
				
		int effect=adminDao.insert(vo);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
	/**
	 * 批量删除 
	 * @param request
	 * @param response
	 * @param model
	 * @author mengdz
	 * 2014年10月21日
	 */
	@RequestMapping(value = "/delete.do")
	public void delete(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		String[] ids=request.getParameterValues("ids[]");
		int effect=adminDao.delete(ids);
		MessageKit.displayJsonResult(response, effect, null, null, null);
	 }
}
