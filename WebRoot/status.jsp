<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'status.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<HEAD>

  <TITLE>JSP进度条</TITLE>
<jsp:useBean id="task" scope="session"  class="test.barBean.TaskBean"/>

  <% if (task.isRunning()) { %>

    <script type="" LANGUAGE="javascript">

      setTimeout("location='status.jsp'", 1000);

    </script>

  <% } %>

</HEAD>

<bODY bgcolor="">

<H1 ALIGN="CENTER">JSP进度条</H1>

  <H2 ALIGN="CENTER">

    结果: <%= task.getResult() %><BR>

    <% int percent = task.getPercent(); %>

    <%= percent %>%

  </H2>

  <TABLE WIDTH="60%" ALIGN="CENTER"

       CELLPADDING=0 CELLSPACING=2>

    <TR>

      <% for (int i = 10; i <= percent; i += 10) { %>

        <TD WIDTH="10%" height="10" BGCOLOR="red"> </TD>

      <% } %>

      <% for (int i = 100; i > percent; i -= 10) { %>

        <TD WIDTH="10%"> </TD>

      <% } %>

    </TR>

  </TABLE>

<TABLE WIDTH="100%" BORDER=0 CELLPADDING=0 CELLSPACING=0>

    <TR>

      <TD ALIGN="CENTER">

        <% if (task.isRunning()) { %>

          正在执行

        <% } else { %>

          <% if (task.isCompleted()) { %>

            完成

          <% } else if (!task.isStarted()) { %>

            尚未开始

          <% } else { %>

            已停止

          <% } %>

        <% } %>

      </TD>

    </TR>
<TR>

      <TD ALIGN="CENTER">

        <BR>

        <% if (task.isRunning()) { %>

          <FORM METHOD="GET" ACTION="stop.jsp">

            <INPUT TYPE="SUBMIT" ="停止">

          </FORM>

        <% } else { %>

          <FORM METHOD="GET" ACTION="start.jsp">

            <INPUT TYPE="SUBMIT" ="开始">

          </FORM>

        <% } %>

      </TD>

    </TR>

  </TABLE>

</BODY>

</html>
