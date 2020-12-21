package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.exception.RecordNotFoundException;
import in.co.rays.model.UserModel;

public class UserModelTest {
	public static UserModel model = new UserModel();

	public static void main(String[] args) throws ParseException, DuplicateRecordException {
		 //testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testFindByLogin();
		// testSearch();
//		testGetRoles();
//		testList();
//		testAuthenticate();
		//testchangePassword();
		testRegisterUser();
	}

	public static void testAdd() throws ParseException, DuplicateRecordException {

		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-DD-yyyy");

			// bean.setId(5234L);
			bean.setFirstName("karishma");
			bean.setLastName("kant");
			bean.setLogin("karish11@gmail.com");
			bean.setPassword("pass1234");
			bean.setDob(sdf.parse("31-12-1990"));
			bean.setRoleId(1);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("femail");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setConfirmPassword("pass1234");
			long pk = model.add(bean);
			UserBean addedbean = model.findByPK(pk);
			System.out.println("Test add succesfuly");
			if (addedbean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {
			UserBean bean = new UserBean();
			long pk = 2;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete succ" + bean.getId());
			UserBean deletedbean = model.findByPK(pk);
			if (deletedbean == null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			UserBean bean = model.findByPK(1);
			bean.setFirstName("monika");
			bean.setLastName("verma");
			bean.setLogin("verma@gmail.com");
			bean.setPassword("vemaji");
			bean.setMobileNo("12345678");
			bean.setConfirmPassword("vermaji");

			model.update(bean);

			UserBean updatedbean = model.findByPK(1L);
			if (!"ranjit".equals(updatedbean.getLogin())) {
				System.out.println("Test Update fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			UserBean bean = new UserBean();
			long pk = 1;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByLogin() {
		try {
			UserBean bean = new UserBean();
			bean = model.findByLogin("shri@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getUnSuccessfulLogin());
			System.out.println(bean.getGender());
			System.out.println(bean.getLastLogin());
			System.out.println(bean.getLock());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("bhagyashri");
			list = model.search(bean);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testGetRoles() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setRoleId(1);
			list = model.getRoles(bean);
			if (list.size() < 0) {
				System.out.println("Test Get Roles fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {

		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getLogin());
				System.out.println(bean.getPassword());
				System.out.println(bean.getDob());
				System.out.println(bean.getRoleId());
				System.out.println(bean.getUnSuccessfulLogin());
				System.out.println(bean.getGender());
				System.out.println(bean.getLastLogin());
				System.out.println(bean.getLock());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testAuthenticate() {

		try {
			UserBean bean = new UserBean();
			bean.setLogin("verma@gmail.com");
			bean.setPassword("vemaji");
			bean = model.authenticate(bean.getLogin(), bean.getPassword());
			if (bean != null) {
				System.out.println("Successfully login");

			} else {
				System.out.println("Invalied login Id & password");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testchangePassword() {

		try {
			UserBean bean = model.findByLogin("@gmail.com");
			String oldPassword = bean.getPassword();
			bean.setId(1l);
			bean.setPassword("mona");
			bean.setConfirmPassword("mona");
			String newPassword = bean.getPassword();
			try {
				model.changePassword(1l, oldPassword, newPassword);
				System.out.println("password has been change successfully");
			} catch (RecordNotFoundException e) {
				e.printStackTrace();
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testforgetPassword() {
		try {
			boolean b = model.forgetPassword("");

			System.out.println("Suucess : Test Forget Password Success");

		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testresetPassword() {
		UserBean bean = new UserBean();
		try {
			bean = model.findByLogin("verma@gmail.com");
			if (bean != null) {
				boolean pass = model.resetPassword(bean);
				if (pass = false) {
					System.out.println("Test Update fail");
				}
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	 public static void testRegisterUser() throws ParseException {
	        try {
	            UserBean bean = new UserBean();
	            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	            // bean.setId(2);
	            bean.setFirstName("vipin");
	            // bean.setLastName("kumawat");
	            bean.setLogin("ankur.er28@gmail.com");
	            bean.setPassword("rr4");
	            bean.setConfirmPassword("rr4");
	            bean.setDob(sdf.parse("11/20/2015"));
	            bean.setGender("Male");
	            bean.setRoleId(1);
	            long pk = model.registerUser(bean);
	            System.out.println(pk+"Successfully register");
	            System.out.println(bean.getFirstName());
	            System.out.println(bean.getLogin());
	            System.out.println(bean.getLastName());
	            System.out.println(bean.getDob());
	            UserBean registerbean = model.findByPK(pk);
	            if (registerbean != null) {
	                System.out.println("Test registation fail");
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        } catch (DuplicateRecordException e) {
	            e.printStackTrace();
	        }
	    }
}
