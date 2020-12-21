
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
import in.co.rays.bean.TimeTableBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CourseModel;
import in.co.rays.model.SubjectModel;
import in.co.rays.model.TimeTableModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class TimeTableListCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		try {
			List clist = cmodel.list();
			List slist = smodel.list();
			request.setAttribute("courseList", clist);
			request.setAttribute("subjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request)
	{
		System.out.println("inside populate");
		TimeTableBean bean = new TimeTableBean();

		bean.setCourseId(DataUtility.getLong(request.getParameter("clist")));
		bean.setSubjectId(DataUtility.getInt(request.getParameter("slist")));
//		System.out.println("-----------> "+(request.getParameter("exdate")));
		
		bean.setExamDate(DataUtility.getDate(request.getParameter("exdate")));
//		System.out.println("new -----------> "+bean.getExamDate());
		populateDTO(bean, request);
		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list=null;
		List nextList=null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = (TimeTableBean) populateBean(request);

		// String op = DataUtility.getString(request.getParameter("operation"));
		// String[] ids = request.getParameterValues("ids");

		try {
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo+1, pageSize);
			request.setAttribute("nextlist",nextList.size());
			ServletUtility.setBean(bean, request);

			// ServletUtility.setList(list, request);
			if (list == null && list.size() == 0) {
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
		System.out.println("inside do post");
		List list=null;
		List nextList=null;
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TimeTableBean bean = (TimeTableBean) populateBean(request);
		TimeTableModel model = new TimeTableModel();
		String[] ids = (String[]) request.getParameterValues("ids");
		try {
		if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEW.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
				|| OP_PREVIOUS.equalsIgnoreCase(op) || OP_DELETE.equalsIgnoreCase(op)) {

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)&& pageNo > 1) {
			
				pageNo--;
			
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				TimeTableBean deletebean = new TimeTableBean();
				for (String id : ids) {
					/*int id1 = DataUtility.getInt(id2);
					bean3.setId(id1);*/
					deletebean.setId(DataUtility.getInt(id));
						model.delete(deletebean);
		                ServletUtility.setSuccessMessage("TimeTable deleted Successfully",request);
//					ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
				}

			} else {
				ServletUtility.setErrorMessage("Select at least one Record", request);
			}
		}
		}else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} 
//		  System.out.println();
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo+1, pageSize);
			request.setAttribute("nextlist",nextList.size());
//			ServletUtility.setBean(bean, request);
		
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("CollegeListCtl doGet End");

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
