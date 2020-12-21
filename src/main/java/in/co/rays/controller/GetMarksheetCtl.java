package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.MarksheetBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.MarksheetModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class GetMarksheetCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "GetMarksheetCtl", urlPatterns = {"/ctl/GetMarksheetCtl"})
public class GetMarksheetCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(GetMarksheetCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("GetMarksheetCTL Method validate Started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll Number"));
			pass = false;
		}
		log.debug("GetMarksheetCTL Method validate Ended");

		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("GetMarksheetCtl Method populatebean Started");

		MarksheetBean bean = new MarksheetBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

		bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

		bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

		log.debug("GetMarksheetCtl Method populatebean Ended");

		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("do get started");
		ServletUtility.forward(ORSView.GET_MARKSHEET_VIEW, request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("GetMarksheetCtl Method doGet Started");
		System.out.println("do post start");
		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		MarksheetModel model = new MarksheetModel();

		MarksheetBean bean = (MarksheetBean) populateBean(request);

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_GO.equalsIgnoreCase(op)) {

			try {
				bean = model.findByRollNo(bean.getRollNo());
				if (bean != null) {
					ServletUtility.setBean(bean, request);
				} else {
					ServletUtility.setErrorMessage("RollNo Does Not exists", request);
				}
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
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.GET_MARKSHEET_VIEW;
	}

}
