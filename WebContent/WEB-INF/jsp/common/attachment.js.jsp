<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
<s:iterator value="#attachments" status="status" var="att">
<s:set var="filePath">
	<s:if test="%{#att.secure}">
		<s:url action="show" namespace="/attachments">
			<s:param name="attachment.seq">${att.seq}</s:param>
		</s:url>
	</s:if>
	<s:else>
		/${ att.filePath }/${ att.seq }
	</s:else>
</s:set>	
Uploader.listUp(new Attachment(${att.seq}, '${filePath}', '<s:property value="%{#att.originalFileName}"/>', ${att.fileSize}, '${att.uploadType}', ${att.secure}));
</s:iterator>