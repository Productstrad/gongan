/**
 * 
 */
package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mendz
 *	2014年10月22日
 */
public class CookieUtil {

	
	
	public void setCookie(HttpServletResponse response,String cookie_domain,String cookie_name,String cookie_value,int cookie_time){
		Cookie cookie=new Cookie(cookie_name, cookie_value);				
		cookie.setPath("/");
		cookie.setMaxAge(cookie_time);
		cookie.setDomain(cookie_domain);
		response.addCookie(cookie);
	}
	
	//获取Cookie值
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		        String defaultValue = "";
		        Cookie cookies[] = request.getCookies();
		        if(cookies != null)
		        {
		            Cookie acookie[] = cookies;
		            int i = 0;
		            for(int j = acookie.length; i < j; i++)
		            {
		                Cookie cookie = acookie[i];
		                if(cookieName.equals(cookie.getName())){
							defaultValue = cookie.getValue();
							break;
						}
		            }

		        }
		        return defaultValue;
	}
}
