<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name="home.title"/><tiles:insertAttribute name="title"/></title>
<script type="text/javascript" src="/javascripts/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/javascripts/jquery-ui-1.8.4.custom.min.js"></script>
<script type="text/javascript" src="/javascripts/jquery.form.js"></script>
<script type="text/javascript" src="/javascripts/jquery.blockUI.custom.js"></script>
<script type="text/javascript" src="/javascripts/jquery.bgiframe.js"></script>
<script type="text/javascript" src="/javascripts/jquery.flashembed-1.2.3.js"></script>
<script type="text/javascript" src="/javascripts/jquery.pngFix.js"></script>
<script type="text/javascript" src="/javascripts/jquery.timer.js"></script>
<script type="text/javascript" src="/javascripts/application.js"></script>
<script type="text/javascript" src="/javascripts/message_resources/ko.js"></script>
<link rel="stylesheet" type="text/css" href="/stylesheets/application.css">
<link rel="stylesheet" type="text/css" href="/stylesheets/smoothness/jquery-ui-1.8.4.custom.css">
<script type="text/javascript">
<s:include value="/WEB-INF/jsp/common/messages.js.jsp"/>

pageId = '<s:property value="pageId"/>';

jQuery(function(){



});
</script>
<tiles:insertAttribute name="javascript"/>
<style type="text/css">

</style>
<tiles:insertAttribute name="stylesheet"/>
</head>