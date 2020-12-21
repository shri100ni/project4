package in.co.rays.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.bean.CollegeBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.bean.TimeTableBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.TimeTableModel;

public class TimeTableModelTest {
	public static TimeTableModel model = new TimeTableModel();

	public static void main(String[] args) throws DuplicateRecordException {
     testAdd();
//     testUpdate();
//		testDelete();
//		testSearch();
//		testList();
	}
	public static void testAdd() throws DuplicateRecordException {
SimpleDateFormat sdf=new SimpleDateFormat("MM-DD-yyyy");
		try {
			TimeTableBean bean = new TimeTableBean();
			bean.setCourseName("BSC");
			bean.setSubjectName("Communication Skills");
			bean.setSemester("sdfa");
			bean.setExamDate(sdf.parse("02-11-1991"));
			bean.setExamTime("000000000");
			bean.setCreatedBy("Admin");
			bean.setModifiedBy("Admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			model.add(bean);
			System.out.println("Test add successfully");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void testUpdate() throws DuplicateRecordException {
		
		SimpleDateFormat sdf=new SimpleDateFormat("MM-DD-yyyy");
				try {
					TimeTableBean bean = model.findByPK(18);

					bean.setCourseName("rrrrr");
					bean.setSubjectName("tttt");
					bean.setSemester("iiiii");
					bean.setExamDate(sdf.parse("02-11-1991"));
					bean.setExamTime("000000000");
					bean.setCreatedBy("Admin");
					bean.setModifiedBy("Admin");
					bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
					bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
					 model.update(bean);
					System.out.println("Test updatetd successfully");
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
	 public static void testDelete(){
		 TimeTableBean bean=new TimeTableBean();
		    			bean.setId(20);
		    	
		    	try {
					model.delete(bean);
					System.out.println("test delete");
				} catch (ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
	 public static void testSearch(){
		 TimeTableBean bean=new TimeTableBean();
	    	bean.setId(15);
	    	try {
	    		System.out.println("hii");
				List list=model.search(bean);
	    		System.out.println("after search hii");

				Iterator it=list.iterator();
				while(it.hasNext()){
					System.out.println();
				bean=(TimeTableBean)it.next();
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectName());
				
				}
			} catch (Exception e) {
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
					TimeTableBean bean=(TimeTableBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getSubjectId());
				}	
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

}
