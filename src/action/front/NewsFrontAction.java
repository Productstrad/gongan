/**
 * 
 */
package action.front;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import service.NewsService;
import util.HttpRequstUtil;
import util.ParamUtil;
import dao.NewsDao;

/**
 * @author mendz
 *	2014年11月5日
 */
@Controller
@RequestMapping("/news")
public class NewsFrontAction {

	private Logger log = LoggerFactory.getLogger(NewsFrontAction.class);
	
	@RequestMapping(value = "/read.do")
	public String read(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		int id=ParamUtil.getIntegerParameter(request, "id");
		//更新点击数
		NewsService.getInstance().addClicks(id);
		
		model.addAttribute("news", NewsDao.getInstance().findByPK(id));
		
		return "front/news/read";
	}
	
	@RequestMapping(value = "/sendnews.do")
	public String sendnews(HttpServletRequest request,HttpServletResponse response			
			,Model model) {
		
		HttpRequstUtil.defaultPost(eLink, eArgs, enCode);
	}
}
