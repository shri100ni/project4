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
<%@ include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
			scope="request"></jsp:useBean>
			
	<form action=" <%=ORSView.LOGIN_CTL%>" method="post">
		

		
		<center>
			<h1>Login</h1>
<h2><font color="red"><%=ServletUtility.getErrorMessage("message", request)%></h2>
		</font>	<H2>
				<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
				</font></H2>
				
				<H2><font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
				</font></H2>
			
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
					<th align="left">LoginId<font color="red">*</font></th>
					<td><input type="text" name="login" placeholder="Enter LoginID" value="<%=DataUtility.getStringData(bean.getLogin())%>">
					</td>
			<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Password<font color="red">*</font></th>
					<td><input type="password" name="password" placeholder="Enter Password" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
			<td style="position: fixed;"><font color="red">  <%=ServletUtility.getErrorMessage("password", request)%> </font></td>
				</tr>
				<tr>
					<th></th>
					<td colspan="2" style="padding-left: 20px">
					<input type="submit" name="operation" value=" <%=LoginCtl.OP_SIGN_IN%> "> &nbsp; 
						<input type="submit" name="operation" value="SignUp">
						&nbsp;</td>
				</tr>
				<tr>
					<th></th>
					<td align="center"><a href=" <%=ORSView.FORGET_PASSWORD_CTL%> "><b>Forget
								my password</b></a>&nbsp;</td>
				</tr>
			</table>
	</form>
	 </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>