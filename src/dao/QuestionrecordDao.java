package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.RowSet;
import org.apache.log4j.Logger;
import vo.Questionrecord;
import common.DBFactory;
import common.SqlBuilder;
import common.VOKit;
import util.StringUtil;

public class QuestionrecordDao {

	private static Logger logger = Logger.getLogger(QuestionrecordDao.class);
	private String proxool_M="proxool.defaultDB";
	private String proxool_S="proxool.defaultDB_s0";	
	static QuestionrecordDao  instance=new QuestionrecordDao();
	public static  QuestionrecordDao  getInstance(){
		return instance;
	}
	private QuestionrecordDao(){
		
	}
	
	public int insert(Questionrecord vo){
		int result = 0;		
		if( null == vo ){
			return -1;
		}		
		try {
			SqlBuilder sql=new SqlBuilder("insert into questionrecord");			
			sql.appendInsertParams(vo);							
			result = DBFactory.getDBObject(proxool_M).insert(sql);	
		} catch (Exception e) {
			logger.error("",e);			
			result = -1;
		}
		return result;
	}
	/**
	 * 通用查询
	 * @param params
	 * @param pageNo 页码，从1开始
	 * @param pageSize 每页大小
	 * @return	 
	 */
	public List<Questionrecord> find(Map<String, Object> paramsMap,int pageNo,int pageSize){
		return find("*", paramsMap, pageNo, pageSize);
	}
	/**
	 * 通用查询，可指明返回字段
	 * @param colsName 返回字段列表  xxxx,xxxx
	 * @param paramsMap
	 * @param pageNo
	 * @param pageSize
	 * @return	 
	 */
	public List<Questionrecord> find(String colsName,Map<String, Object> paramsMap,int pageNo,int pageSize){
		List<Questionrecord> list = null;
		try{
			if(!StringUtil.isNotNullorEmpty(colsName)){
				colsName="*";
			}
			SqlBuilder sql=new SqlBuilder("",paramsMap);
			sql.append("SELECT ").append(colsName).append(" FROM questionrecord");
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("weixincode", "weixincode=?");
			sql.appendWhereParam("createDate", "createDate=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("autoAnswerId", "autoAnswerId=?");
			sql.appendWhereParam("answer", "answer=?");
			if(paramsMap.get("searchOption")!=null){
				if("48noReply".equals(paramsMap.get("searchOption").toString())){
					sql.appendWhereParam("searchOption", "ISNULL(autoAnswerId) and ISNULL(answer) and TIMESTAMPDIFF(MINUTE, createDate, now()) < 2890");//48小时之内，2890多10分钟以免遗漏
				}
			}
			sql.appendWhereParam("status", "status=?");						
			sql.append(" order by ");
			if(paramsMap.get("orderBy")!=null){
				sql.append(sql.sqlValue(paramsMap.get("orderBy").toString()));
			}else{
				sql.append("id desc");			
			}			
			sql.appendLimit(pageNo, pageSize);
			RowSet rs = DBFactory.getDBObject(proxool_S).query(sql);			
			list=VOKit.rs2BeanList(rs, Questionrecord.class);
		}catch (Exception e) {
			logger.error("", e);
		} 
		return list;
	}
	/**
	 * 查找总数
	 * @param paramsMap
	 * @return	 
	 */
	public Integer findCount(Map<String, Object> paramsMap){
		int count=0;
		try{
			SqlBuilder sql=new SqlBuilder("SELECT count(id) FROM questionrecord",paramsMap);					
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("weixincode", "weixincode=?");
			sql.appendWhereParam("createDate", "createDate=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("autoAnswerId", "autoAnswerId=?");
			sql.appendWhereParam("answer", "answer=?");
			if(paramsMap.get("searchOption")!=null){
				if("48noReply".equals(paramsMap.get("searchOption").toString())){
					sql.appendWhereParam("searchOption", "ISNULL(autoAnswerId) and ISNULL(answer) and TIMESTAMPDIFF(MINUTE, createDate, now()) < 2890");//48小时之内，2890多10分钟以免遗漏
				}
			}
			sql.appendWhereParam("status", "status=?");
			
			count = DBFactory.getDBObject(proxool_S).getCount(sql);
		}catch (Exception e) {
			logger.error("", e);
		} 
		return count;
	}
	/**
	 * 根据id查找对象
	 * @param id 对象id
	 * @return	
	 */
	public Questionrecord findByPK(Object id) {
		if(id==null){
			return null;
		}
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		return find(params, 1, 1).get(0);
	}
	/**
	 * 查询单条记录
	 * @param params
	 * @return
	 * @author mengdz	 
	 */
	public Questionrecord findSingle(Map<String, Object> params) {
		List<Questionrecord> list=find(params, 1, 1);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * update vo
	 * 更新vo，属性值为空的字段不更新,要求vo属性不能为元数据类型，以免存在属性默认值更新问题
	 * @param vo
	 * @return	 
	 */
	public int update(Questionrecord vo) {
		int result = 0;		
		if( vo.getId()==null){
			return -1;
		}		
		try {
			SqlBuilder sql=new SqlBuilder("update questionrecord");				
			sql.appendUpdateParams(vo);							
			result = DBFactory.getDBObject(proxool_M).update(sql);
		}catch (Exception e) {
			logger.error("",e);			
			result = -1;
		}		
		return result;
	}
	/**
	 * 通用update
	 * @param params
	 * @return	 
	 */
	public int update(Map<String, Object> paramsMap) {
		int result = 0;					
		try {
			SqlBuilder sql=new SqlBuilder("update questionrecord",paramsMap);						
			sql.appendSetParam("id_set", "id=?");
			sql.appendSetParam("weixincode_set", "weixincode=?");
			sql.appendSetParam("createDate_set", "createDate=?");
			sql.appendSetParam("question_set", "question=?");
			sql.appendSetParam("autoAnswerId_set", "autoAnswerId=?");
			sql.appendSetParam("answer_set", "answer=?");
			sql.appendSetParam("status_set", "status=?");
						
			
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("weixincode", "weixincode=?");
			sql.appendWhereParam("createDate", "createDate=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("autoAnswerId", "autoAnswerId=?");
			sql.appendWhereParam("answer", "answer=?");
			sql.appendWhereParam("status", "status=?");
							
			result = DBFactory.getDBObject(proxool_M).update(sql);				
		}catch (Exception e) {
			logger.error("更新失败",e);			
			result = -1;
		}		
		return result;
	}
	/**
	 * 单个删除
	 * @param id
	 * @return	 
	 */
	public int delete(int id) {
		int result = 0;		
		if( id <=0){
			return -1;
		}
		try {
			SqlBuilder sql=new SqlBuilder("update questionrecord set status=-1 WHERE id=?");
			//按顺序添加sql参数
			sql.params.add(id);			
			result = DBFactory.getDBObject(proxool_M).update(sql);					
		}catch (Exception e) {
			logger.error("删除失败",e);			
			result = -1;
		}	
		return result;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return	
	 */
	public int delete(String[] ids) {
		int result = 0;
		if (null == ids || ids.length == 0) {
			return -1;
		}
		try {
			SqlBuilder sql = new SqlBuilder("update questionrecord set status=-1 WHERE ");
			sql.append(" id in (");
			for (String id : ids) {
				sql.append(",").append(sql.sqlValue(id));
			}
			sql.append(")");
			result = DBFactory.getDBObject(proxool_M).update(new SqlBuilder(sql.toString().replace("(,", "(")));
		} catch (Exception e) {
			logger.error("删除失败", e);			
			result = -1;
		}
		return result;
	}
}
