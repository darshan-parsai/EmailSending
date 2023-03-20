package com.example.demo1.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import com.google.zxing.WriterException;

public interface EmailSenderService {
    public  String sendEmail(String toEmail,String subject, String body);

	public String saveDetails(String name, String password,String mailId);

	public void getOtp(Integer id);

	public String resetPassword(Integer id, String tempPass, String newPass);

	public String sendMailWithAttachment(String toEmail, String subject, String mailBody, File file) throws MessagingException;

	public void sendQr(String toEmail, String url) throws UnsupportedEncodingException, WriterException, IOException, MessagingException;
}
