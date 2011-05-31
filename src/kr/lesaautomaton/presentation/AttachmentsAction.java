package kr.lesaautomaton.presentation;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;

import kr.lesaautomaton.persistence.domain.Attachment;
import kr.lesaautomaton.service.AttachmentLB;
import kr.lesaautomaton.utils.AttachmentUtils;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ValidationAware;

public class AttachmentsAction extends BaseAction implements Preparable,
		ValidationAware {

	private static final Logger LOG = Logger.getLogger(AttachmentsAction.class);

	private Attachment attachment;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	private long maxUploadSize;

	private boolean force;
	private InputStream inputStream;
	private long contentLength;
	private String contentDisposition;

	public String input() {

		//attachment.setSecure(true);

		// System.out.println(ServletActionContext.getServletContext().getContextPath());
		// System.out.println(ServletActionContext.getServletContext().getRealPath("../uploaded"));

		// HttpServletRequest request = ServletActionContext.getRequest();
		// Enumeration paramNames = request.getParameterNames();
		// while (paramNames.hasMoreElements()) {
		// String key = (String) paramNames.nextElement();
		// LOG.debug(key +":"+ request.getParameter(key));
		// }
		//
		//		
		// LOG.debug("attachment.getAttachableSeq():"+
		// attachment.getAttachableSeq());
		// LOG.debug("attachment.getAttachableType():"+
		// attachment.getAttachableType());

		return SUCCESS;

	}

	public String create() throws Exception {

		UploadInterceptor uploadInterceptor = new UploadInterceptor();
		uploadInterceptor.setMaximumSize(maxUploadSize);

		if (attachment.isImage())
			uploadInterceptor.setAllowedExtensions(AttachmentUtils.allowedImageExtenstionCommaDelimeter());

		if (uploadInterceptor.acceptable()) {

			attachment.setFile(upload);
			attachment.setOriginalFileName(uploadFileName);

			if (!AttachmentLB.getInstance().save(attachment, ServletActionContext.getServletContext()))
				addActionError(getText("upload.failure"));

			return SUCCESS;

		}

		return INPUT;

	}

	public String show() throws Exception {
		
		String filePath = null;

		attachment = AttachmentLB.getInstance().at(attachment.getSeq());
		
		if(attachment.isSecure() && account.getGroup().isGuest())return LOGIN_REQUIRED;

		if (attachment != null) {
			
			filePath = ServletActionContext.getServletContext().getRealPath((attachment.isSecure() ? "../" : "/") + attachment.getFilePath() + "/"+ attachment.getSeq());
			
//			if(attachment.isSecure())
//				filePath = ServletActionContext.getServletContext().getRealPath("../" + attachment.getFilePath() + "/"+ attachment.getSeq());
//			else
//				filePath = ServletActionContext.getServletContext().getRealPath("/" + attachment.getFilePath() + "/"+ URLEncoder.encode(attachment.getOriginalFileName(), "UTF-8"));

			inputStream = new FileInputStream(filePath);

			contentLength = attachment.getFileSize();

			if (attachment.isFile() || force)contentDisposition = String.format("attachment;filename=%s", URLEncoder.encode(attachment.getOriginalFileName(), "UTF-8"));

			return SUCCESS;

		} else {

			return NONE;

		}

	}

	public String destroy() throws Exception {
		
		if(AttachmentLB.getInstance().destroy(attachment, ServletActionContext.getServletContext().getRealPath("/"))){

			return SUCCESS;
		
		}else{
			
			return ERROR;
			
		}

	}

	@Override
	public void prepare() throws Exception {
		// TODO Auto-generated method stub
		maxUploadSize = AttachmentUtils.getMaxUploadSize(account);
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public long getMaxUploadSize() {
		return maxUploadSize;
	}

	public void setMaxUploadSize(long maxUploadSize) {
		this.maxUploadSize = maxUploadSize;
	}

	public boolean isForce() {
		return force;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public long getContentLength() {
		return contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	class UploadInterceptor extends FileUploadInterceptor {

		protected boolean acceptable() {

			ActionContext ac = ActionContext.getContext();

			return acceptFile(ac.getActionInvocation().getAction(), upload,
					uploadFileName, uploadContentType, "upload",
					(ValidationAware) ac.getActionInvocation().getAction(), ac
							.getLocale());

		}

	}

}
