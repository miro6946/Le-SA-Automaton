<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" 
%><%@page import="org.apache.commons.lang3.StringUtils"
%><%@page import="java.net.URI"%>
<%
String returnUrl = null;

if(request.getMethod().equals("GET")){
	returnUrl = request.getHeader("referer"); 
	if(StringUtils.isNotEmpty(returnUrl))returnUrl = URI.create(returnUrl).getPath();
}

if(StringUtils.isEmpty(returnUrl))returnUrl = "/index.action";
%>
<tiles:insertDefinition name="default.layout">
<tiles:putAttribute name="javascript">
<script type="text/javascript">
location.href = '<%=returnUrl%>';
</script>
</tiles:putAttribute>

</tiles:insertDefinition>