<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
var actionMessages = [], actionErrors = [], fieldErrors = [], serverMessages = [];

<s:iterator value="%{actionMessages}" var="message">
	actionMessages.push('<s:property value="%{@org.apache.commons.lang3.StringEscapeUtils@escapeEcmaScript(#message)}"/>');
</s:iterator>
<s:iterator value="%{actionErrors}" var="message">
	actionErrors.push('<s:property value="%{@org.apache.commons.lang3.StringEscapeUtils@escapeEcmaScript(#message)}"/>');
</s:iterator>
<s:iterator value="%{fieldErrors}">
	<s:iterator value="%{value}" var="message">
		fieldErrors.push('<s:property value="%{@org.apache.commons.lang3.StringEscapeUtils@escapeEcmaScript(#message)}"/>');
	</s:iterator>
</s:iterator>

serverMessages = $.merge(actionMessages, actionErrors);
serverMessages = $.merge(serverMessages, fieldErrors);

if(serverMessages.length)alert(serverMessages.join('\n'));