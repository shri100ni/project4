package in.co.rays.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.FacultyModel;

public class FacultyModelTest {

	public static FacultyModel model = new FacultyModel();

	public static void main(String[] args) {
		testAdd();
//		testUpdate();
//		testDelete();
//		testSearch();
//		testList();
	}

	public static void testAdd(){
		try {
		FacultyBean bean=new FacultyBean();
		SimpleDateFormat date=new SimpleDateFormat("MM-DD-yyyy");
		bean.setCollegeId(2);
		bean.setCourseId(8);
		bean.setSubjectId(2);

		bean.setFirstName("maharani");
		bean.setLastName("sahu");
		bean.setGender("male");
	    bean.setDob(date.parse("02-31-1992"));
		bean.setEmailId("vahi@email.com");
        bean.setMobNo("1234567609");
		/*bean.setCourseName("jabtakmanho");
		bean.setCollegeName("rajkumari");
		bean.setSubjectName("abc");*/
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
			model.add(bean);
			System.out.println("test add"+bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public static void testUpdate(){
    	
		try {
			FacultyBean bean = model.findByPK(18);
//			SimpleDateFormat date=new SimpleDateFormat("MM-DD-yyyy");
			/*bean.setCollegeId(11);
			bean.setCourseId(111);
			bean.setSubjectId(11);*/
			bean.setFirstName("0000000maharani");
			bean.setLastName("sahu");
			bean.setGender("male");
//		    bean.setDob(date.parse("02-31-1992"));
//			bean.setEmailId("vahi@email.com");
	        bean.setMobNo("1234567609");
			bean.setCourseName("jabtakmanho");
			bean.setCollegeName("rajkumari");
			bean.setSubjectName("abc");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.update(bean);
			System.out.println("test update");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void testDelete(){
    FacultyBean bean=new FacultyBean();
    			bean.setId(18);
    	
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
    	FacultyBean bean=new FacultyBean();
    	bean.setId(15);
    	try {
    		System.out.println("hii");
			List list=model.search(bean);
    		System.out.println("after search hii");

			Iterator it=list.iterator();
			while(it.hasNext()){
				System.out.println();
			bean=(FacultyBean)it.next();
			System.out.println(bean.getFirstName());
			
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
				FacultyBean bean=(FacultyBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCollegeName());
			}	
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
