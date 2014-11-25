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


import service.LoginService;
import util.MessageKit;

/**
 * @author mendz
 *	2014年10月22日
 */
@Controller
@RequestMapping("/sys")
public class LoginAction {

	private Logger log = LoggerFactory.getLogger(LoginAction.class);
	
	@RequestMapping(value = "/login.do")
	public void login(HttpServletRequest request,HttpServletResponse response			
			,Model model) {
		MessageKit.displayMessage(response, new LoginService().login(request, response));
	}
	
	@RequestMapping(value = "/logout.do")
	public void logout(HttpServletRequest request,HttpServletResponse response			
			,Model model) {
		MessageKit.displayMessage(response, new LoginService().logout(request, response));
	}	
}
