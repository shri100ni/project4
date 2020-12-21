package in.co.rays.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.TimeTableBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CourseModel;
import in.co.rays.model.SubjectModel;
import in.co.rays.model.TimeTableModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class TimeTableCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {
	
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
			request.setAttribute("CourseList", clist);
			request.setAttribute("SubjectList", slist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("validate method of TimeTable Ctl started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("exDate"))) {
			request.setAttribute("exDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("exTime"))) {
			request.setAttribute("exTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}

		/*
		 * if (DataValidator.isNull(request.getParameter("description"))) {
		 * request.setAttribute("description", "Please Select Description");
		 * pass = false; }
		 */

		log.debug("validate method of TimeTable Ctl End");
		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected TimeTableBean populateBean(HttpServletRequest request) {
		log.debug("populateBean method of TimeTable Ctl start");
		TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
   
		System.out.println("sub name------>"+request.getParameter("subjectId"));
		
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		System.out.println("course name------>"+request.getParameter("courseId"));
		
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("exDate")));
		bean.setExamTime(DataUtility.getString(request.getParameter("exTime")));
		System.out.println(bean.getExamDate());
		populateDTO(bean, request);
		log.debug("populateBean method of TimeTable Ctl End");
		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do Get method of TimeTable Ctl Started");

		// String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = null;
		if (id > 0) {
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
			}
		}

		log.debug("do Get method of TimeTable Ctl End");
		ServletUtility.forward(getView(), request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("do post method of TimeTable Ctl start");

		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimeTableBean bean = (TimeTableBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				} else {					
					model.add(bean);
					
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Saved", request);

			} catch (ApplicationException e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Time Table already Exists", request);
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
