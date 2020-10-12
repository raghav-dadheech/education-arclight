package com.study.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class DefaultEmailService implements EmailService{

	@Autowired
    private JavaMailSender javaMailSender;
	
	@Autowired
    private VelocityEngine velocityEngine;
	
	@Override
	public void sendEmail(String email, String subject, Map<String, Object> model) throws Exception {
		MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(email);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText(geVelocityTemplateContent(model, "welcome_mail"), true);

		// hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

//        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

        javaMailSender.send(msg);
	}
	
	@Override
	public void sendWelcomeEmail(String email, String subject, Map<String, Object> model) throws Exception {
		sendMail(email, subject, model, "welcome_mail");
	}
	
	@Override
	public void sendInvitationEmail(String email, String subject, Map<String, Object> model) throws Exception {
		sendMail(email, subject, model, "invitation_mail");
	}
	
	@Override
	public void sendForgetPasswordEmail(String email, String subject, Map<String, Object> model) throws Exception {
		sendMail(email, subject, model, "forget_password");
	}
	
	private void sendMail(String email, String subject, Map<String, Object> model, String template) throws Exception {
		MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(geVelocityTemplateContent(model, template), true);
        javaMailSender.send(msg);
	}
	
	public String geVelocityTemplateContent(Map<String, Object> model, String template){
        StringBuffer content = new StringBuffer();
        try{
            content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/vmtemplates/"+template+".vm", model));
            return content.toString();
        }catch(Exception e){
            System.out.println("Exception occured while processing velocity template:"+e.getMessage());
        }
          return "";
    }
}
