
package in.co.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CollegeModel;

public class CollegeModelTest {
	public static CollegeModel model = new CollegeModel();

	public static void main(String[] args) throws DuplicateRecordException {
		 testAdd();
		// testFindByName();
		// testFindByPK();
		// testDelete();
		// testUpdate();
		// testSearch();
//		testList();

	}

	public static void testAdd() throws DuplicateRecordException {

		try {
			CollegeBean bean = new CollegeBean();
			bean.setName("pg");
			bean.setAddress("indore_rod");
			bean.setState("mp");
			bean.setCity("dhar");
			bean.setPhoneNo("000000000");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			System.out.println("Test add successfully");
			CollegeBean addedBean = model.findByPK(pk);
			if (addedBean == null) {
				System.out.println("Test add fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {

		try {
			CollegeBean bean = model.findByName("rajiv");
			if (bean == null) {
				System.out.println("Test Find By Name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPK() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 1L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		try {
			CollegeBean bean = new CollegeBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Test Delete successfully");

			CollegeBean deletedBean = model.findByPK(pk);
			if (deletedBean != null) {

				System.out.println("Test Delete fail");

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		try {
			CollegeBean bean = model.findByPK(1L);
			bean.setName("kamini");
			bean.setAddress("sagar");
			model.update(bean);
			System.out.println("Test Update successfully");
			CollegeBean updateBean = model.findByPK(1L);
			if (!"kamini".equals(updateBean.getName())) {
				System.out.println("Test Update fail");

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setName("kamini");
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {

		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
