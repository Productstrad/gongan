/**
 * 
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.AutoanswerService;
import util.MessageKit;
import util.ParamUtil;

/**
 * @author mendz
 *	2014年11月21日
 */
@Controller
@RequestMapping("/sys/cache")
public class CacheAction {

	private Logger log = LoggerFactory.getLogger(CacheAction.class);
	/**
	 * 清除某个缓存,使数据马上生效
	 * @param request
	 * @param response
	 * @param model
	 * @author mengdz
	 * 2014年11月21日
	 */
	@RequestMapping(value = "/delcache.do")
	public void delcache(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		String cacheName=ParamUtil.getParameter(request, "cacheName","");
		String result=null;
		if("autoanswer".equals(cacheName)){
			result=AutoanswerService.getInstance().delKeyWordsList();
		}
		if("1".equals(result)){
			MessageKit.displayMessage(response, "清除缓存成功，马上生效");
		}else{
			MessageKit.displayMessage(response, "清除缓存失败，无法马上生效");
		}
	}
}
