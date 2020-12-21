package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.management.relation.Role;

import in.co.rays.bean.RoleBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.RoleModel;

public class RoleModelTest {
	public static RoleModel model = new RoleModel();

	public static void main(String[] args) throws Exception {
		// testAdd();
		// testDelete();
		// testUpdate();
		// testFindByPK();
		// testSearch();
		testList();
	}

	public static void testAdd() throws ParseException {
		try {
			RoleBean bean = new RoleBean();
			bean.setName("raj");
			bean.setDescription("kumar");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			RoleBean addedbean = model.findByPK(pk);
			if (addedbean == null) {
				System.out.println("Test add fail");
			}

		} catch (ApplicationException e) {
			e.printStackTrace();

		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {
		try {
			RoleBean bean = new RoleBean();
			long pk = 1;
			bean.setId(pk);
			model.delete(bean);
			RoleBean deletedbean = model.findByPK(pk);
			if (deletedbean != null) {
				System.out.println("Test Delete fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			RoleBean bean = new RoleBean();
			bean.setId(2);
			bean.setName("bhagyshri");
			bean.setDescription("admin");
			bean.setCreatedBy("bhagyshri");
			bean.setModifiedBy("bhagyshri");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			RoleBean updatedbean = model.findByPK(2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		RoleBean bean = new RoleBean();
		long pk = 2;
		try {
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Test Find By PK fail");

			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());

			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("bhagyshri");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("Test list fail");
				System.out.println("Test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
