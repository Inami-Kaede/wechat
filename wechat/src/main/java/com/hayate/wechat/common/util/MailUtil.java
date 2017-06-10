package com.hayate.wechat.common.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 电子邮件工具类
 * 
 * @author Administrator
 *
 */
public class MailUtil {

	/**
	 * 发送电子邮件
	 * 
	 * @param addr
	 *            收件人地址
	 * @param subject
	 *            主题
	 * @param text
	 *            内容
	 * @throws MessagingException
	 */
	public static void sendMail(String addr, String subject, String text){
		Transport transport = null;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", MAIL_HOST);
			props.put("mail.smtp.auth", "true");
			//props.put("mail.smtp.port", "465");
			props.put("mail.smtp.ssl.enable", "true");
			Session session = Session.getInstance(props);
			// 构造信息体 
			MimeMessage message = new MimeMessage(session);
			// 发件地址 
			Address address = new InternetAddress(MAIL_FROM);
			message.setFrom(address);
			// 收件地址 
			Address toAddress = new InternetAddress(addr);
			message.setRecipient(MimeMessage.RecipientType.TO, toAddress);
			// 主题 
			message.setSubject(subject);
			// 正文 
			message.setText(text);
			message.saveChanges();
			transport = session.getTransport("smtp");
			transport.connect(MAIL_HOST, MAIL_FROM, MAIL_AUTH); // 发送 
			transport.sendMessage(message, message.getAllRecipients());
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {			
			try {
				if (transport != null) {
					transport.close();
					transport = null;
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
						
		}
		System.out.println("Mail sent.");  
		

	}

	/**
	 * 邮件发送服务器地址
	 */
	public static final String MAIL_HOST = "smtp.qq.com";
	/**
	 * 发件人地址
	 */
	public static final String MAIL_FROM = "1541635693@qq.com";

	/**
	 * 发件人密码
	 */
	private static final String MAIL_AUTH = "apprpnkcbthehjhj";

	public static String getMailAuth() {
		return MAIL_AUTH;
	}

}
