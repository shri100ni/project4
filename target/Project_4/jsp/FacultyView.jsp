<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.controller.FacultyCtl"%>

<%@page import="java.util.List"%>

<%@page import="in.co.rays.util.HTMLUtility"%>

<%@page import="java.util.HashMap"%>

<%@page import="in.co.rays.util.DataUtility"%>

<%@page import="in.co.rays.util.ServletUtility"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script >
$( function() {
    $( "#datepicker" ).datepicker({ 
    	changeMonth :true,
		  changeYear :true,
 		  yearRange: '1950:2020',
 	  
		  
		  dateFormat:'dd-mm-yy',
		 /*  endDate: '-9y' */  
/*  			  minDate:0
 */ 	 }) ;
  } );
</script>
</head>
<body>

	<%@ include file="Header.jsp"%>

	<form action="<%=ORSView.FACULTY_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.bean.FacultyBean"
			scope="request"></jsp:useBean>

		<%
			List l1 = (List) request.getAttribute("CollegeList");

			List l2 = (List) request.getAttribute("CourseList");

			List l3 = (List) request.getAttribute("SubjectList");
		%>

		<center>
			<%
				if (bean.getId() > 0) {
			%>

			<h1>Update Faculty</h1>
			<%
				} else {
			%>

			<h1>Add Faculty</h1>

			<%
				}
			%>
			<h2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</h2>

			<h2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</h2>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">

			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">

			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<td>First Name<font color="red">*</font></td>
					<td><input type="text" name="firstName"	placeholder="Enter First_Name" value="<%=DataUtility.getStringData(bean.getFirstName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
				</tr>

				<tr>
					<td>Last Name<font color="red">*</font></td>
					<td><input type="text" name="lastName"
						placeholder="Enter Last_Name"
						value="<%=DataUtility.getStringData(bean.getLastName())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font>
					</td>
				</tr>

				<tr>
					<td>EmailId<font color="red">*</font></td>
					<td><input type="text" name="email"
						placeholder="Enter EmailId"
						value="<%=DataUtility.getStringData(bean.getEmailId())%>"
						<%=(bean.getId() > 0) ? "readonly" : ""%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
					</td>
				</tr>

				<tr>
					<td>Date Of Joining<font color="red">*</font></td>
					<td><input type="text" id="datepicker" name="dob"
						placeholder="Enter Date" readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
				</tr>
				<tr>
					<td>Mobile No.<font color="red">*</font></td>
					<td><input type="text" name="mobileNo"
						placeholder="Enter Mobile No." maxlength="10"
						value="<%=DataUtility.getStringData(bean.getMobNo())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
				</tr>

				<tr>
					<td>Gender<font color="red">*</font></td>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Male", "Male");
							map.put("Female", "Female");

							String htmlList = HTMLUtility.getList("gender", bean.getGender(), map);
						%> <%=htmlList%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
				</tr>

				<tr>
				<tr>
					<td>College Name<font style="color: red">*</font></td>
					<td><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()), l1)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
				</tr>
				<tr>
					<td>Course Name<font style="color: red">*</font></td>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), l2)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>
				<tr>
					<td>Subject Name<font style="color: red">*</font></td>
					<td><%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), l3)%>
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("subjectId", request)%></font></td>
				</tr>

				<tr>
					<td></td>
					<td align="center" colspan="2">
						<%
							if (bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=FacultyCtl.OP_CANCEL%>"> <%
 	} else {
 %> <input type="submit" name="operation"
						value="<%=FacultyCtl.OP_SAVE%>"> <input type="submit"
						name="operation" value="<%=FacultyCtl.OP_RESET%>"> <%
 	}
 %>

					</td>
				</tr>
			</table>
		</center>
	</form>

	<%@ include file="Footer.jsp"%>



</body>
</html>