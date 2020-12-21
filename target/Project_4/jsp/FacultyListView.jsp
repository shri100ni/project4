<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.bean.FacultyBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


  <%@page import="in.co.rays.controller.FacultyListCtl"%>
  
  <%@page import="in.co.rays.util.ServletUtility"%>
  
  <%@page import="in.co.rays.util.HTMLUtility"%>
  
  <%@page import="java.util.List"%>
  
  <%@page import="java.util.Iterator"%>
  

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

 <%@include file="Header.jsp"%>

    <center>
        
       <form action="<%=ORSView.FACULTY_LIST_CTL%>" method="post">
      
        <jsp:useBean id="bean" class="in.co.rays.bean.FacultyBean" scope="request"></jsp:useBean>

        <%
           
             List l = (List)request.getAttribute("CollegeList");
        int next=DataUtility.getInt(request.getAttribute("nextlist").toString());

             List l1= (List)ServletUtility.getList(request);
         
        %>
        <h1>Faculty List</h1>
         <h2><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h2>
          <h2><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h2>
          
        <%if(l1 == null || l1.size() == 0){ %>
         
             <br><input type="submit" name="operation" value="<%=FacultyListCtl.OP_BACK%>"> 
        <%}else{ %>
         
            <table width="100%">
               <tr>
                    <td align="center">
                    
                 
                   <label>FirstName : </label> 
                    <input type="text" name="firstName"  placeholder="Enter First_Name"
                        value="<%=ServletUtility.getParameter("firstName", request)%>">
                        &emsp; 
                   <label>College Name : </label> 
                   <%=HTMLUtility.getList("collegeId",String.valueOf(bean.getCollegeId()), l)%>  
                 
                  &emsp; 
                  <input type="submit" name="operation" value="<%=FacultyListCtl.OP_SEARCH %>">
                  <input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET %>">
                  </td>
                </tr>
            </table>
            <br>

            <table border="1" width="100%">
                <tr>
                    <th ><input type="checkbox" id="select_all" name="select">Select All</th>
                    <th >S.No.</th>
                    <th >FirstName</th>
                    <th >LastName</th>
                    <th >EmailId</th>
                    <th >Gender</th>
                    <th >DOJ</th>
                    <th >Mobile No</th>
                    <th >CollegeName</th>
                    <th >CourseName</th>
                    <th >SubjectName</th>
                    <th >Edit</th>
                </tr>

               
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<FacultyBean> it = list.iterator();
                    while (it.hasNext()) {
                       FacultyBean fb = it.next();
                %>
                <tr>
                    <td align="center"><input type="checkbox" class="checkbox" name="ids" value="<%=fb.getId()%>"></td>
                    <td align="center"><%=index++%></td>
                    <td align="center"><%=fb.getFirstName()%></td>
                    <td align="center"><%=fb.getLastName()%></td>
                    <td align="center"><%=fb.getEmailId()%></td>
                    <td align="center"><%=fb.getGender()%></td>
                    <td align="center"><%=fb.getDob()%></td>
                    <td align="center"><%=fb.getMobNo()%></td>
                    <td align="center"><%=fb.getCollegeName()%></td>
                    <td align="center"><%=fb.getCourseName()%></td>
                    <td align="center"><%=fb.getSubjectName()%></td>
                    
                    <td align="center"><a href="FacultyCtl?id=<%=fb.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            <table width="100%">
                <tr>
                    <td ><input type="submit" name="operation"
                        value="<%=FacultyListCtl.OP_PREVIOUS%>"<%=(pageNo==1)?"disabled":"" %>></td>
                    
                    <td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEW%>"></td>
                   
                    <td><input type="submit" name="operation" value="<%=FacultyListCtl.OP_DELETE%>"></td>
                   
                    <td align="right">
                    <input type="submit" name="operation" value="<%=FacultyListCtl.OP_NEXT%>"<%=(list.size()<pageSize)||next==0?"disabled":"" %>></td>
                </tr>
            </table>
            <br>
            <br>
            <br>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">
         <%} %>
        </form>
    </center>
 </body>
   <%@include file="Footer.jsp"%> 

</body>
</html>