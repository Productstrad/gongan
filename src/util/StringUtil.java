package util;


/**
 * @author mendz
 *	2014年7月2日
 */
public class StringUtil {
	/**
	 * 删除字符串后面的字符，如传真，电话后面多余的"-"
	 * @param str
	 * @param rStr
	 * @return
	 */
	public static String removeLastCharacter(String str,String rStr){
		if(rStr==null){
			return str;
		}
		while(str!=null && !"".equals(str) && (str.lastIndexOf(rStr)+1)==str.length()){
			str = str.substring(0, str.lastIndexOf(rStr));
		}
		return str;
	}
	/**
	 * 将字符串首字母变大写
	 * @param str
	 * @return
	 * @author mengdz
	 * 2014年7月21日
	 */
	public static String upCaseFirst(String str){
		if(isNotNullorEmpty(str)){
			return str.substring(0, 1).toUpperCase()+str.substring(1, str.length());
		}
		return str;
	}
	/**
	 * 将字符串首字母变小写
	 * @param str
	 * @return
	 * @author mengdz
	 * 2014年7月21日
	 */
	public static String lowCaseFirst(String str){
		if(isNotNullorEmpty(str)){
			return str.substring(0, 1).toLowerCase()+str.substring(1, str.length());
		}
		return str;
	}
	
	/**
	 * 判断字符串是否为Null或empty,为Null或empty返回false
	 * @param str
	 * @return
	 */
	public static boolean isNotNullorEmpty(String str){
		boolean flag=false;
		if(str!=null && !str.trim().equals("")){
			flag=true;
		}
		return flag;
	}
	
	public static boolean isNull(Object value) {
		if(value==null){
			return true;
		}
		return false;
	}
	
	/**
	 * 去掉图片的后缀
	 * @param picName
	 */
	public static String removePicSuffix(String picName){
		if(picName==null){
			return null;
		}
		if(picName.indexOf( ".jpg")>=0 || picName.indexOf( ".gif")>=0 
				|| picName.indexOf( ".png")>=0 || picName.indexOf( ".bmp")>=0){
            return picName.substring(0,picName.lastIndexOf("."));
        }
		return picName;
	}
	
	/**
	 * 将空值返回为字符串对象
	 * @param obj 
	 * @return Object
	 */
	public static Object nullToString(Object obj){
		if(obj==null){
			return "";
		}
		return obj;
	}
}
