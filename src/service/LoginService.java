/**
 * 
 */
package service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.omg.CORBA.DATA_CONVERSION;

import util.CookieUtil;
import util.Encryption;
import util.ParamUtil;
import util.StringUtil;
import vo.Admin;
import common.Constant;
import dao.AdminDao;

/**
 * @author mendz
 *	2014年10月22日
 */
public class LoginService {

	private static Logger logger = Logger.getLogger(LoginService.class);
	
	public String login(HttpServletRequest request,HttpServletResponse response) {
		try{
			String passWord=ParamUtil.getParameter(request, "passWord");
			String account=ParamUtil.getParameter(request, "account");
			String authImgContent=ParamUtil.getParameter(request, "authImgContent");
			if(!StringUtil.isNotNullorEmpty(account)){				
				return "请填写账户";
			}
			if(!StringUtil.isNotNullorEmpty(passWord)){
				return "请输入密码";
			}
			if(!StringUtil.isNotNullorEmpty(authImgContent)){
				return "请输入验证码";
			}
			if(!authImgContent.equals(request.getSession(true).getAttribute("authImgContent"))){
				return "验证码不对";
			}
			String ePassWord=Encryption.encode(passWord+Constant.EncryptionSalt, "SHA1");
			Map<String, Object> paramsMap=new HashMap<String, Object>();
			paramsMap.put("account", account);	
			AdminDao aDao=AdminDao.getInstance();
			List<Admin> list=aDao.find(paramsMap, 1, 100);
			if(list!=null&&list.size()>0){
				for (Admin admin : list) {
					if(ePassWord.equals(admin.getPassword())){
						//写cookie
						int time=-1;//默认关闭浏览器清除cookies
						if("1".equals(ParamUtil.getParameter(request, "auto"))){
							time=1209600;
						}
						CookieUtil cookieUtil=new CookieUtil();							
						cookieUtil.setCookie(response, Constant.domain, "account", admin.getAccount(), time);						
						cookieUtil.setCookie(response, Constant.domain, "userId", admin.getId().toString(), time);						
						String lastLoginTime=Constant.dateTimeFormat.format(admin.getLastLoginTime());						
						cookieUtil.setCookie(response, Constant.domain, "lastLoginTime",lastLoginTime, time);
						Map<String, Object> params=new HashMap<String, Object>();
						params.put("account", admin.getAccount());
						params.put("userId", admin.getId());
						params.put("lastLoginTime", lastLoginTime);
						cookieUtil.setCookie(response, Constant.domain, "sign", getLoginSign(params), time);
						//更新最后登录时间
						paramsMap.clear();
						paramsMap.put("lastLoginTime_set", new Date());
						paramsMap.put("id", admin.getId());
						aDao.update(paramsMap);
						//返回结果
						return "1";
					}
				}
				return "密码不对";
			}else{
				return "没有该账户";
			}
		}catch(Exception e){
			logger.error("",e);
			return "系统异常";
		}		
	}
	
	public static String getLoginSign(Map<String, Object> params) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		StringBuilder orginString=new StringBuilder();
		orginString.append(params.get("account"));
		orginString.append(Constant.EncryptionSalt);
		orginString.append(params.get("lastLoginTime"));
		orginString.append(params.get("userId"));
		return Encryption.encode(orginString.toString(), "SHA1");
	}
	/**
	 * 判断是否在线
	 * @param request
	 * @param response
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 * @author mengdz
	 * 2014年10月27日
	 */
	public static boolean onlineCheck(HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("account", CookieUtil.getCookieValue(request, "account"));
		params.put("userId", CookieUtil.getCookieValue(request, "userId"));
		params.put("lastLoginTime", CookieUtil.getCookieValue(request, "lastLoginTime"));
		try{
			if(getLoginSign(params).equals(CookieUtil.getCookieValue(request, "sign"))){//防止登录信息被修改
				return true;
			}
		}catch(Exception e){
			logger.error("是否在线验签出错", e);
		}
		return false;
	}
	
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				cookies[i].setDomain(Constant.domain);
				response.addCookie(cookies[i]);
//				String sp = cookies[i].getName();
//				if (sp.equals("logined")) {
//					cookies[i].setMaxAge(0);
//					cookies[i].setPath("/");
//					cookies[i].setDomain(Constant.domain);
//					response.addCookie(cookies[i]);
//				}
//				if (sp.equals("account")) {
//					cookies[i].setMaxAge(0);
//					cookies[i].setPath("/");
//					cookies[i].setDomain(Constant.domain);
//					response.addCookie(cookies[i]);
//				}									
			}
		}
		return "1";
	}
	/**
	 * 取登录用户id
	 * @param request
	 * @return
	 * @author mengdz
	 * 2014年10月27日
	 */
	public static Integer getLoginUserId(HttpServletRequest request) {
		String userId=CookieUtil.getCookieValue(request, "userId");
		if(StringUtil.isNotNullorEmpty(userId)){
			return Integer.valueOf(userId);
		}
		return 0;
	}
	/**
	 * 取登录用户名
	 * @param request
	 * @return
	 * @author mengdz
	 * 2014年10月27日
	 */
	public static String getLoginAccount(HttpServletRequest request) {
		return CookieUtil.getCookieValue(request, "account");		
	}
	
	public static String getLastLoginTime(HttpServletRequest request) {		
		return CookieUtil.getCookieValue(request, "lastLoginTime");
	}
}
