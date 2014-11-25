package common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.AdminService;
import service.LoginService;

/**
 * Servlet Filter implementation class AttackFilter
 */
public class AttackFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AttackFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		String url=req.getRequestURL().toString();
		
		if(url.indexOf("/sys/")>0){//后台验证是否登录
			if(!LoginService.onlineCheck(req, res)&&url.indexOf("login.jsp")<0&&url.indexOf("login.do")<0){				
				res.sendRedirect("/sys/login.jsp");
				return;
			}
			chain.doFilter(request, response);
		}else{//前台防跨站攻击
			AttackHttpServletRequestWrapper xssRequest = new AttackHttpServletRequestWrapper(req);
			chain.doFilter(xssRequest, response);
		}		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
