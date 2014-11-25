package vo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import util.StringUtil;

import com.sun.xml.internal.ws.wsdl.writer.UsingAddressing;

import common.Config;
import common.Constant;
import dao.AdminDao;
import dao.ItemiDao;
import dao.ItemiiDao;
import dao.NewsDao;


public class News {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private int tuisongFlag;
	public int getTuisongFlag() {
		return tuisongFlag;
	}
	public void setTuisongFlag(int tuisongFlag) {
		this.tuisongFlag = tuisongFlag;
	}
	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	private Integer itemI;
	public Integer getItemI() {
		return itemI;
	}
	public void setItemI(Integer itemI) {
		this.itemI = itemI;
	}
	private Integer itemII;
	public Integer getItemII() {
		return itemII;
	}
	public void setItemII(Integer itemII) {
		this.itemII = itemII;
	}
	private String content;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String picpath;
	public String getPicpath() {
		return picpath;
	}
	
	public String getCompletePicPath(){
		String temp=null;
		if(StringUtil.isNotNullorEmpty(picpath)){			
			if(picpath.indexOf(Config.get("config/uploadPicConfig/rootPicDrectoty/text()", null))>=0){//说明是本地上传
				Constant.tempBuffer.delete(0, Constant.tempBuffer.length());
				temp=Constant.tempBuffer.append(Config.get("config/domain/text()", null)).append("/").append(Config.get("config/uploadPicConfig/rootPicDrectoty/text()",null)).append(picpath).toString();
				Constant.tempBuffer.delete(0, Constant.tempBuffer.length());//释放内存
			}else{//粘贴的外链图片
				temp=picpath;
			}
			if(temp!=null&&temp.indexOf("http://")<0){
				temp="http://"+temp;
			}
		}		
		return temp;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	private String mediapath;
	public String getMediapath() {
		return mediapath;
	}
	public String getCompleteMedizPath(){
		String temp=null;
		if(StringUtil.isNotNullorEmpty(mediapath)){
			if(mediapath.indexOf(Config.get("config/uploadMediaConfig/rootMediaDrectoty/text()", null))>=0){//说明是本地上传
				Constant.tempBuffer.delete(0, Constant.tempBuffer.length());
				temp=Constant.tempBuffer.append(Config.get("config/domain/text()", null)).append("/").append(Config.get("config/uploadMediaConfig/rootMediaDrectoty/text()",null)).append(mediapath).toString();
				Constant.tempBuffer.delete(0, Constant.tempBuffer.length());//释放内存
			}else{
				temp=mediapath;
			}
		}
		return temp;
	}
	public void setMediapath(String mediapath) {
		this.mediapath = mediapath;
	}
	public String getCompleteNewsUrl(){	
		Constant.tempBuffer.delete(0, Constant.tempBuffer.length());
		String temp=Constant.tempBuffer.append("http://").append(Config.get("config/domain/text()", null)).append("/news/read.do?id=").append(id).toString();
		Constant.tempBuffer.delete(0, Constant.tempBuffer.length());//释放内存
		return temp;
	}
	private String jumpURL;
	public String getJumpURL() {
		return jumpURL;
	}
	public void setJumpURL(String jumpURL) {
		this.jumpURL = jumpURL;
	}
	public String getCompleteJumpURL(){	
		if(StringUtil.isNotNullorEmpty(jumpURL)){
			if(jumpURL.indexOf("http://")<0){
				return "http://"+jumpURL;
			}
		}
		return jumpURL;
	}
	private Integer userid;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	private Integer clicks;
	public Integer getClicks() {
		return clicks;
	}
	public void setClicks(Integer clicks) {
		this.clicks = clicks;
	}
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public String getCreateDateString(){
		return Constant.dateFormat.format(createDate);
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	private Integer newsType;
	public Integer getNewsType() {
		return newsType;
	}
	public void setNewsType(Integer newsType) {
		this.newsType = newsType;
	}
	private Integer status;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public String getItemIName(){//不可增加	itemIName属性，以免引起insert时自动增加该字段	
		try{
			if(itemI>0){
				return ItemiDao.getInstance().findByPK(itemI).getItemIname();
			}
		}catch(Exception e){}
		return "";
	}
	public String getItemIIName(){
		try{
			if(itemII>0){
				return ItemiiDao.getInstance().findByPK(itemII).getItemIIname();
			}
		}catch(Exception e){}
		return "";
	}
	public String getAccount(){
		try{
			if(userid>0){
				return AdminDao.getInstance().findByPK(userid).getAccount();
			}
		}catch(Exception e){}
		return "";
	}
	@Override
	public String toString() {
		Map<String, Object> values=new HashMap<String, Object>();
		Field[] fields=this.getClass().getDeclaredFields();
		try{
			for (Field f : fields) {
			values.put(f.getName(), f.get(this));
			}
			return values.toString();
		}catch(Exception e){
			return e.toString();
		}
	}
}