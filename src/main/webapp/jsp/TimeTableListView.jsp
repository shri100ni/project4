<%@page import="in.co.rays.model.TimeTableModel"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.TimeTableBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.TimeTableListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script >
function disableSunday(d){
	  var day = d.getDay();
	  if(day==0)
	  {
	   return [false];
	  }else
	  {
		  return [true];
	  }
}
$( function() {
    $( "#datepicker" ).datepicker({ 
    	beforeShowDay:disableSunday,
    	changeMonth :true,
		  changeYear :true,
		  yearRange: '0:+2',
		  
		  
		  dateFormat:'dd-mm-yy',
		  minDate:0
		 /*  endDate: '-9y' */  
		 }) ;
  } );
</script>
</head>
<body>
<jsp:useBean id="bean" class="in.co.rays.bean.TimeTableBean" scope="request"></jsp:useBean>
<%@include file = "Header.jsp" %>


<form action="<%=ORSView.TIMETABLE_LIST_CTL %>" method="post"> 
	
	<center>
		
	<div align="center">
	<h1>TimeTable List</h1>
	<h3>
	
		<font style="font: bold ; color: red"><%=ServletUtility.getErrorMessage(request) %></font>	
		<font style="font: bold ; color: green"><%=ServletUtility.getSuccessMessage(request) %></font>	
	</h3>
	</div>
	        <%  List l2= (List)ServletUtility.getList(request); %>
	
	<%
		List cList = (List) request.getAttribute("courseList");
		List sList = (List) request.getAttribute("subjectList"); 
        int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

	%>
	<%
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);	
	int index = ((pageNo-1)*pageSize)+1;

	List list = ServletUtility.getList(request);
	Iterator<TimeTableBean> it = list.iterator();
	 if(l2 == null || l2.size() == 0){ %>
     <br><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_BACK%>"> 
<%}else{ %>
	
	<table width ="100%">
		<tr>
		<td align="center">
		<label>Course Name : </label>
		<%=HTMLUtility.getList("clist", String.valueOf(bean.getCourseId()), cList) %>
		
		<label>Subject Name : </label>
		<%=HTMLUtility.getList("slist", String.valueOf(bean.getSubjectId()), sList) %>
				
		<label>Date Of Exam : </label>
		<input type ="text"  name="exdate" readonly="readonly" id ="datepicker" placeholder="Select Date" value="<%=DataUtility.getDateString(bean.getExamDate()) %>">		
		&nbsp;
		<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_SEARCH%>">
		&nbsp;
		<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_RESET %>">
		</td>	
		</tr>
	</table>
	
	<table border="1" width="100%" align="center" >
		<tr>
		
			<th ><input type="checkbox" id="select_all" name ="Select">Select All.</th>
			<th>S.No.</th>	
			<th>Course Name.</th>
			<th>Subject Name.</th>
			<th>SEMESTER.</th>
			<th>ExamDate.</th>
			<th>ExamTime.</th>
			<th>Edit</th>
			
		</tr>
	<%
	while(it.hasNext()){
	bean =it.next();	
	%>
	<tr align="center">
		<td><input type = "checkbox" class="checkbox" name="ids" value="<%=bean.getId() %>"></td>
		<td><%=index++ %></td>
		<td><%=bean.getCourseName() %></td>
		<td><%=bean.getSubjectName() %></td>
		<td><%=bean.getSemester() %></td>
		<td><%=bean.getExamDate() %></td>
		<td><%=bean.getExamTime() %></td>
		<td><a href ="TimeTableCtl?id=<%=bean.getId()%>">Edit</a></td>
	</tr>
		<% 
		}
		%>
	</table>
<table width="100%">
                <tr>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_NEW%>"></td>
                    <td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_DELETE%>"></td>
                    <td align="right"><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                </tr>
            </table>
		
	<%} %>
		<input type="hidden" name="operation" value="<%=pageNo %>">
		<input type="hidden" name="operation" value="<%=pageSize%>">
</form><br><br>
<br></center>
</div>
<%@include file = "Footer.jsp" %>

</body>
</html>