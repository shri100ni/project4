<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="in.co.rays.bean.CollegeBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.controller.CollegeListCtl"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.ORSView"%>
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
 <jsp:useBean id="bean" class="in.co.rays.bean.CollegeBean" scope="request"></jsp:useBean>
    <center>
        <h1>College List</h1>
        
         <h2> <font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
          <font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>
           
        <form action="<%=ORSView.COLLEGE_LIST_CTL%>" method="POST">        
        <%  

        List l2= (List)ServletUtility.getList(request);
       
        int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

          if(l2.size()!=0){
            List l=(List)request.getAttribute("collegeName");
        %>

<table width="100%">
                <tr>
                    <td align="center">
                    <label> Name : </label>
                     <%=HTMLUtility.getList("collegename", String.valueOf(bean.getId()), l) %>
                    <label>City : </label> 
                    <input type="text" name="city" placeholder="Enter College City"  value="<%=ServletUtility.getParameter("city", request)%>">
                      &emsp;
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_SEARCH%>">&emsp;
                        <input type="submit" name="operation" value="<%=CollegeListCtl.OP_RESET%>"></td>
                        
                </tr>
            </table>
            <br>
            <table border="1" width="100%">
                <tr>
                    <th style="padding-left: "><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th>S.No.</th>
                    <th>Name.</th>
                    <th>Address.</th>
                    <th>State.</th>
                    <th>City.</th>
                    <th>PhoneNo.</th>
                    <th>Edit</th>
                </tr>
                
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
/*                     System.out.println(pageNo+"<<<<"+">>>"+pageSize);
 */                    int index = ((pageNo - 1) * pageSize) + 1;
/*                     System.out.println(index);
 */
                    List list = ServletUtility.getList(request);
                    Iterator<CollegeBean> it = list.iterator();

                    while (it.hasNext()) {

                        /* CollegeBean */ bean = it.next();
                %>
                
                <tr align="center">
                    <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>
                    <td><%=index++%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getAddress()%></td>
                    <td><%=bean.getState()%></td>
                    <td><%=bean.getCity()%></td>
                    <td><%=bean.getPhoneNo()%></td>
                    <td><a href="CollegeCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEW%>"></td>
                    <td><input type="submit" name="operation" value="<%=CollegeListCtl.OP_DELETE%>"></td>
                    <td align="right"><input type="submit" name="operation" value="<%=CollegeListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> 
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
                <br><br><br>
              
                
                <%} else if(l2.size() == 0){ %>
              <br><input type="submit" name="operation" value="<%=CollegeListCtl.OP_BACK%>">
              <%} %>
</form>
    <%@ include file="Footer.jsp"%>

</body>
</html>