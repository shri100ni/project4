package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.PortableServer.Servant;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class UserRegistrationCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })

public class UserRegistrationCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant OP_SIGN_UP. */
	public static final String OP_SIGN_UP = "SignUp";

	/** The log. */
	private static Logger log = Logger.getLogger(UserRegistrationCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserRegistrationCtl Method validate Started");

		boolean pass = true;

		String login = request.getParameter("login");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("firstName"))) {
        	request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name") );
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));

			pass = false;
		}
		else if (!DataValidator.isName(request.getParameter("lastName"))) {
        	request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name") );
			pass = false;
        }
		if (DataValidator.isNull(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		 if (DataValidator.isNull(request.getParameter("mobileNo"))) {
	        	request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
	        	pass = false;
			}  else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
				request.setAttribute("mobileNo", "Mobile No. contain 10 Digits & Series start with 6-9");
				pass = false;
			}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}
		else if (!DataValidator.isPassword(request.getParameter("password"))) {
        	request.setAttribute("password", "Password contain 8 letters with alpha-numeric & special Character");
        	pass = false;
        }
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		}/* else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
			pass = false;
		}*/
		
		  if (!request.getParameter("password").equals(request.getParameter(
		  "confirmPassword")) && !"".equals(request.getParameter("confirmPassword"))) {
		  request.setAttribute("confirmPassword","Password & Confirm Password must be same!!");
		  
		  pass = false;
		  
		  }
		 
		log.debug("UserRegistrationCtl Method validate Ended");

		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserRegistrationCtl Method populatebean Started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRoleId(RoleBean.STUDENT);

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setLogin(DataUtility.getString(request.getParameter("login")));

		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateDTO(bean, request);

		log.debug("UserRegistrationCtl Method populatebean Ended");

		return bean;

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserRegistrationCtl Method doGet Started");
		ServletUtility.forward(getView(), request, response);
//		System.out.println("do get");

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("in do post method");
		log.debug("UserRegistrationCtl Method doPost Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		UserModel model = new UserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				long pk = model.registerUser(bean);
//				bean.setId(pk);
				request.getSession().setAttribute("UserBean", bean);
//				ServletUtility.setBean(bean, request);
				 ServletUtility.setSuccessMessage("Data is successfully saved",request);
				ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				log.error(e);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			}
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
//		System.out.println("do post");
		log.debug("UserRegistrationCtl Method doPost Ended");

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
