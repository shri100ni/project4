<%@page import="in.co.rays.controller.MyProfileCtl"%>
<%@page import="in.co.rays.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.util.DataUtility"%>
<%@page import="in.co.rays.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<script >
$( function() {
    $( "#datepicker" ).datepicker({ 
    	changeMonth :true,
		  changeYear :true,
		  yearRange: '1980:2001',
		  
		  
		  dateFormat:'dd-mm-yy'
		 /*  endDate: '-9y' */  
		 }) ;
  } );
</script>
</head>
<body>
<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">
<%@ include file="Header.jsp"%>
<script type="text/javascript" src="../js/calendar.js"></script>
        <jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>My Profile</h1>
            
            <H2>
                <font color="red"> <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <input type="hidden" name="id" value="<%=bean.getId()%>">
            <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
            <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
            <input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
            <input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
            

            <table>
             <tr>
                    <th align="left">LoginId<font color="red">*</font></th>
                    <td><input type="text" name="login"
                        value="<%=DataUtility.getStringData(bean.getLogin())%>"readonly="readonly">
                        </td>
						<td style="position : fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
                </tr>

                <tr>
                    <th align="left">First Name<font color="red">*</font></th>
                    <td><input type="text" name="firstName"
                        value="<%=DataUtility.getStringData(bean.getFirstName())%>">
                        </td>
						<td style="position : fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Last Name<font color="red">*</font></th>
                    <td><input type="text" name="lastName"
                        value="<%=DataUtility.getStringData(bean.getLastName())%>">
                        </td>
						<td style="position : fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
                </tr>
                <tr>
                    <th align="left">Gender<font color="red">*</font></th>
                    <td style="position : fixed;">
                        <%
                            HashMap map = new HashMap();
                            map.put("Male", "Male");
                            map.put("Female", "Female");

                            String htmlList = HTMLUtility.getList("gender", bean.getGender(),
                                    map);
                        %> <%=htmlList%><br>
                        
                    </td>
                    
                </tr>
                <tr>
                    <th align="left">Mobile No<font color="red">*</font></th>
                    <td><input type="text" name="mobileNo" maxlength="10"
                        value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
						<td style="position : fixed;"><font
                        color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
                </tr>

                <tr>
                    <th align="left">Date Of Birth <font color="red">*</font></th>
                    <td><input id="datepicker" type="text" name="dob" readonly="readonly"
                        value="<%=DataUtility.getDateString(bean.getDob())%>">
                  </td>
						<td style="position : fixed;">
                    <font
                        color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
                </tr>
                
            <H2>
                <font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
                
                <tr>
                    <th></th>
                    <td colspan="2">
                    <input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE %>"> &nbsp;
                    <input type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD %>"> &nbsp; </td>
                </tr>
            </table>
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>