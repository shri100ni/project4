package in.co.rays.util;

import java.util.Date;

/**
 * The Class DataValidator.
 * @author Iterator
 * @version 1.0
 */
public class DataValidator {
	
	/**
	 * Checks if is null.
	 *
	 * @param val the val
	 * @return true, if is null
	 */
	public static boolean isNull(String val) {
		if (val == null || val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks if is not null.
	 *
	 * @param val the val
	 * @return true, if is not null
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Checks if is integer.
	 *
	 * @param val the val
	 * @return true, if is integer
	 */
	public static boolean isInteger(String val) {
		if (isNotNull(val)) {
			try {
				int i = Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if is long.
	 *
	 * @param val the val
	 * @return true, if is long
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				long i = Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if is email.
	 *
	 * @param val the val
	 * @return true, if is email
	 */
	public static boolean isEmail(String val) {
		String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (isNotNull(val)) {
			try {
				return val.matches(emailreg);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}

	}
	 
 	/**
 	 * Checks if is state.
 	 *
 	 * @param val the val
 	 * @return true, if is state
 	 */
 	public static boolean isState(String val){
	    	
		String statereg = "^[^-\\s][\\p{L} .']+$";
		    			if (DataValidator.isNotNull(val)) {
		    				boolean check = val.matches(statereg);
								return check;
		    				}else
		    				{	
		    					return false;
							}	
		    		}

	/**
	 * Checks if is name.
	 *
	 * @param val the val
	 * @return true, if is name
	 */
	public static boolean isName(String val) {
		String fnamereg = "^[a-zA-Z,.'-]+$";
		if (isNotNull(val)) {
			try {
				return val.matches(fnamereg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	/*public static boolean isLname(String val) {

		String lnamereg = "^[a-zA-Z,.'-]+$";
		if (isNotNull(val)) {
			try {
				return val.matches(lnamereg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}*/
	
	/**
	 * Checks if is mobile no.
	 *
	 * @param val the val
	 * @return true, if is mobile no
	 */
	public static boolean isMobileNo(String val) {
		String moreg = "^[6-9]{1}[0-9]{9}$";
		if (isNotNull(val)) {
			try {
				return val.matches(moreg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if is phone no.
	 *
	 * @param val the val
	 * @return true, if is phone no
	 */
	public static boolean isPhoneNo(String val) {
		String phreg = "^[0][0-9]{10}$";
		if (val.matches(phreg)) {
				return true; 
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if is password.
	 *
	 * @param val the val
	 * @return true, if is password
	 */
	public static boolean isPassword(String val) {
		String passreg = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,15})";   /*"^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"*/
		if (isNotNull(val)) {
			try {
				return val.matches(passreg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}

	}

	/**
	 * Checks if is roll no.
	 *
	 * @param val the val
	 * @return true, if is roll no
	 */
	public static boolean isRollNo(String val) {
		String rollreg ="([A-Za-z]{2,})+([0-9]{2,})" /*"([0-9]{2,})+([A-Za-z]{2,})+([0-9]{2,})"*/;
		if (isNotNull(val)) {
			try {
				return val.matches(rollreg);
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Checks if is date.
	 *
	 * @param val the val
	 * @return true, if is date
	 */
	public static boolean isDate(String val) {
		Date d = null;
		if (isNotNull(val)) {
			d = DataUtility.getDate(val);
		}
		return d != null;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Not null 2" + isNotNull("ABC"));
		System.out.println("Not null 3" + isNotNull(null));
		System.out.println("Not Null 4" + isNull("123"));
		System.out.println("is Int" + isInteger(null));
		System.out.println("is Int" + isInteger("ABC1"));
		System.out.println("Is Int" + isInteger("123"));
		System.out.println("Is Int" + isNotNull("123"));

	}
}