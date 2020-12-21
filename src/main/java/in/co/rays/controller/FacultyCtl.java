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
import in.co.rays.bean.FacultyBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.CourseModel;
import in.co.rays.model.FacultyModel;
import in.co.rays.model.SubjectModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;


/**
 * The Class FacultyCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(FacultyCtl.class);
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload (HttpServletRequest request){
		
		
		CourseModel coursemodel = new CourseModel();
		CollegeModel collegemodel = new CollegeModel();
		SubjectModel subjectmodel = new SubjectModel();
		try {
			List courselist = coursemodel.list();
			List collegelist = collegemodel.list();
			List subjectlist = subjectmodel.list();
			
				request.setAttribute("CourseList", courselist);
				request.setAttribute("CollegeList", collegelist);
				request.setAttribute("SubjectList", subjectlist);
		
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);

		}
	}
	
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request){
		
		System.out.println("inside validate");
		log.debug("Validate Method of Faculty Ctl Started");
		boolean pass = true;
		if(DataValidator.isNull(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "FirstName"));
			 pass = false;
		}
		/*else if (!DataValidator.isName(request.getParameter("firstName"))){
			request.setAttribute("firstName", PropertyReader.getValue("error.name","First Name"));
			pass=false;
			System.out.println(request.getParameter("firstName"));

		}*/
		/*else if (!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.name", "First Name"));
			 pass = false;
		}*/
		if(DataValidator.isNull(request.getParameter("lastName"))){
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "LastName"));
			pass = false;
		}else if (!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.name", "Last Name"));
			 pass = false;
//			System.out.println("second--------->"+pass);
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		}
		
		if(DataValidator.isNull(request.getParameter("email"))){
			request.setAttribute("email", PropertyReader.getValue("error.require", "LoginId"));
			pass = false;
		}else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Invalid"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "MobileNo"));
			pass = false;
		}else if (!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile No. must be 10 Digit and No. Series start with 6-9");
			pass = false;

		}
		
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "CollegeName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "CourseName"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "SubjectName"));
			pass = false;
		}
		System.out.println("last---->"+pass);

		log.debug("validate Ended");
		return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request){
		log.debug("populate bean faculty ctl started");
		FacultyBean bean = new FacultyBean();
	
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		System.out.println(request.getParameter("firstName"));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmailId(DataUtility.getString(request.getParameter("email")));

		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populateDTO(bean, request);
		log.debug("populate bean faculty ctl Ended");
		return bean;
	}
		
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("Do get of faculty ctl Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		
		//Get Model
		FacultyModel model = new FacultyModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(id>0 || op!= null){
			FacultyBean bean;
			try {
				bean = model.findByPK(id);
			ServletUtility.setBean(bean, request);
			
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
//		System.out.println(" do get out ");
		ServletUtility.forward(getView(), request, response);
		log.debug("Do get of  faculty ctl Ended");


	}

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Do post of  faculty ctl Started");
		System.out.println("inside do post");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));	
			
		// Get Model
		FacultyModel model = new FacultyModel();
		System.out.println("hello beore if add");
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			FacultyBean bean = (FacultyBean) populateBean(request);
			
			try{
			if(id > 0){
				model.update(bean);
		//		ServletUtility.setBean(bean, request);
				
			}else{
				System.out.println("hello add");

			long pk = model.add(bean);
			System.out.println("hello add");
		//		bean.setId(pk);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Faculty Successfully Register", request);
			}catch(ApplicationException e){
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return ; 
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Faculty already Exist", request);
			}
		}else if (OP_DELETE.equalsIgnoreCase(op)) {
			FacultyBean bean =(FacultyBean) populateBean(request);
	
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
	}	else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return ;
			}			
	else if (OP_CANCEL.equalsIgnoreCase(op) ) {
		ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return ;
	}			
		ServletUtility.forward(getView(), request, response);
		log.debug("Do post of  faculty ctl Ended");

	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

}
