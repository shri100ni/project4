package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.util.ServletUtility;

/**
 * The Class WelcomeCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "WelcomeCtl", urlPatterns = { "/WelcomeCtl" })

public class WelcomeCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	 
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        log.debug("WelcomeCtl Method doGet Started");

        ServletUtility.forward(getView(), request, response);

        log.debug("WelcomeCtl Method doGet Ended");
    	}


	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		
		return ORSView.WELCOME_VIEW;
	}

	  
	 
}
