package in.co.rays.util;

import java.util.ResourceBundle;

/**
 * The Class PropertyReader.
 * @author Iterator
 * @version 1.0
 * @Copyright (c) SunilOS
 */
public class PropertyReader {

	    /** The rb. */
    	private static ResourceBundle rb = ResourceBundle
	            .getBundle("in.co.rays.bundle.system");
	    
    	/**
    	 * Gets the value.
    	 *
    	 * @param key the key
    	 * @return the value
    	 */
    	public static String getValue(String key) {

	        String val = null;

	        try {
	            val = rb.getString(key);
	        } catch (Exception e) {
	            val = key;
	        }

	        return val;

	    }

	    
	    /**
    	 * Gets the value.
    	 *
    	 * @param key the key
    	 * @param param the param
    	 * @return the value
    	 */
    	public static String getValue(String key, String param) {
	        String msg = getValue(key);
	        msg = msg.replace("{0}", param);
	        return msg;
	    }

	    
	    /**
    	 * Gets the value.
    	 *
    	 * @param key the key
    	 * @param params the params
    	 * @return the value
    	 */
    	public static String getValue(String key, String[] params) {
	        String msg = getValue(key);
	        for (int i = 0; i < params.length; i++) {
	            msg = msg.replace("{" + i + "}", params[i]);
	        }
	        return msg;
	    }

	   

	    /**
    	 * The main method.
    	 *
    	 * @param args the arguments
    	 */
    	public static void main(String[] args) {
	        String[] params = { "Roll No" };
	        System.out.println(PropertyReader.getValue("error.require", params));
	    }

	}




