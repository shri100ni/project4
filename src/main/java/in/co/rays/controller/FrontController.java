package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.util.ServletUtility;

/**
 * The Class FrontController.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebFilter(filterName="ctl",urlPatterns={"/ctl/*","/Doc/*"})
public class FrontController implements Filter {

	/*public FrontController() {
		// TODO Auto-generated constructor stub
	}*/

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			request.setAttribute("message", " Your Session has been Expired... Please Login Again");

			// Set the URI
			String str = request.getRequestURI();
			session.setAttribute("URI", str);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
		    
		} else {
			chain.doFilter(req, resp);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
