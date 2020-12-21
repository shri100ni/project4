package in.co.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.NewsAddress;

import in.co.rays.bean.CourseBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.CourseModel;

public class CourseModelTest {
	
 public static CourseModel model=new CourseModel();
 
	public static void main(String[] args) {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testSearch();
		testList();
		

	}
	public static void testAdd(){
		CourseBean bean=new CourseBean();
		bean.setName("maharani");
		bean.setDescription("rajkumari");
		bean.setDuration("jabtakmanho");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("test add");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void testUpdate(){
    	
		try {
			CourseBean bean = model.findByPK(17);
		
		bean.setName("maharanisaheba");
		bean.setDescription("rajkumarisaheba");
		bean.setDuration("jabtakmanhoapka");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	
			model.update(bean);
			System.out.println("test update");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void testDelete(){
    	CourseBean bean=new CourseBean();
    			bean.setId(17);
    	
    	try {
			model.delete(bean);
			System.out.println("test delete");
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
//    public static void testFindByName(){}
//    public static void testeFindByPK(){}
    public static void testSearch(){
    	CourseBean bean=new CourseBean();
    	bean.setId(16);
    	try {
			List list=model.search(bean);
			Iterator it=list.iterator();
			while(it.hasNext()){
			bean=(CourseBean)it.next();
			System.out.println(bean.getName());
			
				System.out.println(list);
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void testList(){
    	List list=new ArrayList();
    	System.out.println("test list");
    	try {
			list=model.list();
	    	System.out.println("test list");
			Iterator it=list.iterator();
			while(it.hasNext()){
			CourseBean bean=(CourseBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getDuration());
			}	
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
