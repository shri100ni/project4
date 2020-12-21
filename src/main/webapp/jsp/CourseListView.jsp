<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="java.beans.beancontext.BeanContext"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="in.co.rays.controller.CourseListCtl"%>

<%@page import="in.co.rays.controller.BaseCtl"%>

<%@page import="in.co.rays.bean.CourseBean"%>

<%@page import="in.co.rays.util.ServletUtility"%>

<%@page import="java.util.List"%>

<%@page import="java.util.Iterator"%>



<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<jsp:useBean id="bean" class="in.co.rays.bean.CourseBean"></jsp:useBean>
	<%@include file="Header.jsp"%>

	<%
		List l = (List) request.getAttribute("CourseList");
    int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

		List l1 = (List) ServletUtility.getList(request);
	%>

	<center>
		<h1>Course List</h1>
		<h2>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h2>
		<h2>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h2>


		<%
			if (l1 == null || l1.size() == 0) {
		%>
					<br>
			<input type="submit" name="operation" value="<%=CourseListCtl.OP_BACK%>">
			<%
				} else {
			%>

			<form action="<%=ORSView.COURSE_LIST_CTL%>" method="post">

				<table width="100%">
					<tr>
						<td align="center"><label>Name : </label><font><%=HTMLUtility.getList("CourseList", ServletUtility.getParameter("CourseList", request), l)%></font>
							<%-- <input type="text" name="name"  placeholder="Enter Name"
                        value="<%=ServletUtility.getParameter("name", request)%>"> 
                    --%>
							<font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font>&nbsp;

							<input type="submit" name="operation"
							value="<%=CourseListCtl.OP_SEARCH%>"> <input
							type="submit" name="operation"
							value="<%=CourseListCtl.OP_RESET%>"></td>
					</tr>
				</table>
				<table border="1" width="100%">
					<tr>
						<th><input type="checkbox" id="select_all" name="select">Select 	All</th>
						<th>S.No.</th>
<!-- 						<th>Id</th>
 -->						<th>Name</th>						
						
						
						<th>Duration</th>	
						<th>Descriptiop</th>					
						<th>Edit</th>
					</tr>

					<%
						int pageNo = ServletUtility.getPageNo(request);
							int pageSize = ServletUtility.getPageSize(request);
							int index = ((pageNo - 1) * pageSize) + 1;

							List list = ServletUtility.getList(request);
							Iterator<CourseBean> it = list.iterator();
							while (it.hasNext()) {
								/* CourseBean */ bean = it.next();
					%>
					<tr align="center">
						<td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
						<td><%=index++%></td>
<%-- 						<td align="center"><%=bean.getId()%></td>
 --%>					<td><%=bean.getName()%></td>
						<td><%=bean.getDuration()%></td>
			
						<td><%=bean.getDescription()%></td>
						<td><a href="CourseCtl?id=<%=bean.getId()%>">Edit</a></td>

					</tr>
					<%
						}
					%>
				</table>
				<table width="100%">
					<tr>
						<td><input type="submit" name="operation" value="<%=CourseListCtl.OP_PREVIOUS%>"<%=(pageNo == 1) ? "disabled" : ""%>></td>
						<td><input type="submit" name="operation" value="<%=CourseListCtl.OP_NEW%>"></td>
						<td><input type="submit" name="operation"value="<%=CourseListCtl.OP_DELETE%>"></td>
						<td align="right"><input type="submit" name="operation"value="<%=CourseListCtl.OP_NEXT%>"<%=(list.size() < pageSize)||next==0 ? "disabled" : ""%>></td>
					</tr>
				</table>
				<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
					type="hidden" name="pageSize" value="<%=pageSize%>"> <br>
				<br>
				<br>
				<%
					}
				%>
			</form>
		</center>
		<%@include file="Footer.jsp"%>
</body>
</html>