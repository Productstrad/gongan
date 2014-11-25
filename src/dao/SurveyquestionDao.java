package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.RowSet;
import org.apache.log4j.Logger;
import vo.Surveyquestion;
import common.DBFactory;
import common.SqlBuilder;
import common.VOKit;
import util.StringUtil;

public class SurveyquestionDao {

	private static Logger logger = Logger.getLogger(SurveyquestionDao.class);
	private String proxool_M="proxool.defaultDB";
	private String proxool_S="proxool.defaultDB_s0";	
	static SurveyquestionDao  instance=new SurveyquestionDao();
	public static  SurveyquestionDao  getInstance(){
		return instance;
	}
	private SurveyquestionDao(){
		
	}
	
	public int insert(Surveyquestion vo){
		int result = 0;		
		if( null == vo ){
			return -1;
		}		
		try {
			SqlBuilder sql=new SqlBuilder("insert into surveyquestion");			
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
	public List<Surveyquestion> find(Map<String, Object> paramsMap,int pageNo,int pageSize){
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
	public List<Surveyquestion> find(String colsName,Map<String, Object> paramsMap,int pageNo,int pageSize){
		List<Surveyquestion> list = null;
		try{
			if(!StringUtil.isNotNullorEmpty(colsName)){
				colsName="*";
			}
			SqlBuilder sql=new SqlBuilder("",paramsMap);
			sql.append("SELECT ").append(colsName).append(" FROM surveyquestion");
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("surveyId", "surveyId=?");
			sql.appendWhereParam("questionType", "questionType=?");
			sql.appendWhereParam("questionOrder", "questionOrder=?");
			sql.appendWhereParam("status", "status=?");
			if(paramsMap.get("idNotIn")!=null){
				if(sql.toString().indexOf("where")>=0){
					sql.append(" and ");
				}
				sql.append("id not in (");
				String[] ids=paramsMap.get("idNotIn").toString().split(",");
				for (int i=0;i<ids.length;i++) {
					sql.append(sql.sqlValue(ids[i]));
					if(i<(ids.length-1)){
						sql.append(",");
					}
				}	
				sql.append(")");
			}			
			
			sql.append(" order by questionOrder,id asc");			
			sql.appendLimit(pageNo, pageSize);
			RowSet rs = DBFactory.getDBObject(proxool_S).query(sql);			
			list=VOKit.rs2BeanList(rs, Surveyquestion.class);
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
			SqlBuilder sql=new SqlBuilder("SELECT count(id) FROM surveyquestion",paramsMap);					
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("surveyId", "surveyId=?");
			sql.appendWhereParam("questionType", "questionType=?");
			sql.appendWhereParam("questionOrder", "questionOrder=?");
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
	public Surveyquestion findByPK(Object id) {
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
	public Surveyquestion findSingle(Map<String, Object> params) {
		List<Surveyquestion> list=find(params, 1, 1);
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
	public int update(Surveyquestion vo) {
		int result = 0;		
		if( vo.getId()==null){
			return -1;
		}		
		try {
			SqlBuilder sql=new SqlBuilder("update surveyquestion");				
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
			SqlBuilder sql=new SqlBuilder("update surveyquestion",paramsMap);						
			sql.appendSetParam("id_set", "id=?");
			sql.appendSetParam("question_set", "question=?");
			sql.appendSetParam("surveyId_set", "surveyId=?");
			sql.appendSetParam("questionType_set", "questionType=?");
			sql.appendSetParam("questionOrder_set", "questionOrder=?");
			sql.appendSetParam("status_set", "status=?");
						
			
			sql.appendWhereParam("id", "id=?");
			sql.appendWhereParam("question", "question=?");
			sql.appendWhereParam("surveyId", "surveyId=?");
			sql.appendWhereParam("questionType", "questionType=?");
			sql.appendWhereParam("questionOrder", "questionOrder=?");
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
			SqlBuilder sql=new SqlBuilder("update surveyquestion set status=-1 WHERE id=?");
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
			SqlBuilder sql = new SqlBuilder("update surveyquestion set status=-1 WHERE ");
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
