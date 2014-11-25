package vo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import util.StringUtil;
import dao.MembersDao;


public class Questionrecord {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String weixincode;
	public String getWeixincode() {
		return weixincode;
	}
	public void setWeixincode(String weixincode) {
		this.weixincode = weixincode;
	}
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	private String question;
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	private Integer autoAnswerId;
	public Integer getAutoAnswerId() {
		return autoAnswerId;
	}
	public void setAutoAnswerId(Integer autoAnswerId) {
		this.autoAnswerId = autoAnswerId;
	}
	private String answer;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	private Integer status;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNickname(){
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("weixincode", weixincode);
		Members m=MembersDao.getInstance().findSingle(params);
		if(m!=null){
			return m.getNickname();
		}
		return null;
	}
	public String getReplyState(){
		if((autoAnswerId!=null&&autoAnswerId>0)||StringUtil.isNotNullorEmpty(answer)){
			return "已回复";
		}
		return "未回复";
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