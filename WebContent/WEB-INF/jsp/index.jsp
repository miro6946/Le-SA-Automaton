<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
<tiles:insertDefinition name="default.layout">
<tiles:putAttribute name="title"><s:text name="home.title"/></tiles:putAttribute>
<tiles:putAttribute name="javascript">
<script type="text/javascript" src="/javascripts/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="/javascripts/uploader.js"></script>
<script type="text/javascript" src="/javascripts/form.js"></script>
<script type="text/javascript" src="/javascripts/popup.js"></script>
<script type="text/javascript">
var popups = [];

<s:iterator value="%{popups}" status="status" var="p">
	<s:set var="content">
		<s:if test="%{#p.isImageType()}">
		<a href="<s:property value="%{#p.linkUrl}" escape="false" escapeJavaScript="true"/>"><img src="<s:property value="%{#p.imagePath}"/>"/></a>
		</s:if>
		<s:elseif test="%{#p.isHtmlType()}">
		<s:property value="%{#p.html}" escape="false" escapeJavaScript="true"/>
		</s:elseif>
	</s:set>
	popups.push(new Popup(${ p.seq }, ${ p.top }, ${ p.left }, ${ p.width }, ${ p.height }, ${ p.scrollable }, ${ p.enableCloseButton }, '<s:property value="%{#p.bgColor}" escape="false" escapeJavaScript="true"/>', '${ content }', ${5000 + status.count}));
</s:iterator>

jQuery(function($){


	$(document).ajaxError(function(event, xhr, settings, exception){
		alert(appMessage.requestHttpError.replace('?', xhr.status));
		$.unblockUI();
	});
	

});
</script>
</tiles:putAttribute>

<tiles:putAttribute name="stylesheet">
<link rel="stylesheet" type="text/css" href="/stylesheets/application.css">
<style type="text/css">
.recently{font-size:8pt;}
</style>
</tiles:putAttribute>

<tiles:putAttribute name="content">

</tiles:putAttribute>

</tiles:insertDefinition>