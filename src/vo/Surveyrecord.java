package vo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;

import dao.MembersDao;
import dao.SurveyDao;
import dao.SurveyquestionDao;
import dao.SurveyquestionoptionDao;


public class Surveyrecord {
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	private Integer surveyId;
	public Integer getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Integer surveyId) {
		this.surveyId = surveyId;
	}
	private Integer questionId;
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	private Integer optionId;
	public Integer getOptionId() {
		return optionId;
	}
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	private String answer;
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Survey getSurvey(){		
		return SurveyDao.getInstance().findByPK(surveyId);		
	}
	
	public Surveyquestion getQuestion(){		
		return SurveyquestionDao.getInstance().findByPK(questionId);		
	}
	
	public Surveyquestionoption getOption(){
		try{
			return SurveyquestionoptionDao.getInstance().findByPK(optionId);
		}catch(Exception e){
			
		}
		return new Surveyquestionoption();
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