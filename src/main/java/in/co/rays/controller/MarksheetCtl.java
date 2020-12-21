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
import in.co.rays.bean.MarksheetBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.MarksheetModel;
import in.co.rays.model.StudentModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class MarksheetCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })

public class MarksheetCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		StudentModel model = new StudentModel();
		try {
			List l = model.list();
			request.setAttribute("studentList", l);
//			System.out.println(l);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("MarksheetCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Invalid Roll No"/*"Roll No. Should be like IT102,CS103..."*/);
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry marks"));
			pass = false;
		} 
		else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& DataUtility.getInt(request.getParameter("chemistry")) > 100) {
			request.setAttribute("chemistry", "Marks can not be greater than 100");
			pass = false;
		} 
		else if (DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "Marks can Not less then 0 ");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics marks"));
			pass = false;
		}

		else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& DataUtility.getInt(request.getParameter("physics")) > 100) {
			request.setAttribute("physics", "Marks can not be greater than 100");
			pass = false;
		}
		else if (DataUtility.getInt(request.getParameter("physics")) < 0) {
			request.setAttribute("physics", "Marks can Not less then 0 ");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths marks"));
			pass = false;
		}

		else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		}

		else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& DataUtility.getInt(request.getParameter("maths")) > 100) {
			request.setAttribute("maths", "Marks can not be greater than 100");
			pass = false;
		} else if (DataUtility.getInt(request.getParameter("maths")) < 0) {
			request.setAttribute("maths", "Marks can Not less then 0 ");
			pass = false;
		}

		/*
		 * if (DataValidator.isNull(request.getParameter("studentId"))) {
		 * request.setAttribute("studentId",
		 * PropertyReader.getValue("error.require", "Student Name")); pass =
		 * false; }
		 */
		log.debug("MarksheetCtl Method validate Ended");

		return pass;

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("MarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		// bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		bean.setStudentId(DataUtility.getLong(request.getParameter("name")));

		populateDTO(bean, request);

		System.out.println("Population done");

		log.debug("MarksheetCtl Method populatebean Ended");

		return bean;

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		MarksheetModel model = new MarksheetModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			MarksheetBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("MarksheetCtl Method doGet Ended");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("MarksheetCtl Method doPost Started");
		System.out.println("insode do post");

		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		MarksheetModel model = new MarksheetModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		// System.out.println(request.getParameter("id")+"ddddd");

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			try {
				// System.out.println("after update
				// method"+"oo----"+request.getParameter("operation"));

				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);

				} else {
					long pk = model.add(bean);
					/// bean.setId(pk);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);

				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			MarksheetBean bean = (MarksheetBean) populateBean(request);
			// System.out.println("in try");
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				// System.out.println("in try");
				return;
			} catch (ApplicationException e) {
				// System.out.println("in catch");
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("MarksheetCtl Method doPost Ended");

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}
