/**
 * 
 */
package common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author mendz 2014年7月17日
 */
public class SqlBuilder {

	private static Logger log = Logger.getLogger(SqlBuilder.class);

	public StringBuilder sql = new StringBuilder();
	public List<Object> params = new ArrayList<Object>();// 当前需要动态传递的参数,和sql的?参数顺序一致
	public Map<String, Object> allParams = null;// 传给SqlBuilder的所有参数

	public SqlBuilder() {
		
	}
	/**
	 * @param string
	 */
	public SqlBuilder(String s) {
		sql.append(s);
	}

	public SqlBuilder(String s, Map<String, Object> paramsMap) {
		sql.append(s);
		allParams = paramsMap;
	}

	public SqlBuilder append(String s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(StringBuilder s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(StringBuffer s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(Integer s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(Long s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(Double s) {
		sql.append(s);
		return this;
	}

	public SqlBuilder append(Float s) {
		sql.append(s);
		return this;
	}

	public String toString() {
		try {
			setParams();
		} catch (SQLException e) {			
			log.error("toString()时组装参数有错",e);
		}
		return sql.toString();
	}

	public void setParams(PreparedStatement ps) throws SQLException {
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				if (params.get(i) instanceof Integer) {// 已包含元数据类型int的判断
					ps.setInt(i + 1, (Integer) params.get(i));
				} else if (params.get(i) instanceof String) {
					ps.setString(i + 1, (String) params.get(i));
				} else if (params.get(i) instanceof Date) {
					ps.setTimestamp(i + 1,
							new Timestamp(((Date) params.get(i)).getTime()));
				} else if (params.get(i) instanceof Double) {
					ps.setDouble(i + 1, (Double) params.get(i));
				} else if (params.get(i) instanceof Long) {
					ps.setLong(i + 1, (Long) params.get(i));
				} else if (params.get(i) instanceof Float) {
					ps.setFloat(i + 1, (Float) params.get(i));
				}
			}
		}
	}
	
	public String setParams() throws SQLException {			
		int index=sql.indexOf("?"),i=0;
		while (index>=0) {
			sql.replace(index, index+1, sqlValue(params.get(i)));
			index=sql.indexOf("?");
			i++;
		}	
		return sql.toString();
	}
	
	/**
	 * 自动组装insert sql参数
	 * 
	 * @param sqlBuilder
	 * @param params
	 * @param vo
	 * @author mengdz 2014年5月30日
	 */
	public void appendInsertParams(Object vo) {
		sql.append("(");
		StringBuilder subBuilder = new StringBuilder();
		StringBuilder curMname = new StringBuilder();
		Class voClass = vo.getClass();
		Field[] fields = voClass.getDeclaredFields();
		Method curM = null;
		try {
			for (Field f : fields) {
				if (!"id".equalsIgnoreCase(f.getName())) {
					curMname.delete(0, curMname.length()).append("get")
							.append(f.getName().substring(0, 1).toUpperCase())
							.append(f.getName().substring(1));
					curM = voClass.getMethod(curMname.toString(), null);
					if (curM != null && curM.invoke(vo) != null) {// 当前属性值不为空
						if (params != null && params.size() > 0) {
							sql.append(",");
							subBuilder.append(",");
						}
						sql.append(f.getName());
						subBuilder.append("?");
						if(f.getType().equals(Boolean.class)){//boolean对应mysql bit
							if((Boolean)curM.invoke(vo)){
								params.add(1);
							}else {
								params.add(0);
							}
						}else{								
							params.add(curM.invoke(vo));
						}						
					}
				}
			}
			sql.append(") values (").append(subBuilder).append(")");
		} catch (Exception e) {
			log.error("组装insertsql参数出错", e);
		}
	}

	/**
	 * 示例：DBKit.appendParam(sqlBuilder, params, params, "areaID", "areaID=?");
	 * 没有根据params自动拼接所有参数而是每次拼接一个指定参数pName是因为方便最终where参数索引友好
	 * 
	 * @param sqlBuilder
	 * @param params
	 * @param params
	 * @param pName
	 * @param pExpression
	 * @author mengdz 2014年5月19日
	 */
	public void appendWhereParam(String pName, String pExpression) {
		if (allParams != null && allParams.get(pName) != null && !allParams.get(pName).equals("null")) {
			if (sql.toString().indexOf("where") >= 0) {
				sql.append(" and");
			} else {
				sql.append(" where");
			}
			appendParam(pName, pExpression);
		}
	}

	/**
	 * 拼接update sql的set语句
	 * 
	 * @param sqlBuilder
	 * @param params
	 * @param params
	 * @param pName
	 * @param pExpression
	 * @author mengdz 2014年7月7日
	 */
	public void appendSetParam(String pName, String pExpression) {
		if (params != null && allParams.get(pName) != null && !allParams.get(pName).equals("null")) {
			if (sql.toString().indexOf("set") >= 0) {
				sql.append(" ,");
			} else {
				sql.append(" set");
			}
			appendParam(pName, pExpression);
		}
	}
	
	private void appendParam(String pName,String pExpression) {
		if(allParams.get(pName) instanceof Float){//float类型需限定小数点位数才能精确比较
			sql.append(" ").append(pExpression.replace(pName, "format("+pName+",5)").replace("?", "format(?,5)"));
		}else{
			sql.append(" ").append(pExpression);
		}
		if (pExpression.indexOf("?") >= 0) {
			if(allParams.get(pName) instanceof Boolean){
				if((Boolean)allParams.get(pName)){
					params.add(1);//java boolean对应mysql bit类型(1、0)
				}else {
					params.add(0);
				}
			}else{
				params.add(allParams.get(pName));
			}
		}
	}
	/**
	 * 自动组装update sql 的set和where参数
	 * 
	 * @param sqlBuilder
	 * @param params
	 *            用于按顺序存放?参数值
	 * @param vo
	 *            vo属性不能为元数据类型，以免存在属性默认值更新问题
	 * @param whereParam
	 *            where参数名称(保持和VO属性大小写一致)列表 ,不填该参数默认where id=?
	 * @author mengdz 2014年5月20日
	 */
	public void appendUpdateParams(Object vo, String... whereParam) {
		String getIdMethodName = null;// 防止id属性无值时，生成的sql没有where id=?
		sql.append(" set ");
		Class voClass = vo.getClass();
		Field[] fields = voClass.getDeclaredFields();
		Method currentM = null;
		Object curObject = null;
		StringBuilder currentMname = new StringBuilder();
		try {
			// 拼接set参数
			for (Field f : fields) {
				if (!"id".equalsIgnoreCase(f.getName())) {//id参数比不用于set
					if (!isIn(f.getName(), whereParam)) {// 排除用于where参数的属性值,剩下的vo属性值自动设为set参数
						currentMname
								.delete(0, currentMname.length())
								.append("get")
								.append(f.getName().substring(0, 1)
										.toUpperCase())
								.append(f.getName().substring(1));
						currentM = voClass.getMethod(currentMname.toString(),
								null);
						if (currentM.invoke(vo) != null) {
							if (params != null && params.size() > 0) {
								sql.append(",");
							}
							sql.append(f.getName()).append("=?");
							if(f.getType().equals(Boolean.class)){//boolean对应mysql bit
								if((Boolean)currentM.invoke(vo)){
									params.add(1);
								}else {
									params.add(0);
								}
							}else{								
								params.add(currentM.invoke(vo));
							}
						}
					}
				} else {
					getIdMethodName = "get"
							+ f.getName().substring(0, 1).toUpperCase()
							+ f.getName().substring(1);
				}
			}
			// 拼接where参数
			sql.append(" where ");
			if (whereParam == null || whereParam.length == 0) {// 如果没有指定where参数，默认增加where id=?
				sql.append("id=?");
				params.add(voClass.getMethod(getIdMethodName, null).invoke(vo));
			} else {
				for (int i = 0; i < whereParam.length; i++) {
					if (i > 0) {
						sql.append(" and ");
					}
					sql.append(whereParam[i]).append("=?");
					currentMname
							.delete(0, currentMname.length())
							.append("get")
							.append(whereParam[i].substring(0, 1).toUpperCase())
							.append(whereParam[i].substring(1));
					currentM = voClass.getMethod(currentMname.toString(), null);
					curObject = currentM.invoke(vo, null);
					if (curObject != null) {
						if(curObject instanceof Boolean){//boolean对应mysql bit
							if((Boolean)curObject){
								params.add(1);
							}else {
								params.add(0);
							}
						}else{								
							params.add(curObject);
						}						
					} else {
						throw new Exception("sql 参数?无对应值");
					}
				}
			}
		} catch (Exception e) {
			log.error("组装updatesql的set参数出错", e);
		}
	}

	public void appendLimit(int pageNo, int pageSize) {
		if (pageNo > 0 && pageSize > 0) {
			sql.append(" limit ").append((pageNo - 1) * pageSize).append(",")
					.append(pageSize);
		}
	}

	private static boolean isIn(String s, String[] strings) {
		if (strings != null || strings.length > 0) {
			for (String string : strings) {
				if (s.equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(String value) {
		if (null == value) {
			return "''";
		}
		String v = value.trim();
		int vs = v.length();
		StringBuilder sb = new StringBuilder(2 + vs * 2);
		char c = 0;
		sb.append('\'');
		for (int i = 0; i < vs; i++) {
			c = v.charAt(i);
			// 防止sql注入处理，替换'为''，替换\为\\
			if ('\'' == c) {
				sb.append('\'');
				sb.append('\'');
			} else if ('\\' == c) {
				sb.append('\\');
				sb.append('\\');
			} else {
				sb.append(c);
			}
		}
		sb.append('\'');
		return sb.toString();
	}

	private static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 拼接SQL语法的字段字符串值，默认日期格式为：yyyy-MM-dd HH:mm:ss
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(Date value) {
		return "'" + defaultDateFormat.format(value) + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @param simpleDateFormat
	 *            -- 自定义日期格式
	 * @return -- SQL片段字符串
	 */
	public static String sqlValue(Date value, SimpleDateFormat simpleDateFormat) {
		return "'" + simpleDateFormat.format(value) + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 * @return -- SQL片段字符串
	 */
	private static String sqlValue(Timestamp value) {
		return "'" + value + "'";
	}

	/**
	 * 拼接SQL语法的字段字符串值，适用于基本数据类型
	 * 
	 * @param value
	 * @return
	 */
	private static <T> String sqlValuePrimitive(T value) {
		return value.toString();
	}

	/**
	 * 拼接SQL语法的字段字符串值，适用于数组类型
	 * 
	 * @param value
	 * @return
	 */
	private static <T> String sqlValueArray(T[] value) {
		if (null == value) {
			return "''";
		}
		StringBuilder sql = new StringBuilder(64 + value.length * 32);
		for (int i = 0; i < value.length; i++) {
			sql.append(sqlValue(value[i]));
			if (i < value.length - 1) {
				sql.append(",");
			}
		}
		return sql.toString();
	}

	/**
	 * boxed int array
	 * 
	 * @param array
	 * @return
	 */
	private static Integer[] boxedPrimitiveArray(int[] array) {
		Integer[] result = new Integer[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed short array
	 * 
	 * @param array
	 * @return
	 */
	private static Short[] boxedPrimitiveArray(short[] array) {
		Short[] result = new Short[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed long array
	 * 
	 * @param array
	 * @return
	 */
	private static Long[] boxedPrimitiveArray(long[] array) {
		Long[] result = new Long[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed float array
	 * 
	 * @param array
	 * @return
	 */
	private static Float[] boxedPrimitiveArray(float[] array) {
		Float[] result = new Float[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * boxed double array
	 * 
	 * @param array
	 * @return
	 */
	private static Double[] boxedPrimitiveArray(double[] array) {
		Double[] result = new Double[array.length];
		for (int i = 0; i < array.length; i++)
			result[i] = array[i];
		return result;
	}

	/**
	 * 拼接SQL语法的字段字符串值
	 * 
	 * @param value
	 *            -- 数据
	 *            <p>
	 *            注意：如果字段为datetime类型，object
	 *            value不能为null，因为通过jdbc访问mysql时，datetime类型值为''时，会抛出异常
	 * @return -- SQL片段字符串，如果value为null，返回字符串：''
	 */
	public static String sqlValue(Object value) {
		if (null == value) {
			return "''";
		} else if (value instanceof String) {
			return sqlValue((String) value);
		} else if (value instanceof Date) {
			return sqlValue((Date) value);
		} else if (value instanceof Timestamp) {
			return sqlValue((Timestamp) value);
		} else if (value instanceof Integer || value instanceof Long
				|| value instanceof Short || value instanceof Float
				|| value instanceof Double) {
			// 基本数字类型
			return sqlValuePrimitive(value);
		} else if (value instanceof List) {
			return sqlValueArray(((List) value).toArray());
		} else if (value.getClass().isArray()) {
			// 数组类型，（基本数据类型没法进行autoboxing，需要进行额外处理）
			Class ct = value.getClass().getComponentType();
			if (ct == String.class) {
				return sqlValueArray(String[].class.cast(value));
			} else if (ct == int.class) {
				return sqlValueArray(boxedPrimitiveArray((int[]) value));
			} else if (ct == long.class) {
				return sqlValueArray(boxedPrimitiveArray((long[]) value));
			} else if (ct == short.class) {
				return sqlValueArray(boxedPrimitiveArray((short[]) value));
			} else if (ct == float.class) {
				return sqlValueArray(boxedPrimitiveArray((float[]) value));
			} else if (ct == double.class) {
				return sqlValueArray(boxedPrimitiveArray((double[]) value));
			}
			// 默认,转成Object对象数组
			return sqlValueArray((Object[]) value);
		} else {
			return "'" + value.toString() + "'";
		}
	}
}
