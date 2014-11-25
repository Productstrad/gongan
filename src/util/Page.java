package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;

public class Page {
	
	private final static Log log = LogFactory.getLog(Page.class);
	private final static int pageMaxSize = 50;//每页最多50条记录
	/**
	 * 计算总共多少页
	 * mengdz
	 * @param totalNum 记录总数
	 * @param pageSize 每页大小
	 * @return
	 */
	public static int computePageNum(long totalNum,int pageSize){		
		int totalPages=1;
		if(totalNum/pageSize!=0){
			if(totalNum%pageSize!=0){
				totalPages = (int)totalNum/pageSize + 1;
			} else {
				totalPages = (int)totalNum/pageSize;
			}
		} else if(totalNum%pageSize==0){
			totalPages = (int)totalNum/pageSize;
		}
		if(totalNum==0){
			totalPages = 1;
		}
		return totalPages;
	}
	/**
	 * 根据当前页和每页大小算出起始索引
	 * mengdz
	 * @param req
	 * @param pageSize
	 * @return
	 */
	public static int computeStartIndex(HttpServletRequest req,int pageSize){
		int currentPage=1;
		try{
			//currentPage=Integer.parseInt(req.getParameter("pageNo").toString());
			currentPage=getCurrentPage(req);
		}catch(Exception e){
			
		}
		return (currentPage-1)*pageSize;
	}
	/**
	 * 根据当前页和每页大小算出起始索引
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public static int computeStartIndex(int pageNo,int pageSize){
		if(pageNo<=0){
			pageNo=1;
		}
		return (pageNo-1)*pageSize;
	}
	/**
	 * 根据当前页和每页大小算出起始索引
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
//	public static int computeStartIndex(Integer currentPage,int pageSize){		
//		return (currentPage-1)*pageSize;
//	}
	/**
	 * 取当前页码，默认为1，url参数为pageNo
	 * @param req
	 * @return
	 */
	public static int getCurrentPage(HttpServletRequest req){
		int currentPage=1;
		if(req.getParameter("pageNo")!=null){
			try{
				currentPage=Integer.parseInt(req.getParameter("pageNo").toString());
			}catch(Exception e){
				log.error("Exception",e);
			}
		}
		if(currentPage<=0){
			currentPage=1;
		}
		return currentPage;
	}
	/**
	 * 从url取每页条数参数
	 * @param req
	 * @return
	 */
	public static int getPageSize(HttpServletRequest req){
		int pageSize=10;
		if(req.getParameter("pageSize")!=null){
			try{
				pageSize=Integer.parseInt(req.getParameter("pageSize").toString());
			}catch(Exception e){
				log.error("Exception",e);
			}
		}
		
		if(pageSize>pageMaxSize){
			return pageMaxSize;//限定每页最大查询数
		}
		return pageSize;
	}
	public static int getPageSize(HttpServletRequest req,int defaulValue) {
		int pageSize=defaulValue;
		if(req.getParameter("pageSize")!=null){
			try{
				pageSize=Integer.parseInt(req.getParameter("pageSize").toString());
			}catch(Exception e){
				log.error("Exception",e);
			}
		}
		
		if(pageSize>pageMaxSize){
			return pageMaxSize;//限定每页最大查询数
		}
		return pageSize;
	}
	/**
	 * 取页面get\post参数拼接的参数字符串
	 * @param req
	 * @return
	 */
	public static String getParams(HttpServletRequest req){
		StringBuffer paramsBuffer=new StringBuffer();
		Map<String, String[]>map=req.getParameterMap();	
		String [] tStrings=null;
		for(Map.Entry m:map.entrySet()){
			if(!(m.getKey().equals("pageNo")||m.getKey().equals("pageSize"))){
				tStrings=(String [])m.getValue();
				if(tStrings!=null&&tStrings.length>0){
					paramsBuffer.append("&").append(m.getKey()).append("=").append(tStrings[0]);	
				}						
			}
		}
		if(paramsBuffer.length()>0){
			return paramsBuffer.substring(1, paramsBuffer.length());
		}
		return paramsBuffer.toString();
	}
	
	/**
	 * 设置分页参数
	 * @param totalNum
	 * @param pageSize
	 * @param request
	 * @param model
	 */
	public static void setPageBeans(int totalNum,int pageSize,HttpServletRequest request,Model model) {
		List<PageBean> pageNum = new ArrayList<PageBean>();
		int beginPage = 0,pageCount=computePageNum(totalNum,pageSize),pageNo=getCurrentPage(request);
		if( pageNo % 10 == 0 ){
			beginPage = 1+(pageNo-10);
		}else{
			beginPage = 1+(pageNo-(pageNo%10));
		}		
		int endPage = 0;
		if( pageNo % 10 == 0 ){
			endPage = pageNo;
		}else{
			endPage = pageNo-(pageNo%10)+10;
		}
		if(endPage>pageCount)
			endPage=pageCount;
		for(int i=beginPage;i<=endPage;i++){
			PageBean p = new PageBean();
			p.setPage(i);
			pageNum.add(p);
		}
		model.addAttribute("allcount", totalNum);
		model.addAttribute("pagenum", pageNum);
		model.addAttribute("page", pageNo);
		model.addAttribute("pagecount", pageCount);	
		String paramsString=getParams(request);
		model.addAttribute("pageParams", StringUtil.isNotNullorEmpty(paramsString)?"&"+paramsString:"");	
		model.addAttribute("pageUrl", request.getRequestURL().toString());	
	}
}
