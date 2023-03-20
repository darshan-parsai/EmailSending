package com.example.demo1.Controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.service.EmailSenderService;
import com.google.zxing.WriterException;

@RestController
@RequestMapping("/mailSenderController")
public class EmailSenderController {
	@Autowired
	EmailSenderService emailSenderService;

	@GetMapping("/index")
	public String getIndex() {
		return "indedx";
	}

	@PostMapping("/sendMail")
	public String sendMail(@RequestParam String toEmail, String subject, String mailBody) {
		return emailSenderService.sendEmail(toEmail, subject, mailBody);

	}
	@PostMapping("/sendMail/attachment")
	public String sendMailWithAttachment(@RequestParam String toEmail, String subject, String mailBody, File file) throws MessagingException {
		return emailSenderService.sendMailWithAttachment(toEmail,subject,mailBody,file);
	}

	@PostMapping("/index")
	public String saveDetails(@RequestParam String name,@RequestParam String email,@RequestParam String password) {
        System.out.println("inside the save details method ");
		emailSenderService.saveDetails(name, password, email);
		 return "indedx";
	}

	@PostMapping("/getOtp")
	public void getOtp(@RequestParam Integer id) {
		emailSenderService.getOtp(id);
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam Integer id, String tempPass, String newPass) {
		return emailSenderService.resetPassword(id, tempPass, newPass);
	}
	@PostMapping("/sendQR")
	public void sendQR(@RequestParam String toEmail, String url) throws WriterException, IOException, MessagingException {
		emailSenderService.sendQr(toEmail,url);
	}
}
