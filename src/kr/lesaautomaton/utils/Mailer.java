package kr.lesaautomaton.utils;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

/**
 * ���� �߼� ��ƿ
 * 
 * @author Dongsu Lee
 * 
 */
public class Mailer implements Runnable {
	
	private static final Logger LOG = Logger.getLogger(Mailer.class);
	private static final String PROP_PATH = "/mailer.properties";

	private Session mailSession = null;

	private String mailKind = "";

	/** ������ ��� */
	private String mailFrom = "";
	/** �޴� ��� */
	private String rcptTO = "";
	/** ����  */
	private String rcptCC = "";
	/** ���� ���� */
	private String rcptBCC = "";
	/** ���� */
	private String subject = "";
	/** �������� */
	private String contents = "";
	
	private ArrayList attaches = new ArrayList();

	/**
	 * Constructor
	 * 
	 * @throws Exception
	 */
	public Mailer() throws Exception {
			
		if(mailSession==null) {
			
			Properties properties = new Properties();
			properties.load(new InputStreamReader(Mailer.class.getResourceAsStream(PROP_PATH)));

			// Get session
			mailSession = Session.getDefaultInstance(properties, null);
			mailSession.setDebug(Boolean.valueOf(properties.getProperty("mail.session.debug")));
		
		
		}
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param host
	 * @param debug
	 */
	public Mailer(String host, boolean debug) {
		// Get system properties
	 	Properties props = System.getProperties();

		// Setup mail server
	 	props.put("mail.smtp.host", host);

		// Get session
		mailSession = Session.getDefaultInstance(props, null);
		mailSession.setDebug(debug);
		
	}
	
	/**
	 * Constructor
	 * 
	 * @param host
	 */
	public Mailer(String host) {
		
		this(host, false);
	}

	/**
	 * ���� Thread�� �����ؼ� ���� �����⸦ �õ��� ��� ���ȴ�.
	 */
	public void run() {
		try {
			// ���� ����
			sendMail(mailFrom, rcptTO, rcptCC, rcptBCC, subject, contents);
			LOG.debug(String.format("Mailer send to : %s successfully.", rcptTO));

		} catch (Exception e) {
			LOG.debug(String.format("Mailer send to : %s unsuccessfully.", rcptTO));
			//Logger.error.println("[MailUtil.run()] " + mailKind + " : " + e, true);
			e.printStackTrace();
		}
	}
	
	/**
	 * ���� ����
	 * 
	 * @throws AddressException
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 */
	public void sendMail() throws AddressException, UnsupportedEncodingException, MessagingException {
		sendMail(mailFrom, rcptTO, rcptCC, rcptBCC, subject, contents);
	}

	/**
	 * ���� ����
	 *
	 * @param mailSession 
	 * @param mailFrom
	 * @param rcptTo
	 * @param cc
	 * @param subject
	 * @param contents
	 */
	public void sendMail(String mailFrom, String rcptTo, String cc, String bcc, String subject, String contents)
		throws AddressException, UnsupportedEncodingException, MessagingException {

		MimeMessage message = null;
		InternetAddress[] addresses = null;
		Transport transport = null;
		String str = null;
		try {
			message = new MimeMessage(mailSession); // ���� �޽���
			message.setHeader("Content-Type", "text/html; charset=UTF-8");

			// �۽���
			str = mailFrom;
			if (str != null && !str.trim().equals("")) {
				InternetAddress sender = new InternetAddress(str);
				String senderName = sender.getPersonal();
				if (senderName != null) {
					sender.setPersonal(MimeUtility.encodeText(senderName));
				}
				message.setFrom(sender);
			}

			// ������
			str = rcptTo;
			if (str == null)
				str = "";
			if (str != null && !str.trim().equals("")) {
				str = str.replace(';', ',');
				addresses = InternetAddress.parse(str);
				for (int i = 0; i < addresses.length; i++) {
					String tmpStr = addresses[i].getPersonal();
					if (tmpStr != null) {
						addresses[i].setPersonal(MimeUtility.encodeText(tmpStr));
					}
				}
				message.setRecipients(Message.RecipientType.TO, addresses);
			}

			// ����
			str = cc;
			if (str == null)
				str = "";
			if (str != null && !str.trim().equals("")) {
				str = str.replace(';', ',');
				addresses = InternetAddress.parse(str);
				for (int i = 0; i < addresses.length; i++) {
					String tmpStr = addresses[i].getPersonal();
					if (tmpStr != null) {
						addresses[i].setPersonal(MimeUtility.encodeText(tmpStr));
					}
				}
				message.setRecipients(Message.RecipientType.CC, addresses);
			}

			// ���� ����
			str = bcc;
			if (str == null)
				str = "";
			if (str != null && !str.trim().equals("")) {
				str = str.replace(';', ',');
				addresses = InternetAddress.parse(str);
				for (int i = 0; i < addresses.length; i++) {
					String tmpStr = addresses[i].getPersonal();
					if (tmpStr != null) {
						addresses[i].setPersonal(MimeUtility.encodeText(tmpStr));
					}
				}
				message.setRecipients(Message.RecipientType.BCC, addresses);
			}

			// ����
			str = subject;
			if (str.trim().equals("")) {
				str = "(No Subject)";
			}
			message.setSubject(str, "UTF-8");


			if(attaches.size()==0) {
				// ����
				message.setContent(contents, "text/html; charset=UTF-8");
							
			} else {
				// MultiPart �޽��� ����
				Multipart multipart_message = new MimeMultipart();

				// ���� ���� �߰�
				MimeBodyPart message_body = new MimeBodyPart();
				message_body.setContent(contents, "text/html; charset=UTF-8");
				multipart_message.addBodyPart(message_body);
				
				// ÷�� ���� �߰�
				DataSource file_data_source   = null;
				DataHandler file_data_handler = null;
				MimeBodyPart file_attachment  = null;
				for(int i=0; i<attaches.size(); i++) {
					file_data_source  = new FileDataSource((String)attaches.get(i));
					file_data_handler = new DataHandler(file_data_source);
					file_attachment   = new MimeBodyPart();
					file_attachment.setDataHandler(file_data_handler);
					file_attachment.setFileName( 
						MimeUtility.encodeText(file_data_source.getName(), "UTF-8", "B") 
					);

					// ���� �޽����� ÷�� ���� �߰�
					multipart_message.addBodyPart(file_attachment);
				}
				message.setContent(multipart_message);
			}

			transport = mailSession.getTransport("smtp");
			if(Boolean.valueOf(mailSession.getProperty("mail.smtp.auth"))){
				transport.connect(mailSession.getProperty("mail.smtp.auth.username"), mailSession.getProperty("mail.smtp.auth.password"));
			}else{
				transport.connect();
			}
			transport.sendMessage(message, message.getAllRecipients());

		} finally {
			if (transport != null) {
				try {
					transport.close();
					transport = null;
				} catch (Exception ig) {
				}
			}
		}

	}

	/**
	 * ÷�����ϰ�� �߰�
	 * 
	 * @param filePath
	 */
	public void addAttach(String filePath) {
		attaches.add(filePath);
	}
	
	/**
	 * ÷������ ���� ��������
	 * 
	 * @param idx
	 * @return
	 */
	public String getAttach(int idx) {
		if(attaches.size()<=idx) return null;
		return (String) attaches.get(idx);
	}
	
	/**
	 * ÷�����ϵ� ���� ��ȯ
	 * 
	 * @return
	 */
	public ArrayList getAttaches() {
		return attaches;
	}

	/**
	 * @return
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @return
	 */
	public String getMailFrom() {
		return mailFrom;
	}

	/**
	 * @return
	 */
	public Session getMailSession() {
		return mailSession;
	}

	/**
	 * @return
	 */
	public String getRcptBCC() {
		return rcptBCC;
	}

	/**
	 * @return
	 */
	public String getRcptCC() {
		return rcptCC;
	}

	/**
	 * @return
	 */
	public String getRcptTO() {
		return rcptTO;
	}

	/**
	 * @return
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param string
	 */
	public void setContents(String string) {
		if (string == null)
			return;
		contents = string;
	}

	/**
	 * @param string
	 */
	public void setMailFrom(String string) {
		if (string == null)
			return;
		mailFrom = string;
	}

	/**
	 * @param string
	 */
	public void setRcptBCC(String string) {
		if (string == null)
			return;
		rcptBCC = string;
	}

	/**
	 * @param string
	 */
	public void setRcptCC(String string) {
		if (string == null)
			return;
		rcptCC = string;
	}

	/**
	 * @param string
	 */
	public void setRcptTO(String string) {
		if (string == null)
			return;
		rcptTO = string;
	}

	/**
	 * @param string
	 */
	public void setSubject(String string) {
		if (string == null)
			return;
		subject = string;
	}

	/**
	 * @return
	 */
	public String getMailKind() {
		return mailKind;
	}

	/**
	 * @param string
	 */
	public void setMailKind(String string) {
		if (string == null)
			return;
		mailKind = string;
	}
}
