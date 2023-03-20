package com.example.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.example.demo1.repo.StudentRepo;
import com.example.demo1.seriviceImpl.EmailSenderServiceImpl;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class MailSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSenderApplication.class, args);
//		System.out.println("..........."+emailSenderServiceImpl);
//		sendInService();
//		ApplicationContext context=SpringApplication.run(MailSenderApplication.class, args);
//		System.out.println("Server Started.....");
//		EmailSenderServiceImpl emailSenderServiceImpl1 = context.getBean(EmailSenderServiceImpl.class);
//		System.out.println(".....+........"+emailSenderServiceImpl1);
//		emailSenderServiceImpl1.getOtp(2);
	}
//n
	
	
}
