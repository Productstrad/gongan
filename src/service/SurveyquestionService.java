package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.SurveyDao;
import dao.SurveyquestionDao;
import dao.SurveyrecordDao;
import vo.Survey;
import vo.Surveyquestion;
import vo.Surveyrecord;

public class SurveyquestionService {
	
		private Logger logger = Logger.getLogger(SurveyquestionService.class);
		static SurveyquestionService  instance=new SurveyquestionService();
		public static  SurveyquestionService  getInstance(){
			return instance;
		}
		private SurveyquestionService(){
			
		}
		/**
		 * 取当前用户该调查已回答问题的下一题，如该调查未参与过取该调查的第一道题,防止答题过程缓存丢失造成重新进入调查无法继续答题问题
		 * @param weixincode
		 * @param surveyId
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public Surveyquestion getUserCurrentQuestion(String weixincode,int surveyId) {	
			Surveyquestion currentQ=null;
			//查询当前已回答的题目@TODO 改为select disctinct(questionId) 效率更高
			List<Surveyrecord> rList=SurveyrecordService.getInstance().getQuestionRecords(weixincode,surveyId);			
			if(rList!=null&&rList.size()>0){//已经答过题
				StringBuilder temp=new StringBuilder();
				for (Surveyrecord r : rList) {
					temp.append(r.getQuestionId()).append(",");
				}				
				//查询当前未答题
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("surveyId", surveyId);
				params.put("idNotIn", temp.substring(0, temp.length()-1).toString());
				params.put("status", 1);
				currentQ=SurveyquestionDao.getInstance().findSingle(params);
			}else{//第一次进行该调查
				Map<String, Object> params=new HashMap<String, Object>();
				params.put("surveyId", surveyId);				
				params.put("status", 1);
				currentQ=SurveyquestionDao.getInstance().findSingle(params);
			}
			return currentQ;
		}
		
}
