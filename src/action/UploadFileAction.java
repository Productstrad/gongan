/**
 * 
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.MessageKit;
import util.UploadFile;

/**
 * @author mendz
 *	2014年10月20日
 */
@Controller
@RequestMapping("/file")
public class UploadFileAction {

	private Logger log = LoggerFactory.getLogger(UploadFileAction.class);
	
	@RequestMapping(value = "/uploadpicpage.do")
	public String uploadpage(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
				
		return "file/uploadpicpage";
	}	
	
	@RequestMapping(value = "/uploadpic.do")
	public void uploadpic(HttpServletRequest request,HttpServletResponse response			
			,Model model) throws Exception {	
		MessageKit.displayMessage(response, UploadFile.getInstance().uploadPic(request, 0,"waterText"));
	}	
}
