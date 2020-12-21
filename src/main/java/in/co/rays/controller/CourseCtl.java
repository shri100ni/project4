package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CourseBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CourseModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class CourseCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name ="CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

/** The log. */
private static Logger log = Logger.getLogger(CourseCtl.class);
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		log.debug("CourseCtl validate started");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "Course Name"));
			 pass = false ;
		}else if (!DataValidator.isState(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.name", "Course Name"));
			 pass = false ;
		}
		if (DataValidator.isNull(request.getParameter("duration"))) {
			request.setAttribute("duration", PropertyReader.getValue("error.require", "Duration"));
			pass = false ;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false ;
		}

		log.debug("CourseCtl validate End");
		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("CourseCtl PopulatedBean started");
		CourseBean bean = new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));

		bean.setDuration(DataUtility.getString(request.getParameter("duration")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
	      System.out.println(bean.getDescription());
		populateDTO(bean, request);
		log.debug("CourseCtl PopulatedBean End");
		return bean;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		ServletUtility.forward(getView(), request, response);
		log.debug("Do get method od courseCtl started");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		// get Model
		CourseModel model = new CourseModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		CourseBean bean=null;
		
//		System.out.println("in do get of courseCtl");
		
		/*if(id>0){
			
			try{
				System.out.println("inside id "+id);
			bean = model.findByPK(id);
			ServletUtility.setBean(bean, request);
			
			}catch(ApplicationException e){
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				
			}
		}
		ServletUtility.forward(getView(), request, response);
        return;*/
		
		try
		{
//			System.out.println("in try of courseCtl");
			if(id>0)
			{
				bean=model.findByPK(id);
//				System.out.println(bean.getName());
				ServletUtility.setBean(bean, request);
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			log.error(e);
			ServletUtility.handleException(e, request, response);
		}
		
		ServletUtility.forward(getView(), request, response);

	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do Post method of CourseCtl started ");
		String op = DataUtility.getString(request.getParameter("operation"));

		// Get Model
		CourseModel model = new CourseModel();
		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					// bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Course is Successfully saved", request);

			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course Name Already Exist", request);

			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			CourseBean bean = (CourseBean) populateBean(request);
			try {
				model.delete(bean);
				;
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("Do Post method CourseCtl Ended");

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.COURSE_VIEW;
	}

}
