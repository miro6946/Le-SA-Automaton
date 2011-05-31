<%@ page language="java" contentType="application/x-javascript; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" 
%><%@page import="org.apache.struts2.util.TokenHelper"%>
<%
%>
$('input[name=<%=TokenHelper.DEFAULT_TOKEN_NAME%>]').val('<%=TokenHelper.setToken(TokenHelper.DEFAULT_TOKEN_NAME)%>');
