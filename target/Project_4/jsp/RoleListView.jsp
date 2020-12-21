<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.controller.RoleListCtl"%>
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
<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="in.co.rays.bean.RoleBean" scope="request"></jsp:useBean>

    <center>
        <h1>Role List</h1>

        <form action="<%=ORSView.ROLE_LIST_CTL%>" method="post">
        <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
              <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
                              <% List l=(List)request.getAttribute("role"); 
                              int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

                List l2 = ServletUtility.getList(request);
                %>
        </h2>
         <%if(l2 == null || l2.size() == 0){ %>
             <br><input type="submit" name="operation" value="<%=RoleListCtl.OP_BACK%>"> 
        <%}else{ %>
            <table width="100%">
                <tr>
                    <td align="center"><label>Name : </label> <font><%=HTMLUtility.getList("role", String.valueOf(bean.getId()), l) %></font>
                        &nbsp; <input type="submit" name="operation" value="<%=RoleListCtl.OP_SEARCH %>">
                         &nbsp; <input type="submit" name="operation" value="<%=RoleListCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>
            <table border="1" width="100%">
                <tr>
                    <th style="padding-left: "><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th>S.No.</th>
<!--                     <th>Id</th>
 -->                    <th>Name</th>
                    <th>Description</th>
                    <th>Edit</th>
                </tr>
               
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<RoleBean> it = list.iterator();
                    while (it.hasNext()) {
                        /* RoleBean */ bean = it.next();
                %>
                <tr align="center">
                <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"
                    <%if(userBean.getId()==bean.getId()||bean.getId()==RoleBean.ADMIN){ %> <%="disabled" %><%} %>></td>

                    <td><%=index++%></td>
<%--                     <td><%=bean.getId()%></td>
 --%>                    <td><%=bean.getName()%></td>
                    <td><%=bean.getDescription()%></td>
                    <td><a href="RoleCtl?id=<%=bean.getId()%>"
                    <% if(bean.getId()==1){ %> onclick="return false;"<%} %>>Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                      <td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_NEW%>"></td> 
                        <td><input type="submit" name="operation"
						value="<%=RoleListCtl.OP_DELETE%>"></td> 
                    
                    <td align="right"><input type="submit" name="operation"
                        value="<%=RoleListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>"><br><br><br>
                <%} %>
        </form>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>