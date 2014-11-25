/**
 * 
 */
package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.AuthImg;

/**
 * @author mendz
 *	2014年10月27日
 */
@Controller
@RequestMapping("/authimg")
public class AuthImgAction {

	private Logger log = LoggerFactory.getLogger(AuthImgAction.class);
	/**
	 * 取验证码
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 * @author mengdz
	 * 2014年10月27日
	 */
	@RequestMapping(value = "/getauthimg.do")
	public void getauthimg(HttpServletRequest request,HttpServletResponse response			
			,Model model) throws Exception {	
		new AuthImg().getAuthImg(request, response);
	}
}
