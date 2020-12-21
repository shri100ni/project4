package in.co.rays.controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.StudentBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.StudentModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class StudentListCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(StudentListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {
		
	CollegeModel	model = new CollegeModel();
//	System.out.println("student list preload");
		try {
			List l=model.list();
//			System.out.println("2student list preload");

			Iterator it=l.iterator();
			while(it.hasNext()){
//				System.out.println("3student list preload");

				CollegeBean u=(CollegeBean) it.next();
//				System.out.println("colleg name=>"+u);
			}
			request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			e.printStackTrace();
			log.error(e);
		}
		
	}
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		StudentBean bean = new StudentBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeName")));
		

		return bean;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("StudentListCtl doGet Start");
		List list = null;
List nextList=null;
		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		StudentBean bean = (StudentBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		StudentModel model = new StudentModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			nextList = model.search(bean, pageNo+1, pageSize);
			request.setAttribute("nextlist",nextList.size());
//			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
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
		log.debug("StudentListCtl doGet End");

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 log.debug("StudentListCtl doPost Start");
	        List list = null;
	        List nextList=null;
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        pageNo = (pageNo == 0) ? 1 : pageNo;
	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;

	        StudentBean bean = (StudentBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        String[] ids = request.getParameterValues("ids");

	        StudentModel model = new StudentModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                    || "Previous".equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }
	            }
	            else if (OP_RESET.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
					return;
				}

				 else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
					return;
				} 
	                else if (OP_DELETE.equalsIgnoreCase(op)) {
						pageNo = 1;
						if (ids != null && ids.length > 0) {
							StudentBean deletebean = new StudentBean();
							for (String id : ids) {
								deletebean.setId(DataUtility.getInt(id));
								model.delete(deletebean);
				                ServletUtility.setSuccessMessage("Student deleted Successfully",request);
	
							}
						} else {
							ServletUtility.setErrorMessage("Select at least one record", request);
						
	                }

	            }
	            list = model.search(bean, pageNo, pageSize);
	            nextList = model.search(bean, pageNo+1, pageSize);
	            request.setAttribute("nextlist",nextList.size());
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
	                ServletUtility.setErrorMessage("No record found ", request);
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
	        log.debug("StudentListCtl doGet End");
	    
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}

}
