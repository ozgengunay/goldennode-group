package com.thingabled.commons.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class Mailer {
	public Mailer() {
	}

	public static void sendEmail(String from, String to, String subject, String body, MailType mailType) {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("localhost");
			email.addTo(to);
			if (from == null)
				from = "noreply@thingabled.com";
			email.setFrom(from);
			email.setSubject(subject);
			if (mailType == MailType.HTML) {
				email.setHtmlMsg(body);
				email.setTextMsg("Your email client does not support HTML messages");
			} else {
				email.setTextMsg(body);
			}
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
