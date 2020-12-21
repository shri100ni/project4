<%@page import="in.co.rays.controller.MarksheetMeritListCtl"%>
<%@page import="in.co.rays.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
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
<center>
        <h1>Marksheet Merit List</h1>

        <form action="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method="POST">
          <tr>
                 <h2>   <td colspan="8"><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></td>
               </h2></tr>
            <br>
            
            <table border="1" width="100%">
                <tr>

                    <th>S.No</th>
                    <th>Roll No</th>
                    <th>Name</th>
                    <th>Physics</th>
                    <th>Chemistry</th>
                    <th>Maths</th>
                    <th>Total</th>
                    <th>Percentage</th>

                </tr>
              
                <%
                    int pageNo = ServletUtility.getPageNo(request);
                    int pageSize = ServletUtility.getPageSize(request);
                    int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
                    Iterator<MarksheetBean> it = list.iterator();

                    while (it.hasNext()) {

                        MarksheetBean bean = it.next();
 if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) {
                        	

        					int phy = bean.getPhysics();
        					int chem = bean.getChemistry();
        					int math = bean.getMaths();
        					int total = phy + chem + math;
        					float percentage = total / 3;
                       
                %>
                <tr align="center">
                     <td><%=index++%></td>
                    <td><%=bean.getRollNo()%></td>
                    <td><%=bean.getName()%></td>
                    <td><%=bean.getPhysics()%></td>
                    <td><%=bean.getChemistry()%></td>
                    <td><%=bean.getMaths()%></td>
                     <td><%=total%></td>
                    <td><%=percentage+"%"%></td> 

                </tr>

                <%
                      }
                    }
                %>
            </table>
            <table>
                <tr>
                    <td align="right"><input type="submit" name="operation"
                        value="<%=MarksheetMeritListCtl.OP_BACK%>"></td>
                </tr>
            </table>
            <input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
                type="hidden" name="pageSize" value="<%=pageSize%>">

        </form>
    </center>
        <%@ include file="Footer.jsp"%>
    
</body>
</html>