<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.MarksheetListCtl"%>
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
<jsp:useBean id="bean" class="in.co.rays.bean.MarksheetBean" scope="request"></jsp:useBean>
    <center>
        <h1>Marksheet List</h1>
       <form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="POST">

                <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>  
               <h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
                 
             <%                 List l2 = ServletUtility.getList(request);

             List l=(List)request.getAttribute("rollNo");
             int next=DataUtility.getInt(request.getAttribute("nextlist").toString());
             %>
              <%if(l2  == null || l2 .size() == 0){ %>
             <br><input type="submit" name="operation" value="<%=MarksheetListCtl.OP_BACK%>"> 
        <%}else{ %>
                
            <table width="100%">
                <tr>
                    <td align="center">
                       <label> Name : </label> <input type="text"
                        name="name" placeholder="Enter Name"
                        value="<%=ServletUtility.getParameter("name", request)%>">
                        &emsp; <label>RollNo : </label><font><%=HTMLUtility.getList("rollNo", String.valueOf(bean.getId()), l) %></font>&emsp;
                        <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_SEARCH %>">&emsp;
                        <input type="submit" name="operation" value="<%=MarksheetListCtl.OP_RESET %>"></td>
                
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
                <tr>
                    <th style="padding-left: "><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th>S.No</th>
                    <th>RollNo</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                    <th>Total</th>
                    <th>Percentage%</th>
                    <th>Result</th>
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();

                    while (it.hasNext()) {

                        /* MarksheetBean */ bean = it.next();
                       
                            int phy = bean.getPhysics();
        					int chem = bean.getChemistry();
        					int math = bean.getMaths();
        					int total = phy + chem + math;
        					float percentage = total / 3;
                     %>
                <tr align="center">
                    <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                    <td><%=bean.getRollNo()%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getPhysics()%></td>
                    <td><%=bean.getChemistry()%></td>
                    <td><%=bean.getMaths()%></td>
                     <td><%=total%></td>
                      <td><%=percentage%></td>
                      <td>
                      <%
								if(phy >=33 && chem>=33 && math >= 33) { %>
								<span style="color: green"> Pass</span>
								<%}else{ %>
								<span style="color: red"> Fail</span>
								<% } %>
								</td>
                    <td><a href="MarksheetCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>

                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td><input type="submit" name="operation"
                        value="<%=MarksheetListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    <td><input type="submit" name="operation"
                        value="<%=MarksheetListCtl.OP_NEW%>"></td>
                    <td><input type="submit"
                        name="operation" value="<%=MarksheetListCtl.OP_DELETE%>"></td>
                   
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>"><br><br><br>
                <%} %>
        </form>
    </center>
    <br><br>
    <%@include file="Footer.jsp"%>
       
</body>

</html>