package com.arnav.services;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
	
	@Autowired
	private Environment env;

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMailWithTemplate(final String recipientName,
			final String recipientEmail,
			final String subject,
			final String template,
			final Map<String, String> processData)
			throws MessagingException{
		
		// Prepare the evaluation context
		final Context ctx = new Context();
		ctx.setVariable("name", recipientName);
		for (Map.Entry<String, String> entry : ((Map<String, String>)processData).entrySet()) {
		    ctx.setVariable(entry.getKey(), entry.getValue());
		}
		
		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
		MimeMessageHelper message;
		
		try {
			  message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			  message.setSubject(subject);
			  message.setFrom(env.getProperty("support.email"));
			  message.setTo(recipientEmail);
			  message.setText("Hello are you ready change your password = " + 
			  ctx.getVariables().get("resetLink"), true); // true = isHtml
			 
		  } catch (javax.mail.MessagingException e) {
			  e.printStackTrace();
		  } 
			 
	  // Send mail
	  this.mailSender.send(mimeMessage);
	}

}
