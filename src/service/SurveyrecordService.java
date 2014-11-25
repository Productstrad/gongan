package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.SurveyrecordDao;
import vo.Surveyquestion;
import vo.Surveyrecord;

public class SurveyrecordService {
	
		private Logger logger = Logger.getLogger(SurveyrecordService.class);
		static SurveyrecordService  instance=new SurveyrecordService();
		public static  SurveyrecordService  getInstance(){
			return instance;
		}
		private SurveyrecordService(){
			
		}
		/**
		 * 查询某人某调查已回答的题目列表
		 * @param weixincode
		 * @param surveyId
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public List<Surveyrecord> getQuestionRecords(String weixincode,int surveyId) {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixincode", weixincode);
			params.put("surveyId", surveyId);			
			List<Surveyrecord> rList=SurveyrecordDao.getInstance().find(params,1,1000);
			return rList;
		}
		
		/**
		 * 取当前用户已回答的某个调查的题目数
		 * @param weixincode
		 * @param surveyId
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public int getQuestionRecordsCount(String weixincode,int surveyId){
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("weixincode", weixincode);
			params.put("surveyId", surveyId);					
			return SurveyrecordDao.getInstance().findDistinctQuestionCount(params);	
		}
}
