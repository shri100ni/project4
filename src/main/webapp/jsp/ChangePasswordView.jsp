<%@page import="in.co.rays.controller.ChangePasswordCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
			scope="request"></jsp:useBean>

		<center>
			<h1>Change Password</h1>
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
					<th align="left">Old Password<font color="red">*</font></th>
					<td><input type="password" name="oldPassword"
						placeholder="Enter Old Password"
						value=<%=DataUtility.getString(request.getParameter("oldPassword") == null ? ""
					: DataUtility.getString(request.getParameter("oldPassword")))%>></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">New Password<font color="red">*</font></th>
					<td><input type="password" name="newPassword"
						placeholder="Enter New Password"
						value=<%=DataUtility.getString(request.getParameter("newPassword") == null ? ""
					: DataUtility.getString(request.getParameter("newPassword")))%>>

					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
				</tr>

				<tr>
					<th align="left">Confirm Password<font color="red">*</font></th>
					<td><input type="password" name="confirmPassword"
						placeholder="Confirm Password"
						value=<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? ""
					: DataUtility.getString(request.getParameter("confirmPassword")))%>>
					</td>
						<td style="position : fixed;"><font
						color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
				</tr>

				<tr>
					<th></th>
					<td colspan="2">
						 <input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_SAVE%>"> 
						<input type="submit" name="operation"
						value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>"></td>
				</tr>

			</table>
			
	</form>
	</center>
	<%@ include file="Footer.jsp"%>

</body>
</html>