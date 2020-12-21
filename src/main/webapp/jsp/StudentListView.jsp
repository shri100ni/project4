<%@page import="in.co.rays.bean.CollegeBean"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.StudentListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.rays.bean.StudentBean" scope="request"></jsp:useBean>
    <center>
        <h1>Student List</h1>

        <form action="<%=ORSView.STUDENT_LIST_CTL%>" method="post">
        <%
			List l = (List) request.getAttribute("collegeList");
        List l2 = ServletUtility.getList(request);
		%>
		<h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
        <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
                </h2>
                 <%if(l2 == null || l2.size() == 0){ %>
             <br><input type="submit" name="operation" value="<%=StudentListCtl.OP_BACK%>"> 
        <%}else{ %>
            <table width="100%">
                <tr>
                    <td align="center">
                      <label> First Name : </label> <input
                        type="text" name="firstName" placeholder="Enter First  Name"
                        value="<%=ServletUtility.getParameter("firstName", request)%>">
                       
                        <label>Last  Name : </label> <input type="text" name="lastName"placeholder="Enter Last Name"
                        value="<%=ServletUtility.getParameter("lastName", request)%>">
                       
                        <label>Email Id : </label> <input type="text" name="email" placeholder="Enter Email ID"
                        value="<%=ServletUtility.getParameter("email", request)%>">
                       
                         <label>College Name : </label><font><%=HTMLUtility.getList("collegeName", String.valueOf(bean.getCollegeId()), l)%></font>
                         
                         &emsp;
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_SEARCH %>">&emsp;
                        <input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET %>">    </td>
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
                <tr>
                    <th style="padding-left: "><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th>S.No.</th>                    
                    <th>College.</th>
                    <th>First Name.</th>
                    <th>Last Name.</th>
                    <th>Date Of Birth.</th>
                    <th>Mobil No.</th>
                    <th>Email ID.</th>
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<StudentBean> it = list.iterator();

                    while (it.hasNext()) {

                        /* StudentBean */ bean = it.next();
                %>
                <tr align="center">
                    <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                    <td><%=bean.getCollegeName()%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=bean.getDateOfBirth()%></td>
                    <td><%=bean.getMobileNo()%></td>
                    <td><%=bean.getEmail()%></td>
                    <td><a href="StudentCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td><input type="submit" name="operation"
                        value="<%=StudentListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                        <td><input type="submit" name="operation"
                        value="<%=StudentListCtl.OP_NEW%>"></td>
                    <td><input type="submit"
                        name="operation" value="<%=StudentListCtl.OP_DELETE%>"></td>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=StudentListCtl.OP_NEXT%>"<%=(list.size()<pageSize)?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"><input
                type="hidden" name="pageSize" value="<%=pageSize%>">
<br><br><br><%} %>

        </form>
    </center>
        <%@ include file="Footer.jsp"%>
    
</body>
<br>
</html>