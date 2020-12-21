
<%@page import="in.co.rays.controller.GetMarksheetCtl"%>
<%@page import="in.co.rays.util.DataUtility"%>
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
	<jsp:useBean id="bean" class="in.co.rays.bean.MarksheetBean"
		scope="request"></jsp:useBean>
	<center>
	
	
		<h1>Get Marksheet</h1>

		<h2><font color="red"> <%=ServletUtility.getErrorMessage(request)%>
		</font></h2>

		<H2>
			<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
			</font>
		</H2>
		<form action="<%=ORSView.GET_MARKSHEET_CTL%>" method="post">
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<th align="left">RollNo<font color="red">*</font> :</th>&emsp;
				<td><input type="text" name="rollNo"placeholder="Enter RollNo"
					value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;</td>
				<td><input type="submit" name="operation"
					value="<%=GetMarksheetCtl.OP_GO%>"></td>
					 <td style="position: fixed;"> <font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
				<%
                    if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
                    	

    					int phy = bean.getPhysics();
    					int chem = bean.getChemistry();
    					int math = bean.getMaths();
    					int total = phy + chem + math;
    					float percentage = total / 3;
                 %>
               <table border="1px" width="100%" >
                <tr >
                <th colspan="8">RollNo : <%=bean.getRollNo() %></th>
                </tr>
                <tr >
                <th colspan="8px">Name : <%=bean.getName() %></th>
                </tr>
				<tr>
                <th colspan="2">Subject</th>
                <th colspan="2">Max marks.</th>
                <th colspan="2">Min marks.</th>
                <th colspan="2">Marks Obtained</th>
                </tr>
                <tr >
                <th colspan="6"></th>
                <th>marks</th>
                <th>Remarks*</th>
                
                </tr>
                <tr>
                <th colspan="2">Physics</th>
                <td colspan="2" align="center">100</td>
                <td colspan="2" align="center">35</td>
                <td colspan="1" align="center"><%=bean.getPhysics() %></td>
                
                <%if(bean.getPhysics()>=35) {%>
                <td align="center"><font color="green">pass</font>            
                </td>
                <%}else{ %>
                <td align="center"><font color="red">fail*</font>             
                </td>
                <%} %>
                </tr>
                 <tr>
                <th colspan="2">Chemistry</th>
                <td colspan="2" align="center">100</td>
                <td colspan="2" align="center">35</td>
                <td colspan="1" align="center"><%=bean.getChemistry() %></td>
                <%if(bean.getChemistry()>=35) {%>
                <td align="center"><font color="green">pass</font>              
                </td>
                <%}else{ %>
                <td align="center"><font color="red">fail*</font>             
                </td>
                <%} %>
                </tr>
                 <tr>
                <th colspan="2">Maths</th>
                <td colspan="2" align="center">100</td>
                <td colspan="2" align="center">35</td>
                <td colspan="1" align="center"><%=bean.getMaths() %></td>
                <%if(bean.getMaths()>=35) {%>
                <td align="center"><font color="green">pass</font>              
                </td>
                <%}else{ %>
                <td align="center"><font color="red">fail*</font>             
                </td>
                <%} %>
                </tr>
                <tr>
                <th colspan="2">total</th>
                <td colspan="2" align="center">300</td>
                <%-- <%= bean.getPhysics()+bean.getChemistry()+bean.getMaths()%> --%>
                <th colspan="4" align="center"><%=total%></th>
                </tr>
                <tr>
                <th colspan="4">Grand total</th>
                <th colspan="4" align="center"><%=total %>/300</th>
                </tr>
                <tr>
                <th colspan="4">Percentage</th>
                <th colspan="4"><%=total/3 %>%</th>
                </tr>
                <tr>
                <th colspan="4">Result</th>
                <% if(bean.getPhysics()>35||bean.getChemistry()>35||bean.getMaths()>35){%>
                <th colspan="4">PASS</th>
                <%}else{ %>
                <th colspan="4">FAIL</th>
                <%} %>
                </tr>
                </table>
            <%
                }
            %>
        </form>
	</center>
	    <%@ include file="Footer.jsp"%>
	
</body>
</html>