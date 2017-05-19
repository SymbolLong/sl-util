package com.zhang.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.activation.*;

public class MailUtil {

	private String host = "pop.ym.163.com"; 	// smtp服务器
	private String from = ""; 					// 发件人地址
	private String fromName = "";				// 发件人显示名称
	private String affix = ""; 					// 附件地址
	private String affixName = ""; 				// 附件显示名称
	private String user = ""; 					// 用户名
	private String pwd = ""; 					// 密码
	private String subject = ""; 				// 邮件标题
	private String body = "";					// 邮件内容
	
//	public final String BODY_HTML = "text/html;charset=utf-8";
//	public final String BODY_TEXT = "";
	
	public MailUtil(String host, String user, String pwd) {
		this.host = host;
		this.user = user;
		this.pwd = pwd;
		this.from = user;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setAffix(String affix) {
		this.affix = affix;
	}

	public void setAffixName(String affixName) {
		this.affixName = affixName;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	
	

	/**
	 * 发送邮件，多个收件人用逗号隔开
	 * @param to
	 */
	public void send(String toAddress) {

		Properties props = new Properties();

		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(true);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
		try {
			String[] tos = toAddress.split(",");
			InternetAddress[] address = new InternetAddress[tos.length];
			for(int i=0; i<tos.length; i++) {
				address[i] = new InternetAddress(tos[i].trim());
			}
			
			// 加载发件人地址
			message.setFrom(new InternetAddress("\"" + MimeUtility.encodeText(fromName.equals("") ? from : fromName) + "\" <" + from + ">"));
			// 加载收件人地址
			message.addRecipients(Message.RecipientType.TO, address);
			// 加载标题
			message.setSubject(subject);
			
			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();
			
			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(body, "text/html;charset=utf-8");
			
			multipart.addBodyPart(contentPart);
			if( ! affix.isEmpty()) {
				
				// 添加附件
				BodyPart messageBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(affix);
				// 添加附件的内容
				messageBodyPart.setDataHandler(new DataHandler(source));
				// 添加附件的标题
				// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
				sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				messageBodyPart.setFileName("=?UTF-8?B?"
						+ enc.encode(affixName.getBytes("UTF-8")) + "?=");
				System.out.println(affixName);
				multipart.addBodyPart(messageBodyPart);
			}

			// 将multipart对象放到message中
			message.setContent(multipart);
			
			
			
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(host, user, pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		MailUtil cn = new MailUtil("smtp.ym.163.com", "jiangjj@unionpaysmart.com", "meiyun1024");
		cn.setFrom("jiangjj@unionpaysmart.com");
		cn.setAffix("");
		cn.setAffixName("test.txt");
		cn.setSubject("测试带附件的邮件1");
		cn.setBody("支付失败的中奖用户列表\r\n\r\n\r\n\r\n");
		cn.send("18621801968@163.com,516469990@qq.com");
	}
}
