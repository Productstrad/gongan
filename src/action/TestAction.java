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



/**
 * @author mendz
 *	2014年10月21日
 */
@Controller
@RequestMapping("/test")
public class TestAction {

	private Logger log = LoggerFactory.getLogger(AdminAction.class);
	
	@RequestMapping(value = "/testpic.do")
	public String testpic(HttpServletRequest request,HttpServletResponse response			
			,Model model) {			
		return "test/pic";
	 }
}
