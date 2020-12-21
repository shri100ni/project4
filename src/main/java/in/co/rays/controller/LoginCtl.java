package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.RoleModel;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class LoginCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant OP_REGISTER. */
	public static final String OP_REGISTER = "Register";
	
	/** The Constant OP_SIGN_IN. */
	public static final String OP_SIGN_IN = "SignIn";
	
	/** The Constant OP_SIGN_UP. */
	public static final String OP_SIGN_UP = "SignUp";
	
	/** The Constant OP_LOG_OUT. */
	public static final String OP_LOG_OUT = "logout";

	/** The log. */
	private static Logger log = Logger.getLogger(LoginCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("LoginCtl Method validate Started");
		boolean pass = true;
		String op = request.getParameter("operation");
//		System.out.println("validate----"+op);
		if (OP_SIGN_UP.equalsIgnoreCase(op) || "SignUp".equalsIgnoreCase(op)) {
			System.out.println("inside SignUp  " + op);
			return pass;
		}
		if (OP_LOG_OUT.equalsIgnoreCase(op) || "Logout".equalsIgnoreCase(op)) {
			System.out.println("inside logout  " + op);
			return pass;
		}
		
		String login=request .getParameter("login");
		
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login ID"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login ID"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		
		log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("LoginCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		log.debug(" Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		if (OP_LOG_OUT.equals(op)) {

			session = request.getSession();

			session.invalidate();
			ServletUtility.setSuccessMessage("log out successfully", request);

		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Method doPost Ended");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
//		System.out.println("do post started");
		log.debug(" Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));
//System.out.println("do post ----"+op);
		// get model
		UserModel model = new UserModel();
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {

			UserBean bean = (UserBean) populateBean(request);

			try {

				bean = model.authenticate(bean.getLogin(), bean.getPassword());

				if (bean != null) {
					session.setAttribute("user", bean);
					long rollId = bean.getRoleId();

					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}

					String str=(String)session.getAttribute("URI");
					
//					System.out.println("uri is------------->"+str);
					
					if(str==null){
					ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
					return;
					}
					else{
						ServletUtility.redirect(str, request, response);
						return;
					}
					
				} else {
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} /*
			 * else if (OP_LOG_OUT.equals(op)) {
			 * 
			 * session = request.getSession();
			 * 
			 * session.invalidate();
			 * 
			 * ServletUtility.redirect(ORSView.LOGIN_CTL, request, response);
			 * 
			 * return;
			 * 
			 * }
			 */
		else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			System.out.println("calling userctl");
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
