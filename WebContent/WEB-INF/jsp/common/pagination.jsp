<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" 
%><%@page import="org.apache.struts2.ServletActionContext"
%><%@page import="com.opensymphony.xwork2.util.ValueStack"%>
<s:set var="currentPage" value="page ? page : 1"/>
<s:set var="totalCount">${param.totalCount}</s:set>
<s:set var="windowSize" value="10"/>
<s:set var="startPage" value="0"/>
<s:set var="endPage" value="0"/>
<s:set var="left" value="0"/>
<s:set var="right" value="0"/>

<%

	ValueStack vs = ServletActionContext.getValueStack(request);
	double totalCount = Double.parseDouble(String.valueOf(vs.findValue("totalCount")));
	int pageSize = Integer.parseInt(String.valueOf(vs.findValue("pageSize")));		
	int lastPage = 1;

	if(totalCount >= pageSize)lastPage = (int)Math.ceil(totalCount / pageSize);
	vs.set("lastPage", lastPage);
	
%>

<s:if test="%{#currentPage lte ((#windowSize / 2) + 1)}">
	<s:set var="startPage" value="1"/>
	<s:set var="endPage" value="%{(#currentPage + 1) + (#windowSize - (#currentPage - 1))}"/>
	<s:if test="%{#endPage gt lastPage}"><s:set var="endPage" value="%{lastPage}"/></s:if>
</s:if>
<s:elseif test="%{#currentPage gte (lastPage - (#windowSize / 2))}">
	<s:set var="startPage" value="%{#currentPage - (#windowSize - (lastPage - #currentPage))}"/>
	<s:set var="endPage" value="%{lastPage}"/>
	<s:if test="%{#startPage lt 1}"><s:set var="startPage" value="1"/></s:if>
</s:elseif>
<s:else>
	<s:set var="startPage" value="%{#currentPage - (#windowSize / 2)}"/>
	<s:set var="endPage" value="%{#startPage + #windowSize}"/>
</s:else>

<s:if test="%{#startPage - 1 > 1}">
	<s:a includeParams="get">
		<s:param name="page" value="1"/>		
		1
	</s:a>
	...
</s:if>

<s:iterator begin="#startPage" end="#endPage" var="pg">
		<s:if test="#currentPage neq #pg">
			<s:a includeParams="get">
				<s:param name="page" value="#pg"/>		
				${ pg }	
			</s:a>		
		</s:if>
		<s:else>
			${ pg }
		</s:else>
</s:iterator>

<s:if test="%{#endPage < lastPage - 1}">
	...
	<s:a includeParams="get">
		<s:param name="page" value="lastPage"/>		
		${ lastPage }	
	</s:a>
</s:if>