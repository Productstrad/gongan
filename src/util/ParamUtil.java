/**
 * 
 */
package util;

import java.math.BigDecimal;
//import java.sql.Date;
//import java.sql.Time;
//import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import common.Constant;

/**
 * @author mendz
 *	2014年7月2日
 */
public class ParamUtil {
	
	public static String getParameter(HttpServletRequest request,String name) {
		return getStringParameter(request, name);
	}
	
	public static String getParameter(HttpServletRequest request,String name,String defaultValue) {
		return getStringParameter(request, name,defaultValue);
	}
	
	public static String getStringParameter(HttpServletRequest request,String name) {
		return request.getParameter(name);
	}
	
	public static String getStringParameter(HttpServletRequest request,String name,String defaultValue) {
		String value=request.getParameter(name);
		if(StringUtil.isNotNullorEmpty(value)){
			return value;
		}else {
			return defaultValue;
		}		
	}
	
	public static Integer getIntegerParameter(HttpServletRequest request,String name) {
		Integer value=null;
		try {
			value=Integer.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Integer getIntegerParameter(HttpServletRequest request,String name,Integer defaultValue) {
		Integer valueInteger=defaultValue;
		try {
			valueInteger=Integer.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return valueInteger;
	}
	
	public static Long getLongParameter(HttpServletRequest request,String name) {
		Long value=null;
		try {
			value=Long.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Long getLongParameter(HttpServletRequest request,String name,Long defaultValue) {
		Long value=defaultValue;
		try {
			value=Long.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Float getFloatParameter(HttpServletRequest request,String name) {
		Float value=null;
		try {
			value=Float.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Float getFloatParameter(HttpServletRequest request,String name,Float defaultValue) {
		Float value=defaultValue;
		try {
			value=Float.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Double getDoubleParameter(HttpServletRequest request,String name) {
		Double value=null;
		try {
			value=Double.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Double getDoubleParameter(HttpServletRequest request,String name,Double defaultValue) {
		Double value=defaultValue;
		try {
			value=Double.valueOf(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
//	public static Timestamp getTimestampParameter(HttpServletRequest request,String name) {
//		Timestamp value=null;
//		try {
//			value=Timestamp.valueOf(request.getParameter(name));
//		} catch (Exception e) {
//			
//		}
//		return value;
//	}
//	
//	public static Time getTimeParameter(HttpServletRequest request,String name) {
//		Time value=null;
//		try {
//			value=Time.valueOf(request.getParameter(name));
//		} catch (Exception e) {
//			
//		}
//		return value;
//	}
	
	public static Date getDateParameter(HttpServletRequest request,String name) {
		Date value=null;
		try {
			value=Constant.dateFormat.parse(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Date getDateTimeParameter(HttpServletRequest request,String name) {
		Date value=null;
		try {
			value=Constant.dateTimeFormat.parse(request.getParameter(name));
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static Boolean getBooleanParameter(HttpServletRequest request,String name) {
		Boolean value=null;
		try {
			if(StringUtil.isNotNullorEmpty(request.getParameter(name))){
				if("1".equals(request.getParameter(name))||"true".equalsIgnoreCase(request.getParameter(name))){
					value=true;
				}else if ("0".equals(request.getParameter(name))||"false".equalsIgnoreCase(request.getParameter(name))) {
					value=false;
				}
			}
		} catch (Exception e) {
			
		}
		return value;
	}
	
	public static BigDecimal getBigDecimalParameter(HttpServletRequest request,String name) {
		BigDecimal value=null;
		try {
			value=BigDecimal.valueOf(Long.valueOf(request.getParameter(name)));
		} catch (Exception e) {
			
		}
		return value;
	}
}
