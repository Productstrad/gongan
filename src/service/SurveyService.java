package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.org.apache.bcel.internal.generic.ISUB;

import common.Constant;
import util.OsCache;
import vo.Survey;
import vo.Surveyquestion;
import vo.Surveyquestionoption;
import vo.Surveyrecord;
import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.message.resp.TextMessage;
import app.weixin.util.MessageUtil;
import app.weixin.util.WeixinUtil;
import dao.SurveyDao;
import dao.SurveyquestionDao;
import dao.SurveyquestionoptionDao;
import dao.SurveyrecordDao;

public class SurveyService {
	
		private Logger logger = Logger.getLogger(SurveyService.class);
		static SurveyService  instance=new SurveyService();
		public static  SurveyService  getInstance(){
			return instance;
		}
		private SurveyService(){
			
		}
		public String[] optionFlag=new String[]{"a","b","c","d","e","f","g","h"};//选项标志，用于选择题,个数与optionMaxNum一致
		private int cacheTime=3600;//60分钟
//		public String getOptionFlagString(){
//			StringBuilder temp=new StringBuilder();
//			for (char c : optionFlag) {
//				temp.append(" ").append(c);
//			}
//			temp.append(" ");
//			return temp.toString();
//		}
		public String surveyAnserFlag="回答";
		public int optionMaxNum=8;
		/**
		 * 检测用户信息是否为调查反馈信息格式
		 * @param content
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public String isSurveyFormat(String content,String fromUserName) {			
        	String isSurveyFormat="0";        	
        	char[] ch = content.toLowerCase().toCharArray();            	
        	for (char c : ch) {
				if(!Arrays.asList(SurveyService.getInstance().optionFlag).contains(String.valueOf(c).toLowerCase())){//判断是否单、多选题回复格式					
		        	if(!content.startsWith(SurveyService.getInstance().surveyAnserFlag)){//判断是否问答题回复格式
		        		return isSurveyFormat;//既不是选择题，也不是问答题回复格式返回非调查回复内容标志位
		        	}
				}
			}
        	//判断是否当前题目可选项
//        	if(isSurveyFormat){//已经是字母类型回复
				try {					
					String	cache =(String)OsCache.getInstance().get(fromUserName+"Survey", cacheTime);//取缓存									
					if(cache!=null){
						String[] temp=cache.split(":");
						if(temp.length>1){//temp.length=1说明是问答题
							for (char c : ch) {//判断回复字母是否是待选字母
								if((temp[1]).indexOf(String.valueOf(c).toLowerCase()+"_")<0){
									isSurveyFormat="-2";
									break;
								}else{
									isSurveyFormat="1";
								}
							}
						}
					}
				} catch (Exception e) {
//					logger.error("",e);
					isSurveyFormat="-1";
				}				
//        	}        	
        	if(!"1".equals(isSurveyFormat)){
        		if(content.length()>surveyAnserFlag.length()){//有回复内容
        			isSurveyFormat="1";//
        		}
        	}
        	return isSurveyFormat;
		}
//		public static void main(String[] args) {
//			System.out.println(SurveyService.getInstance().isSurveyFormat("答题"));
//		}
		/**
		 * 取当前调查第一题，用于用户点击调查按钮时		
		 * @param toUserName 微信公众号
		 * @param fromUserName 用户
		 * @param respType
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public String getTopSurvey(String toUserName,String fromUserName,String respType) {
			Map<String, Object> params=new HashMap<String, Object>();
			//取调查
			params.put("status", 1);
			Survey s=SurveyDao.getInstance().findSingle(params);
			//取当前用户该调查已回答问题的下一题，如该调查未参与过取该调查的第一道题,防止答题过程缓存丢失造成重新进入调查无法继续答题问题
			Surveyquestion currentQ=SurveyquestionService.getInstance().getUserCurrentQuestion(fromUserName, s.getId());		
			//取得问题描述响应信息并设置缓存
			String respContent=dealCurrentQ(currentQ, toUserName, fromUserName);
			//返回响应
            String respMessage=null;
            if(MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(respType)){
            	TextMessage textMessage = new TextMessage();  
                textMessage.setToUserName(fromUserName);  
                textMessage.setFromUserName(toUserName);  
                textMessage.setCreateTime(new Date().getTime());  
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
                textMessage.setFuncFlag(0);  
                // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
                textMessage.setContent(s.getTitle()+"：\n"+respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage); 
            }
            return respMessage;
		}
		
		public String dealOptionSelect(String content,String toUserName,String fromUserName,String respType) {
			content=content.toLowerCase();
			String respMessage=null,respContent=null;
			try {								
				//取缓存
				String cache=(String)OsCache.getInstance().get(fromUserName+"Survey", cacheTime);//60分钟
				String[] temp=cache.split(":");
				//取调查id,问题id
				String[] temp3=temp[0].split("_");
				int surveyId=Integer.valueOf(temp3[0]);
				int questionId=Integer.valueOf(temp3[1]);				
				//取问题
				Surveyquestion q=SurveyquestionDao.getInstance().findByPK(questionId);
				//插入选择或回答记录
				Surveyrecord r=new Surveyrecord();
				r.setSurveyId(surveyId);
				r.setQuestionId(questionId);
				r.setWeixincode(fromUserName);
				if(q.getQuestionType().equals(0)||q.getQuestionType().equals(1)){//选择题
					int optionId=0;
					String[] temp1=temp[1].split(",");
					char[] ch = content.toCharArray();//回复内容
					for (String t : temp1) {//选项字母标志及选项id绑定记录
						optionId=0;
						for (char c : ch) {//回复内容
							if(t.indexOf(String.valueOf(c)+"_")>=0){
								String[] temp2=t.split("_");
								optionId=Integer.valueOf(temp2[1]);
								break;
							}
						}	
						if(optionId>0){
							r.setOptionId(optionId);
							r.setCreateDate(new Date());
							SurveyrecordDao.getInstance().insert(r);
							if(q.getQuestionType().equals(0)){//单选题只插入一次
								break;
							}
						}
					}
				}else if(q.getQuestionType().equals(2)||q.getQuestionType().equals(3)){//问答题
					r.setAnswer(content.replace(surveyAnserFlag, ""));				
					r.setCreateDate(new Date());
					SurveyrecordDao.getInstance().insert(r);
				}				
				//返回新的问题
				Surveyquestion currentQ=SurveyquestionService.getInstance().getUserCurrentQuestion(fromUserName, surveyId);
				//取得问题描述响应信息并设置缓存
				respContent=dealCurrentQ(currentQ, toUserName, fromUserName);
			} catch (Exception e) {
				logger.error("",e);
				respContent= "系统异常,请点击调查按钮重新继续答题";
			}		
			//返回响应	            
            if(MessageUtil.RESP_MESSAGE_TYPE_TEXT.equals(respType)){
            	TextMessage textMessage = new TextMessage();  
                textMessage.setToUserName(fromUserName);  
                textMessage.setFromUserName(toUserName);  
                textMessage.setCreateTime(new Date().getTime());  
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);  
                textMessage.setFuncFlag(0);  
                // 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义  
                textMessage.setContent(respContent);  
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage); 
            }
            return respMessage;
		}
		/**
		 * 根据当前问题设置缓存，并返回问题响应信息
		 * @param currentQ
		 * @param toUserName
		 * @param fromUserName
		 * @param respType
		 * @return
		 * @author mengdz
		 * 2014年11月6日
		 */
		public String dealCurrentQ(Surveyquestion currentQ,String toUserName,String fromUserName) {
			String respContent=null;
			if(currentQ==null){//已经答完最后一题
				//清空该用户缓存@TODO
				respContent="您已完成调查问卷,非常感谢您的参与！";
				return respContent;
			}
			Map<String, Object> params=new HashMap<String, Object>();
			//若为单、多选题取选项
			List<Surveyquestionoption> oList=null;
			if(currentQ.getQuestionType().equals(0)||currentQ.getQuestionType().equals(1)){
				//取该题的选项
				params.clear();
				params.put("questionId", currentQ.getId());
				params.put("status", 1);
				oList=SurveyquestionoptionDao.getInstance().find(params,1,optionMaxNum);
			}
			//拼接缓存值
			StringBuilder cacheString=new StringBuilder();//缓存格式： surveyId_questionId:optionFlag_optionId,optionFlag_optionId
			cacheString.append(currentQ.getSurveyId()).append("_").append(currentQ.getId()).append(":");
			//拼接返回响应
			Constant.tempBuffer.delete(0, Constant.tempBuffer.length());			
			Constant.tempBuffer.append("第").append(SurveyrecordService.getInstance().getQuestionRecordsCount(fromUserName, currentQ.getSurveyId())+1).append("题、").append(currentQ.getQuestion());
			if (currentQ.getQuestionType().equals(0)) {//单选题
				Constant.tempBuffer.append("(单选题,请回复选项字母)");
			}else if(currentQ.getQuestionType().equals(1)){
				Constant.tempBuffer.append("(多选题，多个选择请按格式：字母1字母2… 回复，如：bde)");
			}else if(currentQ.getQuestionType().equals(2)||currentQ.getQuestionType().equals(3)){//问题题、意见与建议
				Constant.tempBuffer.append("(问答题，请在答案前加\"").append(surveyAnserFlag).append("\"两字)");
			}
			Constant.tempBuffer.append("\n");
			if(oList!=null){
				for (int i=0;i<oList.size();i++) {				
					Constant.tempBuffer.append(optionFlag[i]).append("、").append(oList.get(i).getOptionCotent()).append("\n");
					//拼接缓存值,在缓存中绑定选项标志和选项id
					cacheString.append(optionFlag[i]).append("_").append(oList.get(i).getId());
					if(i<(oList.size()-1)){
						cacheString.append(",");
					}
				}
			}
			respContent=Constant.tempBuffer.toString();
			Constant.tempBuffer.delete(0, Constant.tempBuffer.length());	
			//设置缓存
			OsCache.getInstance().put(fromUserName+"Survey", cacheString.toString());
			
            return respContent;
		}
		
		public String getXmlList(Map<String, Object> paramsMap,String toUserName,String fromUserName,int pageNo,int pageSize,String respType) {
			SurveyDao surveyDao=SurveyDao.getInstance();
			List<Survey> list=surveyDao.find(paramsMap,pageNo,pageSize);
			//int count=newsDao.findCount(paramsMap);
			int count=1;
			List<Article> articleList = new ArrayList<Article>();
			for (Survey n : list) {
				Article a = new Article();
				a.setTitle(n.getTitle());  
			    a.setDescription("");  
			    //a.setPicUrl(n.getCompletePicPath());  
			    a.setUrl(n.getSurveyUrl());
			    articleList.add(a);
			}
			String respMessage=null;
			if(MessageUtil.RESP_MESSAGE_TYPE_NEWS.equals(respType)){
				//创建图文消息  
		        NewsMessage newsMessage = new NewsMessage();  
		        newsMessage.setToUserName(fromUserName);//此时原发送者为接受者  
		        newsMessage.setFromUserName(toUserName);  
		        newsMessage.setCreateTime(new Date().getTime());  
		        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);  
		        newsMessage.setFuncFlag(0); 
		        newsMessage.setArticleCount(count);  
			    newsMessage.setArticles(articleList);  
			    respMessage = MessageUtil.newsMessageToXml(newsMessage); 
			}
	        return respMessage;
		}
		
//		public Survey getCurrentSurvey() {
//			SurveyDao surveyDao=SurveyDao.getInstance();
//			Map<String, Object> paramsMap=new HashMap<String, Object>();
//			Survey survey=surveyDao.findSingle(paramsMap,pageNo,pageSize);
//		}
}
