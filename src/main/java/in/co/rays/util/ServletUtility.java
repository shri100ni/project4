package in.co.rays.util;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.controller.BaseCtl;
import in.co.rays.controller.ORSView;
import in.co.rays.model.AppRole;
import in.co.rays.model.UserModel;

/**
 * The Class ServletUtility.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class ServletUtility {

	/**
	 * Forward.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public static void forward(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		  RequestDispatcher rd = request.getRequestDispatcher(page);
	        rd.forward(request, response);
		
	}

	/**
	 * Forward view.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public static void forwardView(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.setAttribute("bodyPage", page);
		RequestDispatcher rd = request.getRequestDispatcher(ORSView.LAYOUT_VIEW);
		rd.forward(request, response);
	}

	/**
	 * Redirect.
	 *
	 * @param page the page
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public static void redirect(String page, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.sendRedirect(page);
	}

	/**
	 * Handle exception.
	 *
	 * @param e the e
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	public static void handleException(Exception e, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("exception", e);
		response.sendRedirect(ORSView.ERROR_CTL);
		

	}

	/**
	 * Gets the error message.
	 *
	 * @param property the property
	 * @param request the request
	 * @return the error message
	 */
	public static String getErrorMessage(String property, HttpServletRequest request) {

		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Gets the error message html.
	 *
	 * @param request the request
	 * @return the error message html
	 */
	public static String getErrorMessageHtml(HttpServletRequest request) {

		Enumeration<String> e = request.getAttributeNames();

		StringBuffer sb = new StringBuffer("<UL>");
		String name = null;

		while (e.hasMoreElements()) {
			name = e.nextElement();
			if (name.startsWith("error.")) {
				sb.append("<LI class='error'>" + request.getAttribute(name) + "</LI>");
			}
		}
		sb.append("</UL>");
		return sb.toString();
	}

	/**
	 * Gets the message.
	 *
	 * @param property the property
	 * @param request the request
	 * @return the message
	 */
	public static String getMessage(String property, HttpServletRequest request) {
		String val = (String) request.getAttribute(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets the error message.
	 *
	 * @param msg the msg
	 * @param request the request
	 */
	public static void setErrorMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_ERROR, msg);
	}

	/**
	 * Gets the error message.
	 *
	 * @param request the request
	 * @return the error message
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_ERROR);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets the success message.
	 *
	 * @param msg the msg
	 * @param request the request
	 */
	public static void setSuccessMessage(String msg, HttpServletRequest request) {
		request.setAttribute(BaseCtl.MSG_SUCCESS, msg);
	}

	/**
	 * Gets the success message.
	 *
	 * @param request the request
	 * @return the success message
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String val = (String) request.getAttribute(BaseCtl.MSG_SUCCESS);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets the bean.
	 *
	 * @param bean the bean
	 * @param request the request
	 */
	public static void setBean(BaseBean bean, HttpServletRequest request) {
		request.setAttribute("bean", bean);
	}

	/**
	 * Sets the user model.
	 *
	 * @param model the model
	 * @param request the request
	 */
	public static void setUserModel(UserModel model, HttpServletRequest request) {
		request.getSession().setAttribute("user", model);
	}

	/**
	 * Gets the bean.
	 *
	 * @param request the request
	 * @return the bean
	 */
	public static BaseBean getBean(HttpServletRequest request) {
		return (BaseBean) request.getAttribute("bean");
	}

	/**
	 * Checks if is logged in.
	 *
	 * @param request the request
	 * @return true, if is logged in
	 */
	public static boolean isLoggedIn(HttpServletRequest request) {
		UserModel model = (UserModel) request.getSession().getAttribute("user");
		return model != null;
	}

	/**
	 * Gets the role.
	 *
	 * @param request the request
	 * @return the role
	 */
	public static long getRole(HttpServletRequest request) {
		UserModel model = (UserModel) request.getSession().getAttribute("user");
		if (model != null) {
			return model.getRoleId();
		} else {
			return AppRole.GUEST;
		}
	}

	/**
	 * Gets the parameter.
	 *
	 * @param property the property
	 * @param request the request
	 * @return the parameter
	 */
	public static String getParameter(String property, HttpServletRequest request) {
		String val = (String) request.getParameter(property);
		if (val == null) {
			return "";
		} else {
			return val;
		}
	}

	/**
	 * Sets the list.
	 *
	 * @param list the list
	 * @param request the request
	 */
	public static void setList(List list, HttpServletRequest request) {
		request.setAttribute("list", list);
	}

	/**
	 * Gets the list.
	 *
	 * @param request the request
	 * @return the list
	 */
	public static List getList(HttpServletRequest request) {
		return (List) request.getAttribute("list");
	}

	/**
	 * Sets the page no.
	 *
	 * @param pageNo the page no
	 * @param request the request
	 */
	public static void setPageNo(int pageNo, HttpServletRequest request) {
		request.setAttribute("pageNo", pageNo);
	}

	/**
	 * Gets the page no.
	 *
	 * @param request the request
	 * @return the page no
	 */
	public static int getPageNo(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageNo");
	}

	/**
	 * Sets the page size.
	 *
	 * @param pageSize the page size
	 * @param request the request
	 */
	public static void setPageSize(int pageSize, HttpServletRequest request) {
		request.setAttribute("pageSize", pageSize);
	}

	/**
	 * Gets the page size.
	 *
	 * @param request the request
	 * @return the page size
	 */
	public static int getPageSize(HttpServletRequest request) {
		return (Integer) request.getAttribute("pageSize");
	}

}
