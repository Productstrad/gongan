package vo;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;


public class Piclib {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String pictitle;
	public String getPictitle() {
		return pictitle;
	}
	public void setPictitle(String pictitle) {
		this.pictitle = pictitle;
	}
	private String picpath;
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	private Integer userid;
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	private Date createDate;
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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