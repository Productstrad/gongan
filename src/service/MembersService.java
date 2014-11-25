package service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import app.weixin.pojo.AccessToken;
import app.weixin.util.WeixinUtil;
import dao.MembersDao;
import vo.Members;

public class MembersService {
	
		private Logger logger = Logger.getLogger(MembersService.class);
		static MembersService  instance=new MembersService();
		public static  MembersService  getInstance(){
			return instance;
		}
		private MembersService(){
			
		}
		/**
		 * 记录关注、访问情况
		 * @param fromUserName
		 * @return
		 * @author mengdz
		 * 2014年11月21日
		 */
		public String visitRecord(String fromUserName) {
			//查找是否有记录
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixincode", fromUserName);
			params.put("status", 1);
			Members m=MembersDao.getInstance().findSingle(params);
			if(m!=null){				
				params.put("lastaccesstime_set", new Date());
				MembersDao.getInstance().update(params);
			}else{
				m=new Members();
				m.setCreateDate(new Date());				
				m.setStatus(1);
				m.setWeixincode(fromUserName);
				//取粉丝信息	
				AccessToken at =WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);
				JSONObject jObj=WeixinUtil.getFansInfo(fromUserName, at.getToken());
				if(jObj!=null){
					m.setCity(jObj.getString("city"));
					m.setProvince(jObj.getString("province"));
					m.setCountry(jObj.getString("country"));
					m.setSex(jObj.getInt("sex"));
					m.setHeadimgurl(jObj.getString("headimgurl"));
					m.setNickname(jObj.getString("nickname"));
					m.setFocusTime(new Date(jObj.getLong("subscribe_time")*1000));
				}
				MembersDao.getInstance().insert(m);
			}
			return "1";
		}		
}
