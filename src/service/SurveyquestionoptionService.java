package service;

import org.apache.log4j.Logger;

import vo.Surveyquestionoption;

public class SurveyquestionoptionService {
	
		private Logger logger = Logger.getLogger(SurveyquestionoptionService.class);
		static SurveyquestionoptionService  instance=new SurveyquestionoptionService();
		public static  SurveyquestionoptionService  getInstance(){
			return instance;
		}
		private SurveyquestionoptionService(){
			
		}
		
		
}
