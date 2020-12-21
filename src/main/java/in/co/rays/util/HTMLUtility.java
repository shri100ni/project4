package in.co.rays.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import in.co.rays.bean.DropdownListBean;

/**
 * The Class HTMLUtility.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class HTMLUtility {
	
	/**
	 * Gets the list.
	 *
	 * @param name the name
	 * @param selectedVal the selected val
	 * @param map the map
	 * @return the list
	 */
	public static String getList(String name, String selectedVal, HashMap<String, String> map) {
		StringBuffer sb = new StringBuffer("<select style='width: 170px; height: 23px;' class='form-control' name='" + name + "'>");
		sb.append("<option  value=''>-------Select------</option>");

		Set<String> keys = map.keySet();
		String val = null;
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * Gets the list.
	 *
	 * @param name the name
	 * @param selectedVal the selected val
	 * @param list the list
	 * @return the list
	 */
	public static String getList(String name, String selectedVal, List list) {

        Collections.sort(list);
        
        
        
        StringBuffer sb = new StringBuffer("<select style='width: 170px;  height: 23px;' class='form-control' name='" + name + "'>");

        boolean select=true;
        if (select)
        {

            sb.append("<option style='width: 210px;  height: 30px;' selected value=''>----------Select----------------------</option>");
        }

        
        List<DropdownListBean> dd = (List<DropdownListBean>) list;

       // StringBuffer sb = new StringBuffer(       "<select style='width: 163px;  height: 23px;' class='form-control' name='" + name + "'>");
        
        String key = null;
        String val = null;

        for (DropdownListBean obj : dd) {
            key = obj.getKey();
            val = obj.getValue();

            if (key.trim().equals(selectedVal)) {
                sb.append("<option selected value='" + key + "'>" + val
                        + "</option>");
            } else {
                sb.append("<option value='" + key + "'>" + val + "</option>");
            }
        }
        sb.append("</select>");
//        System.out.println("get list 2=========" +sb.toString());
        return sb.toString();
    }


	/**
	 * Gets the list.
	 *
	 * @param name the name
	 * @param selectedVal the selected val
	 * @param map the map
	 * @param select the select
	 * @return the list
	 */
	public static String getList(String name, String selectedVal, HashMap<String, String> map, boolean select) {

		StringBuffer sb = new StringBuffer(
				"<select style='width: 170px; height: 23px;' class='form-control' name='" + name + "'>");

				Set<String> keys = map.keySet();
		String val = null;
		if(select){
			sb.append("<option selected value=''>------select------</option>");
		}
		for (String key : keys) {
			val = map.get(key);
			if (key.trim().equals(selectedVal)) {
				sb.append("<option selected value='" + key + "'>" + val + "</option>");
			} else {
				sb.append("<option value='" + key + "'>" + val + "</option>");
			}
		}
		sb.append("</select>");
		return sb.toString();
	}

	/**
	 * Gets the input error messages.
	 *
	 * @param request the request
	 * @return the input error messages
	 */
	public static String getInputErrorMessages(HttpServletRequest request) {

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
	 * Gets the error message.
	 *
	 * @param request the request
	 * @return the error message
	 */
	public static String getErrorMessage(HttpServletRequest request) {
		String msg = ServletUtility.getErrorMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-error-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Gets the success message.
	 *
	 * @param request the request
	 * @return the success message
	 */
	public static String getSuccessMessage(HttpServletRequest request) {
		String msg = ServletUtility.getSuccessMessage(request);
		if (!DataValidator.isNull(msg)) {
			msg = "<p class='st-success-header'>" + msg + "</p>";
		}
		return msg;
	}

	/**
	 * Gets the submit button.
	 *
	 * @param label the label
	 * @param access the access
	 * @param request the request
	 * @return the submit button
	 */
	public static String getSubmitButton(String label, boolean access, HttpServletRequest request) {

		String button = "";

		if (access) {
			button = "<input type='submit' name='operation'    value='" + label + "' >";
		}
		return button;
	}

}