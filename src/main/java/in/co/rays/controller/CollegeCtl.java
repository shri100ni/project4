package in.co.rays.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.CollegeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CollegeModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

/**
 * The Class CollegeCtl.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="CollegeCtl",urlPatterns={"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(CollegeCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("CollegeCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("name"))) {
    		request.setAttribute("name", PropertyReader.getValue("error.name", " College Name"));
    		pass = false;
    	}

        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }
        
        
        if (DataValidator.isNull(request.getParameter("state"))) {
            request.setAttribute("state",
                    PropertyReader.getValue("error.require", "State"));
            pass = false;
        }
        else if (!DataValidator.isState(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.name", "Invalid State"));
			pass = false;
		}
        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city",
                    PropertyReader.getValue("error.require", "City"));
            pass = false;
        }
        else if (!DataValidator.isName(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.name", "Invalid City"));
			pass = false;
		}
        if (DataValidator.isNull(request.getParameter("phoneNo"))) {
            request.setAttribute("phoneNo", PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        }else if (!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo", PropertyReader.getValue("error.phoneno", "Phone No"));
			pass = false;
		}

        log.debug("CollegeCtl Method validate Ended");

        return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		 log.debug("CollegeCtl Method populatebean Started");

	        CollegeBean bean = new CollegeBean();

	        bean.setId(DataUtility.getLong(request.getParameter("id")));

	        bean.setName(DataUtility.getString(request.getParameter("name")));

	        bean.setAddress(DataUtility.getString(request.getParameter("address")));

	        bean.setState(DataUtility.getString(request.getParameter("state")));

	        bean.setCity(DataUtility.getString(request.getParameter("city")));

	        bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));

	        populateDTO(bean, request);

	        log.debug("CollegeCtl Method populatebean Ended");

	        return bean;

	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  String op = DataUtility.getString(request.getParameter("operation"));
//		  System.out.println(">>>>>>>college ctl doget"+request.getParameter("operation"));

	        // get model
	        CollegeModel model = new CollegeModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
//System.out.println(request.getParameter("id"));
	        if (id > 0) {
	            CollegeBean bean;
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
	    }

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("CollegeCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
       
                } else {
                    long pk = model.add(bean);
//                    bean.setId(pk);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage("Data is successfully saved", request);

                }
                
            } catch (ApplicationException e) {
                e.printStackTrace();
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists",request);
            }

        } else if (OP_DELETE.equalsIgnoreCase(op)) {

            CollegeBean bean = (CollegeBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        }else if (OP_RESET.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
            return; }
        else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;

        }

        ServletUtility.forward(getView(), request, response);

        log.debug("CollegeCtl Method doGet Ended");
	}

	/* (non-Javadoc)
	 * @see in.co.rays.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
