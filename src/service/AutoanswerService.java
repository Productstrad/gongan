package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.AutoanswerDao;
import vo.Autoanswer;

public class AutoanswerService {
	
		private Logger logger = Logger.getLogger(AutoanswerService.class);
		static AutoanswerService  instance=new AutoanswerService();
		public static  AutoanswerService  getInstance(){
			return instance;
		}
		private AutoanswerService(){
			
		}
		
		private List<Autoanswer> keyWordsList=null;
		private long lastSetKeyWordListTime=0;
		/**
		 * 从缓存获取所有咨询问题关键词
		 * @return
		 * @author mengdz
		 * 2014年11月20日
		 */
		public List<Autoanswer> getKeyWordsList(){
			if(keyWordsList==null||((System.currentTimeMillis()-lastSetKeyWordListTime)/(1000*60)>60)||lastSetKeyWordListTime==0l){
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("status", 1);
				keyWordsList=AutoanswerDao.getInstance().find("id,keyWord", params, 1, Integer.MAX_VALUE);
				lastSetKeyWordListTime=System.currentTimeMillis();
			}
//			System.out.println(keyWordsList.hashCode());
			return keyWordsList;
		}
		/**
		 * 删除咨询问题关键词缓存
		 * 
		 * @author mengdz
		 * 2014年11月21日
		 */
		public String delKeyWordsList(){
			keyWordsList=null;
			return "1";
		}
}
