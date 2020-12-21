<%@page import="java.util.LinkedHashMap"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

   <%@page import="in.co.rays.controller.CourseCtl"%>
   
   <%@page import="in.co.rays.util.DataUtility"%>
   
   <%@page import="in.co.rays.util.ServletUtility"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
     
     <%@ include file="Header.jsp"%>

     <jsp:useBean id="bean" class="in.co.rays.bean.CourseBean" scope="request"></jsp:useBean>
   
     <form action="<%=ORSView.COURSE_CTL%>" method="post">
     
    <center>   
    <%if(bean.getId()>0){ %>
       
            <h1>Update Course</h1>
    <%
    }else{%>
     
            <h1>Add Course</h1>
            
      <%} %>

            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>

            <input type="hidden" name="id" value="<%=bean.getId()%>"> 
             
            <input type="hidden" name="createdBy"  value="<%=bean.getCreatedBy()%>">
            
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
            
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
           
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

            <table>
                <tr>
                    <td>Name<font style="color: red">*</font></td>
                    <td>
                    <input type="text" name="name" placeholder="Enter Course Name"
                        value="<%=DataUtility.getStringData(bean.getName())%>">
                    </td>
                    <td style="position: fixed;">
                    <font color="red"><%=ServletUtility.getErrorMessage("name",request) %> </font></td>
                </tr>
               
                <tr>
                    <td>Duration<font style="color: red">*</font></td>
                   <td>
                   
                   <%
           		LinkedHashMap map = new LinkedHashMap();

/* 	    HashMap map = new HashMap();
 */		map.put("1 Years", "1 Years");
    	map.put("2 Years", "2 Years");
    	map.put("3 Years", "3 Years");
    	map.put("4 Years", "4 Years");
    	map.put("5 Years", "5 Years");
    	map.put("6 Years", "6 Years");
    	
  	  String htmlList = HTMLUtility.getList("duration", bean.getDuration(), map);
	%> 
	<%=htmlList%>
                   <%-- <input type="text" name="duration" placeholder="Enter Course Duration" 
                         value="<%=DataUtility.getStringData(bean.getDuration())%>"> --%></td>
                    <td style="position: fixed;">    
                   <font color="red"> <%=ServletUtility.getErrorMessage("duration", request)%></font></td>
                </tr><br>
                
               <tr>
                    <td>Description<font style="color: red">*</font></td>
                    <td>
                   <input type="text" style="height: 50px; width: 169px" name="description" placeholder="Enter Description" 
                                value="<%=DataUtility.getStringData(bean.getDescription())%>">
                  <%--  <input type="text" name="description" placeholder="Enter Description" 
                                value="<%=DataUtility.getStringData(bean.getDescription())%>"> --%>
                     </td>
                     
                     <td style="position: fixed;"> 
                     <font color="red"> <%=ServletUtility.getErrorMessage("description", request)%></font>
                     </td>
                </tr>
                <tr>
                 <td></td>  
                 <td align="center" colspan="2">
                 <%
                     if (bean.getId() > 0) {
		  	 %>
		  <input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE%>">  
		  
		  <input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>"> 
		   <%
             }
            else
             {
		   %>
	    
	      <input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">  
		  
		  <input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>"> 
	    
	       <%
             }
	       %>        
                </td>
           </tr>
            </table>
           </center>
    </form>
   
    <%@ include file="Footer.jsp"%>
     
</body>
</html>