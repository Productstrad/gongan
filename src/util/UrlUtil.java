package util;

import javax.servlet.http.HttpServletRequest;





public class UrlUtil {
	/**
	 * 取带http头的域名
	 * 
	 * @param request
	 * @return
	 */
	public static String getDomainAddHttp(HttpServletRequest request) {
		return "http://" + request.getServerName();
	}

	/**
	 * 取域名的第一节****.emz.com.cn
	 * 
	 * @param request
	 * @return
	 */
	public static String getDomainFromUrl(HttpServletRequest request) {
		String domain = null;
		String serverName = request.getServerName();
		// if (serverName != null) {
		// if
		// (serverName.endsWith(ReadProperties.readValue("/system.properties",
		// "emzExtCompanyDomain"))) {
		// int end =
		// serverName.lastIndexOf(ReadProperties.readValue("/system.properties",
		// "emzExtCompanyDomain"));
		// if (end != -1) {
		// domain = serverName.substring(0, end);
		//
		// }
		//
		// }
		// }
		String[] temp = serverName.split("\\.");
		domain = temp[0];
		return domain;
	}

	/**
	 * 取完整url，包括参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getUrlAndParams(HttpServletRequest request) {
//		String url = request.getRequestURL().toString() + "?"
//				+ request.getQueryString();
		String url = request.getRequestURL().toString()+ (request.getQueryString()!=null?"?"+request.getQueryString():"");
		return url;
	}
	/**
	 * 取页面名称
	 * @param req
	 * @return
	 */
	public String getPageName(HttpServletRequest req) {
		String url = req.getRequestURL().toString();
		String[] temp = url.split("/");
		return temp[temp.length - 1];
	}
	
	
}
