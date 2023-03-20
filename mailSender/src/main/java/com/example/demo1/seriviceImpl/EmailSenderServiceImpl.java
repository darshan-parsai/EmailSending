package com.example.demo1.seriviceImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo1.entity.User;
import com.example.demo1.repo.StudentRepo;
import com.example.demo1.service.EmailSenderService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	StudentRepo studentRepo;

	public String sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom("darshan.trh@gmail.com");
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(body);

		javaMailSender.send(simpleMailMessage);
		return "mail send successfully ";
	}

	@Override
	public String saveDetails(String name, String password, String mailId) {
		User user = new User();
		user.setName(name);
		user.setPassword(password);
		user.setEmail(mailId);
		studentRepo.save(user);
		return "data saved successfullly";

	}

	@Override
	public void getOtp(Integer id) {
		String mailId = studentRepo.getEmailId(id);
		Random random = new Random();
		Integer tempPass = random.nextInt(999999);
		String tempPassword = String.format("%06d", tempPass);
		studentRepo.updateTempPass(tempPassword, id);
		String subject = "Otp for changing your password";
		String body = "hello your one time password for changing your permanent password is " + tempPassword
				+ " it will be valid till 30 min";
		sendEmail(mailId, subject, body);
	}

	@Override
	public String resetPassword(Integer id, String tempPass, String newPass) {
		String tempPassword = studentRepo.getTempPass(id);
		if (tempPass.equals(tempPassword)) {
			studentRepo.updatePermanentPass(newPass, id);
			return "password changed successfully";
		} else {
			return "OTP which is inserted by you is not correct";
		}

	}

	@Override
	public String sendMailWithAttachment(String toEmail, String subject, String mailBody, File file)
			throws MessagingException {
		System.out.println("in send mail with attachment ");
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom("darshan.trh@gamil.com");
			mimeMessageHelper.setTo(toEmail);
			System.out.println("to email"+toEmail);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(mailBody);

			FileSystemResource fileSystemResource = new FileSystemResource(file);
			mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
			javaMailSender.send(mimeMessage);
		} catch (Exception e) {
			e.printStackTrace();
			return "mail is not send";
		}
		return "mail send successfully";
	}

	public void generateQR(String data, String path, String charset, Map hashMap, int height, int width) throws WriterException, IOException {
                BitMatrix bitMatrix = new MultiFormatWriter().encode(new String(data.getBytes(charset),charset),BarcodeFormat.QR_CODE,width,height);
                MatrixToImageWriter.writeToFile(bitMatrix, path.substring(path.lastIndexOf('.')+1), new File(path));
  	}

	@Override
	public void sendQr(String toEmail, String url) throws WriterException, IOException, MessagingException {
		String path = "new.png";
		String charSet= "UTF-8";
		Map<EncodeHintType, ErrorCorrectionLevel> hasMap = new HashMap<>();
		hasMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		generateQR(url, path, charSet, hasMap, 200, 200);
		File file = new File("C://Users//abc//source//mailSender//"+path);
		String subject = "This is the barcode for your url";
		String mailBody = "Hi we are sending you the barcode for the url scan it just go through with that";
		String res = sendMailWithAttachment(toEmail, subject, mailBody, file);
		System.out.println("...//.///"+res);
	}
}
