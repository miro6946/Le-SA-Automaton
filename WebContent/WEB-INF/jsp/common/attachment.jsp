<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"
%><%@ include file="/WEB-INF/jsp/common/directive.jsp" %>
<s:form id="attachment_caller" method="post" action="new" namespace="/attachments" cssStyle="margin:0;padding:0;">
<s:hidden name="attachment.attachableSeq"/>
<s:hidden name="attachment.attachableType"/>
<s:hidden name="attachment.secure"/>
<s:hidden name="attachment.uploadType"/>
</s:form>
<s:form id="attachment_disposer" method="post" action="destroy" namespace="/attachments" cssStyle="margin:0;padding:0;">
<s:token/>
<s:hidden name="attachment.seq"/>
<s:hidden name="attachment.attachableSeq"/>
<s:hidden name="attachment.attachableType"/>
</s:form>