package util;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import util.JsonUtil;
import util.StringUtil;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;




public class MessageKit {
	
	private static Logger log = Logger.getLogger(MessageKit.class);

	public static void alertMessage(HttpServletResponse response, String msg, boolean isBack) {
		try {
			response.setCharacterEncoding("utf-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert(\"" + msg + "\");");
			if (isBack) {
				out.println("history.back();");
			}
			out.println("</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public static void alertMessage(HttpServletResponse response, String msg, String redirectUrl) {
		try {
			response.setCharacterEncoding("utf-8"); 
			PrintWriter out = response.getWriter();
			out.println("<script >");
			if(msg!=null)out.println("alert(\"" + msg + "\");");
			if (StringUtil.isNotNullorEmpty(redirectUrl)) {
				out.println("location.href=\"" + redirectUrl + "\";");
			}
			out.println("</script>");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	public static void displayMessage(HttpServletResponse response, String msg) {
		try {
			response.setCharacterEncoding("utf-8"); 
			PrintWriter out = response.getWriter();
			out.println(msg);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	/**
	 * 根据接口执行结果输出json提示信息<br/>
	 * 成功：result>0,data<br/>
	 * 失败：result<=0,code,msg<br/>
	 * @param response
	 * @param result 可取值update,insert的effect值 result>0说明执行成功,否则执行失败
	 * @param code 错误类型识别码，执行失败时用
	 * @param data 返回的数据，执行成功时用 
	 * @param msg 错误提示，执行失败时用
	 * @author mengdz
	 * 2014年5月20日
	 */
	public static void displayJsonResult(HttpServletResponse response,int result,Integer code,Map data,String msg) {
		Map<String, Object> reMap=new HashMap<String, Object>();
		if (result > 0) {			
			reMap.put("suc", 1);			
			if(data!=null){
				reMap.put("data", data);							
			}
		}else {
			reMap.put("suc", 0);
			reMap.put("code", code);
			reMap.put("msg", msg);			
		}		
		displayMessage(response, JsonUtil.toJson(reMap));
	}
}
