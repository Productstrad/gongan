package common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.RowSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import util.ParamUtil;
import util.StringUtil;






public class VOKit {

	public static  <T>  T request2Bean(HttpServletRequest request,Class<T> claz) throws Exception {
		try {
			T bean = claz.newInstance();
			HashMap map = new HashMap();
			Enumeration names = request.getParameterNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				String value = ParamUtil.getStringParameter(request, name);
				if(StringUtil.isNotNullorEmpty(value) && !"null".equals(value) ){//添加对null字段的过滤 huang 2014-5-23
					map.put(name,value);
				}
			}
			//注册日期类型
			SqlDateConverter date = new SqlDateConverter("yyyy-MM-dd");
			ConvertUtils.register(date, java.sql.Date.class); 
			SqlTimestampConverter timestamp = new SqlTimestampConverter("yyyy-MM-dd HH:mm:ss");
			ConvertUtils.register(timestamp, java.util.Date.class);
			BeanUtils.populate(bean, map);
			return bean;
		} catch (InstantiationException e) {
			throw new Exception(e.getMessage());
		} catch (IllegalAccessException e) {
			throw new Exception(e.getMessage());
		} catch (InvocationTargetException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	private static String convertVOName2FieldName(String a){
		char[] c = a.toCharArray();
		String b = "";
		for(int i=0,l=c.length;i<l;i++){
			String ci = String.valueOf(c[i]);
			boolean isNum = false;
			try{
				isNum=Integer.parseInt(ci)>=0;
			}catch(Exception e){
				isNum = false;
			}
			if(!isNum && ci.equals(ci.toLowerCase())){	/*是小写字母*/
				b+=ci;
			}else{
				b+="_"+ci.toLowerCase();
			}
		}
		return b;
	}
	
	/**
	 * 将VO里面的String属性null值转为blank
	 * @param <T>
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public static <T>  T convertStringFieldValueNull2Blank(T vo) throws Exception{
		Field[] fields =   vo.getClass().getDeclaredFields();
		for(Field field : fields){
			String fieldName = field.getName();
			if(field.getType().equals(String.class)){
				String value = BeanUtils.getProperty(vo, fieldName);
				if(StringUtil.isNull(value)){
					BeanUtils.setProperty(vo, fieldName, "");
				}
			}
		}
		return vo;
	}
	
	public static String buildQueryString(Object vo,String charset) throws Exception {
		String queryString = null;
		Field[] fields =   vo.getClass().getDeclaredFields();
		boolean isFirst = true;
		for(Field field : fields){
			String fieldName = field.getName();
			String value = BeanUtils.getProperty(vo, fieldName);
			if(StringUtil.isNotNullorEmpty(value)){
				if(queryString==null){
					queryString="";
				}
				if(!isFirst){
					queryString+="&";
				}else{
					isFirst = false;
				}
				queryString+=fieldName+"="+java.net.URLEncoder.encode(value,charset);;
			}
		}
		return queryString;
	}
	
	public static String buildQueryString(Object vo) throws Exception {
		return buildQueryString(vo,"UTF-8");
	}
	
	public static <T> T rs2Bean(ResultSet rs,Class<T> claz) throws Exception {		
		T bean = claz.newInstance();
		HashMap map = new HashMap();
		Field[] fields =  claz.getDeclaredFields();
		for(Field field:fields){
			String value = null;
			String fieldName = convertVOName2FieldName(field.getName());
			try{
				value =rs.getString(fieldName);
			}catch(SQLException sqlE){

			}
			if(value!=null){
				map.put(field.getName(), value);
			}
		}
		SqlDateConverter date = new SqlDateConverter("yyyy-MM-dd");
		ConvertUtils.register(date, java.sql.Date.class); 
		SqlTimestampConverter timestamp = new SqlTimestampConverter("yyyy-MM-dd 24HH:mm:ss");
		ConvertUtils.register(timestamp, java.util.Date.class);
		BeanUtils.populate(bean, map);
		return bean;
	}
	/**
	 * 将resultSet转化为List
	 * @param rs
	 * @param claz
	 * @return
	 * @throws Exception
	 * @author mengdz
	 * 2014年6月4日
	 */
	public static <T> List<T> rs2BeanList(RowSet rs,Class<T> claz) throws Exception {
		List list=null;		
		if(rs!=null){	
			SqlDateConverter date = new SqlDateConverter("yyyy-MM-dd");
			ConvertUtils.register(date, java.sql.Date.class); 
			SqlTimestampConverter timestamp = new SqlTimestampConverter("yyyy-MM-dd 24HH:mm:ss");
			ConvertUtils.register(timestamp, java.util.Date.class);
			list=new ArrayList<T>();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			HashMap m=new HashMap();
			while(rs.next()){
				m.clear();
				T bean=claz.newInstance();
				Object obj=null;
				for(int i=1;i<columnCount+1;i++){			
					//m.put(VOKit.dealName(rsmd.getColumnName(i)), rs.getObject(i));
					obj=rs.getObject(i);
					if(obj!=null){
						if(obj instanceof Boolean){//bit类型值如果vo属性是非boolean(如int),在此将bit转为int
							if(!claz.getDeclaredField(rsmd.getColumnName(i)).getType().equals(Boolean.class)){
								if((Boolean)obj){
									obj=1;
								}else{
									obj=0;
								}
							}
						}
						m.put(rsmd.getColumnName(i), obj);
					}
				}
				BeanUtils.populate(bean, m);
				list.add(bean);
			}			
		}
		return list;
//		List list=null;
//		if(rs!=null){		
//			list=new ArrayList<T>();
//			Field[] fields =  claz.getDeclaredFields();
//			HashMap map = new HashMap();
//			SqlDateConverter date = new SqlDateConverter("yyyy-MM-dd");
//			ConvertUtils.register(date, java.sql.Date.class); 
//			SqlTimestampConverter timestamp = new SqlTimestampConverter("yyyy-MM-dd 24HH:mm:ss");
//			ConvertUtils.register(timestamp, java.util.Date.class);
//			while(rs.next()){
//				T bean = claz.newInstance();
//				map.clear();
//				for(Field field:fields){
//					Object value = null;					
//					try{						
//						value =rs.getString(field.getName());//	ResultSet.get()方法不区分字段大小写					
//					}catch(Exception e){
//						
//					}
//					if(value!=null){
//						map.put(field.getName(), value);
//					}
//				}
//				BeanUtils.populate(bean, map);
//				list.add(bean);
//			}
//		}
//		return list;
	}
	
	/**
	 * 将List<Map<String,Object>>转为bean list
	 * @param list
	 * @param claz
	 * @return
	 * @throws Exception
	 * @author mengdz
	 * 2014年8月15日
	 */
	public static <T> List<T> MapList2BeanList(List<Map<String,Object>> list,Class<T> claz) throws Exception {
		List beanList=null;
		if(list!=null){
			SqlDateConverter date = new SqlDateConverter("yyyy-MM-dd");
			ConvertUtils.register(date, java.sql.Date.class); 
			SqlTimestampConverter timestamp = new SqlTimestampConverter("yyyy-MM-dd 24HH:mm:ss");
			ConvertUtils.register(timestamp, java.util.Date.class);
			beanList=new ArrayList<T>();			
			for (Map m : list) {
				T bean=claz.newInstance();
				BeanUtils.populate(bean, m);
				beanList.add(bean);
			}
		}
		return beanList;
	}
	
	/**
	 * 将total_count这种形式的转换为totalCount
	 * @param oldName
	 * @return
	 * @author mengdz
	 * 2014年8月13日
	 */
	public static String dealName(String oldName) {
		StringBuilder name=new StringBuilder();
		String[] tempStrings=oldName.split("_");
		for (String s : tempStrings) {
			name.append(StringUtil.upCaseFirst(s));
		}
		return StringUtil.lowCaseFirst(name.toString());
	}	
	/**
	 * 将util.Date转为sql.Date
	 * @param date
	 * @return
	 * @author mengdz
	 * 2014年8月14日
	 */
	public static java.sql.Date Date2SqlDate(Date date) {		
		String dateStr = Constant.dateFormat.format(date);
		return java.sql.Date.valueOf(dateStr);
	}	
}
