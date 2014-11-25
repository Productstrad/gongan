package vo;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dao.ItemiDao;


public class Itemii {
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private Integer itemIId;
	public Integer getItemIId() {
		return itemIId;
	}
	public void setItemIId(Integer itemIId) {
		this.itemIId = itemIId;
	}
	public String getItemIName(){
		return ItemiDao.getInstance().findByPK(itemIId).getItemIname();
	}
	private String itemIIname;
	public String getItemIIname() {
		return itemIIname;
	}
	public void setItemIIname(String itemIIname) {
		this.itemIIname = itemIIname;
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