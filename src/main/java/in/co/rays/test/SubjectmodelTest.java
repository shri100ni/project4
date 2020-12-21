package in.co.rays.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.SubjectBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.SubjectModel;

public class SubjectmodelTest {

	public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) {
//		testAdd();
//		testUpdate();
//		testDelete();
		testSearch();
//		testList();
	}

	public static void testAdd(){
		SubjectBean bean=new SubjectBean();
		bean.setName("maharani");
		bean.setCourseId(000000000);
		bean.setCourseName("jabtakmanho");
		bean.setDescription("rajkumari");
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
			SubjectBean bean = model.findByPK(58);
		
		bean.setName("maharanisaheba");
		bean.setCourseId(0000);
		bean.setCourseName("jabtakmanhoapka");
		bean.setDescription("rajkumarisaheba");
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
    	SubjectBean bean=new SubjectBean();
    			bean.setId(58);
    	
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
    	SubjectBean bean=new SubjectBean();
    	bean.setId(16);
    	try {
			List list=model.search(bean);
			Iterator it=list.iterator();
			while(it.hasNext()){
			bean=(SubjectBean)it.next();
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
				SubjectBean bean=(SubjectBean)it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getDescription());
			}	
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
