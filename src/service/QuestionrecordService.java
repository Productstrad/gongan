package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import app.weixin.message.resp.TextMessage;
import app.weixin.util.MessageUtil;
import dao.AutoanswerDao;
import dao.QuestionrecordDao;
import util.StringUtil;
import vo.Autoanswer;
import vo.Questionrecord;

public class QuestionrecordService {
	
		private Logger logger = Logger.getLogger(QuestionrecordService.class);
		
		/**
		 * 自动回答,不用单例，因为计算费时，单例需要同步降低性能
		 * @param content 用户回复的内容
		 * @param toUserName
		 * @param fromUserName 提问者
		 * @param respType
		 * @return
		 * @author mengdz
		 * 2014年11月20日
		 */
		public String autoAnswer(String content,String toUserName,String fromUserName,String respType) {
			//一下逻辑不用单例，因为计算费时，单例需要同步降低性能
			//记录提问信息
			Questionrecord q=new Questionrecord();
			q.setCreateDate(new Date());
			q.setQuestion(content);
			q.setStatus(1);
			q.setWeixincode(fromUserName);			
			//取最相关答案
			List<Autoanswer> list=getAutoAnswer(content);
			//插入提问记录
			Autoanswer a=null;
			if(list!=null&&list.size()>0){				
				a=list.get(0);				
				q.setAutoAnswerId(a.getId());
			}
			QuestionrecordDao.getInstance().insert(q);
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
                if(a!=null){
                	textMessage.setContent(a.getContent());  
                }else{
                	textMessage.setContent("您的问题已经记录下来,由于涉及多个部门的协调,将在48小时内给于回复"); 
                }
                // 将文本消息对象转换成xml字符串  
                respMessage = MessageUtil.textMessageToXml(textMessage); 
            }
            return respMessage;
		}
		/**
		 * 取问题的最相关答案,不用单例，因为计算费时，单例需要同步降低性能
		 * @param question
		 * @return
		 * @author mengdz
		 * 2014年11月21日
		 */
		public List<Autoanswer> getAutoAnswer(String question){
			//取所有咨询问题关键词列表
			List<Autoanswer> keyWordsList=AutoanswerService.getInstance().getKeyWordsList();
//			System.out.println(keyWordsList.hashCode());
			//取该提问最相关的咨询答案id
			int curSimlar=0,maxSimlar=0,curOrder=0,maxOrder=0,i=0;//curSimlar:问句中关键词出现次数 curOrder:命中的关键词在关键词列表中的次序权重，越靠前权重越大
			Integer [] answerIds=new Integer []{0,0,0};//依次存储相关度最高的3个咨询答案id
			String[] temps=null;
			for (Autoanswer a : keyWordsList) {
				if(StringUtil.isNotNullorEmpty(a.getKeyWord())){
					//计算当前咨询答案和提问的相关度
					curSimlar=0;
					curOrder=0;
					temps=a.getKeyWord().split("，");
					for (i=0;i<temps.length;i++) {						
						if(question.indexOf(temps[i])>=0){							
							++curSimlar;//记录关键词命中次数
							curOrder=curOrder+100-i;//记录命中的关键词的排前程度
						}
						//System.out.println(q+"? "+a.getKeyWord()+":"+curSimlar);
					}					
					//若当前咨询答案相关度最大，重设answerIds
					if(curSimlar>maxSimlar){//关键词命中次数优先
						answerIds=reSetAnserId(a.getId(), answerIds);//重设相关度最高的几个咨询答案
						maxSimlar=curSimlar;
						maxOrder=curOrder;
					}else if(curSimlar==maxSimlar){//关键词命中次数相同的，根据命中的所有关键词的排前程度，越排前命中的越优先
						if(curOrder>maxOrder){
							answerIds=reSetAnserId(a.getId(), answerIds);
							maxOrder=curOrder;
						}
					}
					
				}				
			}
			//根据最相关咨询答案id返回答案
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("ids", Arrays.toString(answerIds).replace("[", "").replace("]", ""));
			List<Autoanswer> list=AutoanswerDao.getInstance().find("id,title,content", params, 1, Integer.MAX_VALUE);
			//根据相关度排序
			List<Autoanswer> newlist=new ArrayList<Autoanswer>();
			for(int j=0;j<answerIds.length;j++){
				for (Autoanswer a : list) {
					if(a.getId().equals(answerIds[j])){
						newlist.add(a);
						break;
					}
				}
			}
			return newlist;
		}		
		/**
		 * 设置第一个元素为curId,其它顺次右移
		 * @param curId
		 * @param answerIds
		 * @return
		 * @author mengdz
		 * 2014年11月20日
		 */
		private Integer [] reSetAnserId(Integer curId,Integer[] answerIds){
			for(int i=answerIds.length-1;i>0;i--){
				answerIds[i]=answerIds[i-1];
			}
			answerIds[0]=curId;
			return answerIds;
		}
		/**
		 * 从最相关的几条咨询答案中取相关性最强的一条
		 * @param answerIds
		 * @param list
		 * @return
		 * @author mengdz
		 * 2014年11月20日
		 */
		private Autoanswer getAutoAnswer(Integer[] answerIds,List<Autoanswer> list){
			for (Autoanswer a : list) {
				if(a.getId().equals(answerIds[0])){
					return a;
				}
			}
			return null;
		}
}
