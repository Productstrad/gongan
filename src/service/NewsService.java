package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.apache.log4j.Logger;

import util.HttpRequstUtil;
import util.JsonUtil;
import util.StringUtil;
import vo.News;
import app.weixin.message.resp.Article;
import app.weixin.message.resp.NewsMessage;
import app.weixin.pojo.AccessToken;
import app.weixin.util.MessageUtil;
import app.weixin.util.WeixinUtil;
import dao.NewsDao;

public class NewsService {
	
		private Logger logger = Logger.getLogger(NewsService.class);
		static NewsService  instance=new NewsService();
		public static  NewsService  getInstance(){
			return instance;
		}
		private NewsService(){
			
		}
		
		public String getXmlNewsList(Map<String, Object> paramsMap,String toUserName,String fromUserName,int pageNo,int pageSize,String respType) {
			String respMessage=null;
			NewsDao newsDao=NewsDao.getInstance();
			paramsMap.put("status", 1);
			List<News> list=newsDao.find(paramsMap,pageNo,pageSize);
			int count=newsDao.findCount(paramsMap);
			if(list!=null&&list.size()>0){
				List<Article> articleList = new ArrayList<Article>();
				for (News n : list) {
					Article a = new Article();
					a.setTitle(n.getTitle());  
				    a.setDescription("");  
				    a.setPicUrl(n.getCompletePicPath());  
				    a.setUrl(StringUtil.isNotNullorEmpty(n.getJumpURL())?n.getCompleteJumpURL():n.getCompleteNewsUrl());
				    articleList.add(a);
				}			
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
			}
	        return respMessage;
		}
		/**
		 * 更新新闻点击数
		 * @param id
		 * @author mengdz
		 * 2014年11月6日
		 */
		public void addClicks(int id) {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("id", id);
			params.put("clicksAdd_set", 1);
			NewsDao.getInstance().update(params);
		}
		/**
		 * 推送新闻,
		 * @param list 目前仅支持一次推送一条信息
		 * @return
		 * @author mengdz
		 * 2014年11月17日
		 */
		public String sendNews(List<News> list) {
			if(list==null||list.size()==0){
				return "没有新闻";
			}
			AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.appId, WeixinUtil.appSecret);			
			if (null != at) {
				List<HashMap<String, Object>> newsMap=new ArrayList<HashMap<String, Object>>();
				String localRootPath=NewsService.class.getClassLoader().getResource("").getPath();
				localRootPath=localRootPath.substring(1,localRootPath.indexOf("WEB-INF")-1);
				for (News n : list) {
					HashMap<String, Object> map=new HashMap<String, Object>();
					//上传该新闻的媒体文件
					if(StringUtil.isNotNullorEmpty(n.getPicpath())||StringUtil.isNotNullorEmpty(n.getMediapath())){
						//@TODO增加多媒体上传功能						
						//上传媒体文件
						Map mapResult=null;
						String uploadMediaResult=WeixinUtil.uploadMedia(localRootPath+n.getPicpath(), at.getToken(), "image");//图片虚拟路径是否可以？
						try{
							mapResult=(Map)JsonUtil.toBean(uploadMediaResult, HashMap.class);
						}catch(Exception e){
							logger.error("",e);
						}
						//模拟数据
//						mapResult=new HashMap<String, Object>();
//						mapResult.put("media_id", "kvRyyniIEgeJsJqyEomENXlt3fn4A1hdVx0IK6rdPd_emlnAe1mxQo2q5ymS_6cz");
						if(mapResult!=null&&mapResult.get("media_id")!=null){
							map.put("thumb_media_id", mapResult.get("media_id").toString());
							map.put("author", n.getAccount());
							map.put("title", n.getTitle());
							map.put("content_source_url", n.getCompleteNewsUrl());
							map.put("content", n.getContent());
		//					map.put("digest", null);
		//					map.put("show_cover_pic", null);
							newsMap.add(map);
						}
					}
				}
				if(newsMap.size()>0){
					Map<String, Object> paramsMap=new HashMap<String, Object>();
					paramsMap.put("articles", newsMap);
					String newsWeixinId = WeixinUtil.uploadNews(paramsMap, at.getToken());
					//模拟数据
//					String newsWeixinId ="bZqJQG5EBFoBgBGxUyimStjAMvYBPD91kygohMVoECXhmbgrwuLN0qXS65XDdt2c";
		            if (!"-1".equals(newsWeixinId)){ //新闻上传成功 
		                //取所有分组信息
		            	JSONArray allGroup=WeixinUtil.getAllGroup(at.getToken());
		            	if(allGroup!=null&&allGroup.size()>0){		            		
		            		Map<String, Object> filterMap=new HashMap<String, Object>();
		            		Map<String, Object> mediaIdMap=new HashMap<String, Object>();
		            		mediaIdMap.put("media_id", newsWeixinId);	
		            		Map<String, Object> params=new HashMap<String, Object>();
		            		params.put("mpnews", mediaIdMap);
			            	params.put("msgtype", "mpnews");			            	
			            	//遍历分组群发新闻
			            	for (Object o : allGroup) {
			            		filterMap.put("group_id",((JSONObject)o).get("id"));
				            	params.put("filter", filterMap);
				            	// 推送图文消息
				            	WeixinUtil.qunSendSMS(params, at.getToken());
							}
			            	//更新新闻推送标志位
			            	setTuisong(list.get(0).getId()); 
			            	return "推送新闻成功";
		            	}
		            }
				}
			}			
			return "推送新闻失败";
		}
		
		public int setTuisong(int id) {
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("tuisongFlag_set", 1);
			params.put("id", id);
			return NewsDao.getInstance().update(params);
		}
		
		public static void main(String[] args) {
//			Map<String, Object> params=new HashMap<String, Object>();
//			params.put("itemI", 6);
//    		params.put("itemII", 4);
//            System.out.println(NewsService.getInstance().getXmlNewsList(params,"","", 1, 4, MessageUtil.RESP_MESSAGE_TYPE_NEWS));
			
//			String t=NewsService.class.getClassLoader().getResource("").getPath();
//			t=t.substring(1,t.indexOf("WEB-INF")-1);
//			System.out.println(t);
			
			//一下逻辑不用单例，因为计算费时，单例需要同步降低性能
//			long start=System.currentTimeMillis();
//			List<String[]> tagsList=new ArrayList<String[]>();
//			String[] answertags="都 是 东 方 闪电 sdf 进 决 赛了 fda jl jl 减肥 的 说了句  防辐 射的 解放 路见 身份证 挂失".split(" ");
//			for(int i=0;i<1000000;i++){
//				//tagsList.add(answertags);
//			}
//			tagsList.add("户口 挂失 流程 是  怎样".split(" "));
//			String question="户口挂失流程是怎样的？";
//			int curSimlar=0,maxSimlar=0,i=0,answerId=0;
//			for (String[] tags : tagsList) {
//				curSimlar=0;
//				for(i=0;i<tags.length;i++){
//					if(question.indexOf(tags[i].trim())>=0){
//						++curSimlar;
//					}
//				}
//				if(curSimlar>maxSimlar){
//					maxSimlar=curSimlar;
//					answerId=maxSimlar;//此处模拟，实际就是有更大相关度的答案，则更新answerId
//				}
//			}
//			long end=System.currentTimeMillis();
//			System.out.println(answerId);
//			System.out.println(end-start);
			
			
		}
}
