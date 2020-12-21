package in.co.rays.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.UserBean;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.ServletUtility;;

/**
 * The Class BaseCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public abstract class BaseCtl extends HttpServlet {
     
	/** The Constant OP_SAVE. */
	public static final String OP_SAVE = "Save";
	
	/** The Constant OP_CANCEL. */
	public static final String OP_CANCEL = "Cancel";
	
	/** The Constant OP_RESET. */
	public static final String OP_RESET = "Reset";
	
	/** The Constant OP_UPDATE. */
	public static final String OP_UPDATE = "Update";
	
	/** The Constant OP_DELETE. */
	public static final String OP_DELETE = "Delete";
	
	/** The Constant OP_LIST. */
	public static final String OP_LIST = "List";
	
	/** The Constant OP_SEARCH. */
	public static final String OP_SEARCH = "Search";
	
	/** The Constant OP_VIEW. */
	public static final String OP_VIEW = "View";
	
	/** The Constant OP_NEXT. */
	public static final String OP_NEXT = "Next";
	
	/** The Constant OP_PREVIOUS. */
	public static final String OP_PREVIOUS = "Previous";
	
	/** The Constant OP_NEW. */
	public static final String OP_NEW = "New";
	
	/** The Constant OP_GO. */
	public static final String OP_GO = "Go";
	
	/** The Constant OP_BACK. */
	public static final String OP_BACK = "Back";
	
	/** The Constant OP_LOG_OUT. */
	public static final String OP_LOG_OUT = "Logout";
	
	/** The Constant MSG_SUCCESS. */
	public static final String MSG_SUCCESS = "success";
	
	/** The Constant MSG_ERROR. */
	public static final String MSG_ERROR = "error";

	/**
	 * Validate.
	 *
	 * @param request the request
	 * @return true, if successful
	 */
	protected boolean validate(HttpServletRequest request) {
		return true;
	}

	/**
	 * Preload.
	 *
	 * @param request the request
	 */
	protected void preload(HttpServletRequest request) {
	}

	/**
	 * Populate bean.
	 *
	 * @param request the request
	 * @return the base bean
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		return null;
	}

	/**
	 * Populate DTO.
	 *
	 * @param dto the dto
	 * @param request the request
	 * @return the base bean
	 */
	protected BaseBean populateDTO(BaseBean dto, HttpServletRequest request) {

		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserBean userbean = (UserBean) request.getSession().getAttribute("user");

		if (userbean == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";
		} else {

			modifiedBy = userbean.getLogin();

			// If record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}

		}

		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDatetime"));

		if (cdt > 0) {
			dto.setCreatedDatetime(DataUtility.getTimestamp(cdt));
		} else {
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}

		dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      System.out.println("inside service method of basectl");
		// Load the preloaded data required to display at HTML form
		preload(request);
		String op = DataUtility.getString(request.getParameter("operation"));
//		System.out.println("inside service operation "+op);
		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(op) && !OP_CANCEL.equalsIgnoreCase(op) && !OP_VIEW.equalsIgnoreCase(op)
				&& !OP_DELETE.equalsIgnoreCase(op) && !OP_RESET.equalsIgnoreCase(op) ) {
			// Check validation, If fail then send back to page with error
			// messages
//			System.out.println("inside service operation");
//			System.out.println("oo----"+request.getParameter("operation"));

			
			if (!validate(request)) {
//				System.out.println("inside service valid");
				BaseBean bean = (BaseBean) populateBean(request);
//				System.out.println("after populate service");
				ServletUtility.setBean(bean, request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
		}
		
		 super.service(request, response);
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	protected abstract String getView();

}
