<%@page import="in.co.rays.util.ServletUtility"%>
<%@page import="in.co.rays.controller.ForgetPasswordCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <form action="<%=ORSView.FORGET_PASSWORD_CTL%>" method="post">

        <%@ include file="Header.jsp"%>

        <jsp:useBean id="bean" class="in.co.rays.bean.UserBean"
            scope="request"></jsp:useBean>

        <center>
            <h1>Forgot your password?</h1>
             
            <H2>
                <font color="green"><%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H2>
            <H2>
                <font color="red"><%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H2>
            <input type="hidden" name="id" value="<%=bean.getId()%>">

            <table>
                 <lable>Submit your email address and we'll send you password.</lable><br><br>
                <th align="left">Email Id <font color="red">*</font>:</th>&emsp;
                
                <td><input type="text" name="login" placeholder="Enter ID Here"
                    value="<%=ServletUtility.getParameter("login", request)%>">&emsp;</td>
                
               <td> <input type="submit" name="operation" value="<%=ForgetPasswordCtl.OP_GO%>"></td>
               
               <td style="position: fixed;"> <font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
               
            </table>
            
           
            
    </form>
    </center>
    <%@ include file="Footer.jsp"%>
</body>
</html>