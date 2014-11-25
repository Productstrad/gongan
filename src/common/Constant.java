/**
 * 
 */
package common;

import java.text.SimpleDateFormat;

/**
 * @author mendz
 *	2014年8月14日
 */
public class Constant {
	
	public static SimpleDateFormat dateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public static String EncryptionSalt="`~$*gjd#flG^lfd655>?_+";//加密盐
	
	public static String domain=Config.getNoCache("config/domain/text()", null);
	/**
	 * 线程安全的buffer
	 */
	public static StringBuffer tempBuffer=new StringBuffer();
}
