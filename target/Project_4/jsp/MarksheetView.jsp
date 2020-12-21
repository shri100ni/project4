<%@page import="in.co.rays.controller.MarksheetCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.bean.MarksheetBean"
		scope="request"></jsp:useBean>
	<form action="<%=ORSView.MARKSHEET_CTL%>" method="post">
		<%
			List l = (List) request.getAttribute("studentList");
		%>

		<center>
		<%
 	if (bean.getId() > 0) {
 %><h1>Update Marksheet</h1>
 <%} 
 else{%>
			<h1>Add Marksheet</h1>
			<%} %>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			<input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">


			<table>
				<tr>
					<th align="left">Rollno<font color="red">*</font></th>
					<td><input type="text" name="rollNo" placeholder="Enter Rollno"
						value="<%=DataUtility.getStringData(bean.getRollNo())%>">
						</td>
						<td style="position : fixed;"> <font
						color="red"> <%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Name<font color="red">*</font></th> <th><%=HTMLUtility.getList("name", String.valueOf(bean.getStudentId()), l) %></th>
		<td style="position : fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>								
									</tr>
				<tr>
					<th align="left">Physics<font color="red">*</font></th>
					<td><input type="text" name="physics" placeholder="Enter Physics" maxlength="3"
						value="<%=DataUtility.getStringData(bean.getPhysics()).equals("0")?"":DataUtility.getStringData(bean.getPhysics())%>"></td>
						<td style="position : fixed;">
						<font color="red"> <%=ServletUtility.getErrorMessage("physics", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Chemistry<font color="red">*</font></th>
					<td><input type="text" name="chemistry" placeholder="Enter Chemistry" maxlength="3"
						value="<%=DataUtility.getStringData(bean.getChemistry()).equals("0")?"":DataUtility.getStringData(bean.getChemistry())%>"></td>
						<td style="position : fixed;">
						<font color="red"> <%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Maths<font color="red">*</font></th>
					<td><input type="text" name="maths" placeholder="Enter Maths" maxlength="3"
						value="<%=DataUtility.getStringData(bean.getMaths()).equals("0")?"":DataUtility.getStringData(bean.getMaths())%>">
						</td>
						<td style="position : fixed;">
						<font color="red"> <%=ServletUtility.getErrorMessage("maths", request)%></font></td>

				</tr>
				<tr>
					<th>
					 <%
 	if (bean.getId() > 0) {
 %>
				 <td colspan="2" style="padding-left: 30px"><input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_UPDATE%>">&nbsp;<input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_CANCEL%>"></th></td> <%
 	}
 	else{ %> 
<td colspan="2" style="padding-left: 30px"><input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_SAVE%>">&nbsp;<input type="submit" name="operation"
						value="<%=MarksheetCtl.OP_RESET%>"></td><%} %>
				</tr>
			</table>
	</center>
	</form>
	
	<%@ include file="Footer.jsp"%>
</body>
</html>