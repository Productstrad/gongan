/**
 * 
 */
package action.front;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import util.ParamUtil;
import vo.Surveyrecord;
import dao.SurveyDao;

/**
 * @author mendz
 *	2014年11月5日
 */
@Controller
@RequestMapping("/survey")
public class SurveyFrontActon {

	private Logger log = LoggerFactory.getLogger(SurveyFrontActon.class);
	
	@RequestMapping(value = "/read.do")
	public String read(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		
		model.addAttribute("survey", SurveyDao.getInstance().findByPK(ParamUtil.getIntegerParameter(request, "id")));
		return "front/survey/read";
	}
	
	@RequestMapping(value = "/postsurvey.do")
	public String postsurvey(HttpServletRequest request,HttpServletResponse response			
			,Model model) {	
		Integer surveyId=ParamUtil.getIntegerParameter(request, "surveyId");
		Surveyrecord r=new Surveyrecord();		
		r.setSurveyId(surveyId);
		r.setQuestionId(1);
		r.setOptionId(ParamUtil.getIntegerParameter(request, "testOption"));
		r.setWeixincode(weixincode);
		r.setCreateDate(new Date());
	}
}
