<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.CollegeCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=ORSView.COLLEGE_CTL%>" method="POST">
		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.bean.CollegeBean"
			scope="request"></jsp:useBean>

		<center>
		<%if(bean.getId()>0){ %>
					<h1>Update College</h1>
		
		<%}else{ %>
			<h1>Add College</h1>
<%} %>
			<H2>
				<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font>
			</H2>
			<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font>
			</H2>

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
					<th align="left">Name<font color="red">*</font></th>
					<td><input type="text" name="name"
						placeholder="Enter College Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Address<font color="red">*</font></th>
					<td><input type="text" name="address"
						placeholder="Enter College Address"
						value="<%=DataUtility.getStringData(bean.getAddress())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("address", request)%></font></td>
				</tr>
				<tr>
					<th align="left">State<font color="red">*</font></th>
					<td><input type="text" name="state"
						placeholder="Enter College State"
						value="<%=DataUtility.getStringData(bean.getState())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("state", request)%></font></td>
				</tr>
				<tr>
					<th align="left">City<font color="red">*</font></th>
					<td><input type="text" name="city"
						placeholder="Enter College City"
						value="<%=DataUtility.getStringData(bean.getCity())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("city", request)%></font></td>
				</tr>
				<tr>
					<th align="left">PhoneNo<font color="red">*</font></th>
					<td><input type="text" name="phoneNo" maxlength="11"
						placeholder="Enter College PhoneNo"
						value="<%=DataUtility.getStringData(bean.getPhoneNo())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("phoneNo", request)%></font></td>
				</tr>


				<tr><th></th>
					<td colspan="2" style="padding-left: 25px">
						 <%
 	if (bean.getId() > 0) {
 %><input type="submit" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">
 <input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>">
  <%
	}else{
 %>
  <input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE%>">
    <input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>">
  <%} %>
			</td>	</tr>
			</table>
	</form>
	</center>
	<%@ include file="Footer.jsp"%>

</body>
</html>