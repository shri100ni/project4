package in.co.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CourseModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class CourseListCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */

@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	public static Logger log = Logger.getLogger(CourseListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.controller.BaseCtl#preload(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {

		CourseModel model = new CourseModel();
		try {
			List l = model.list();
			request.setAttribute("CourseList", l);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean = new CourseBean();
		bean.setId(DataUtility.getLong(request.getParameter("CourseList")));
//		 populateDTO(bean, request);
		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do get method of CourseCtl Started");
		List list = null;
		List nextList=null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo + 1, pageSize);
			request.setAttribute("nextlist", nextList.size());
			// ServletUtility.setList(list, request);

			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record Found", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("do get method of CourseCtl End");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
List nextList=null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(request.getParameter("pageSize")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
					|| OP_PREVIOUS.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
					return;
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						CourseBean deletebean = new CourseBean();

						for (String id : ids) {

							deletebean.setId(DataUtility.getInt(id));
							model.delete(deletebean);
							ServletUtility.setSuccessMessage("Course deleted Successfully", request);

						}
					} else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}
				}
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			}
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo+1, pageSize);
			request.setAttribute("nextlist",nextList.size());
			if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

		log.debug("CourseListCtl doGet End");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
