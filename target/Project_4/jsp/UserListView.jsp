<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.model.RoleModel"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.controller.UserCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.controller.UserListCtl"%>
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
<jsp:useBean id="bean" class="in.co.rays.bean.UserBean" scope="request"></jsp:useBean>

     <form action="<%=ORSView.USER_LIST_CTL%>" method="post">

    <center>
        <h1>User List</h1>
        <h2><font color="red" ><%=ServletUtility.getErrorMessage(request)%></font></td>
               </h2> 
            <h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
                      <%
               List list = ServletUtility.getList(request);
              
              
               List l=(List)request.getAttribute("roleList");
               int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

               %> 
    <%if(list == null || list.size() == 0){ %>
             <br><input type="submit" name="operation" value="<%=UserListCtl.OP_BACK%>"> 
        <%}else{ %>
            <table width="100%">
                <tr>
                    <td align="center">
                        <label>Role : </label><font><%=HTMLUtility.getList("roleList", String.valueOf(bean.getRoleId()), l)%></font>
                       <label>FirstName : </label> <input
                        type="text" name="firstName" placeholder="Enter First Name"
                        value="<%=ServletUtility.getParameter("firstName", request)%>">
                        &emsp; 
                         <label>LoginId : </label> <input
                        type="text" name="login" placeholder="Enter LoginId"
                        value="<%=ServletUtility.getParameter("login", request)%>">
                     
                        &emsp; <input type="submit" name="operation" value="<%=UserListCtl.OP_SEARCH %>">
                        &emsp; <input type="submit" name="operation" value="<%=UserListCtl.OP_RESET %>">
                    </td>
                </tr>
            </table>
            <br>

            <table border="1" width="100%">
                <tr>
                    <th style="padding-left: 20px"><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th>S.No</th>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>Role</th>
                    <th>LoginId</th>
                    <th>Gender</th>
                    <th>DOB</th>
                    <th>MobileNo</th>
                    <th>Edit</th>
                </tr>

                

                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    
                    Iterator<UserBean> it = list.iterator();
                    while (it.hasNext()) {
                        /* UserBean */ bean = it.next();
                        RoleModel model=new RoleModel();
                        RoleBean roleBean=new RoleBean();
                        roleBean=model.findByPK(bean.getRoleId());
                        
                %>
                <tr align="center">
                    <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"
                    <%if(userBean.getId()==bean.getId()||bean.getRoleId()==RoleBean.ADMIN){ %> <%="disabled" %><%} %>></td>
                    <td><%=index++%></td>
                    <td><%=bean.getFirstName()%></td>
                    <td><%=bean.getLastName()%></td>
                    <td><%=roleBean.getName()%></td>
                    <td><%=bean.getLogin()%></td>
                    <td><%=bean.getGender()%></td>
                    <td><%=bean.getDob()%></td>
                    <td><%=bean.getMobileNo() %></td>
                    <td><a href="UserCtl?id=<%=bean.getId()%>"<%if(bean.getRoleId()==1){ %> onclick="return false;"<%} %>>Edit</a></td>
                </tr>
                <%
                    }
                %>
             
            </table>
            <table width="100%">
                <tr>
                    <td ><input type="submit" name="operation"
                        value="<%=UserListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                                           <td ><input type="submit" name="operation" value="<%=UserListCtl.OP_NEW%>"></td>
                     
                      <td ><input type="submit" name="operation" value="<%=UserListCtl.OP_DELETE%>"></td>
                     <td align="right"><input type="submit" name="operation"
                        value="<%=UserListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                                                            
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>"><br><br><br>
                <%} %>
        </form>
        <br><br><br>
    </center>
    <%@include file="Footer.jsp"%>
</body>
</html>