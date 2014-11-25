package vo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;


public class Surveyquestionoption {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String optionCotent;
	public String getOptionCotent() {
		return optionCotent;
	}
	public void setOptionCotent(String optionCotent) {
		this.optionCotent = optionCotent;
	}
	private Float optionScore;
	public Float getOptionScore() {
		return optionScore;
	}
	public void setOptionScore(Float optionScore) {
		this.optionScore = optionScore;
	}
	private Integer questionId;
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	private Integer optionOrder;
	public Integer getOptionOrder() {
		return optionOrder;
	}
	public void setOptionOrder(Integer optionOrder) {
		this.optionOrder = optionOrder;
	}
	private Integer status;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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