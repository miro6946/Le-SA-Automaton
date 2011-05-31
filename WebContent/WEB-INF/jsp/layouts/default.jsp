<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><tiles:insertAttribute name="title"/></title>
<script type="text/javascript" src="/javascripts/jquery-1.4.2.js"></script>
<script type="text/javascript" src="/javascripts/jquery.form.js"></script>
<script type="text/javascript" src="/javascripts/jquery.blockUI.custom.js"></script>
<script type="text/javascript" src="/javascripts/jquery.bgiframe.js"></script>
<script type="text/javascript" src="/javascripts/jquery.flashembed-1.2.3.js"></script>
<script type="text/javascript" src="/javascripts/jquery.pngFix.js"></script>
<script type="text/javascript" src="/javascripts/application.js"></script>
<script type="text/javascript" src="/javascripts/message_resources/ko.js"></script>
<s:head/>
<script type="text/javascript">
<s:include value="/WEB-INF/jsp/common/messages.js.jsp"/>
</script>
<tiles:insertAttribute name="javascript"/>
<tiles:insertAttribute name="stylesheet"/>
</head>
<body>

<s:include value="/WEB-INF/jsp/common/noscript.htm"/>

<tiles:insertAttribute name="content"/>

</body>
</html>