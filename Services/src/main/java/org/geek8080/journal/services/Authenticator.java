package org.geek8080.journal.services;

import org.geek8080.journal.entities.User;
import org.geek8080.journal.utils.Encryption;
import org.geek8080.journal.utils.FileHandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Authenticator {

	private static User USER;
	private static String OTP;

	private static Authenticator authenticator;

	private Authenticator() throws IOException {
		OTP = "" + (new Random().nextInt(899999) + 100000);

		if (FileHandler.exists(".", "user")) {
			List<String> encryptedUserInfoList = FileHandler.readFile(".", "user");
			String encryptedUserInfo = "";
			for (String text : encryptedUserInfoList) {
				encryptedUserInfo += text;
			}

			String userInfo[] =  Encryption.decryptText(encryptedUserInfo).split("\n");
			USER = new User(userInfo[0], userInfo[1]);
		}
	}

	public static Authenticator getInstance() throws IOException {
		if(authenticator == null){
			authenticator = new Authenticator();
		}
		return authenticator;
	}

	public void generateOTP(String emailID){
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("prashant202.98@gmail.com","Prince@02");
					}
				});

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailID));
			message.setSubject("Journal-Daily OTP");
			message.setText("Your OTP for password generation is: " + OTP + ". Please don't share this information for your own privacy.");
			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (MessagingException e) {throw new RuntimeException(e);}
	}

	public boolean authenticateOTP(String otp){
		return OTP.equalsIgnoreCase(otp);
	}

	public void saveUser(String userName, String password) throws IOException {
		User user = new User(userName, password);
		if (FileHandler.exists(".", "user")){
			FileHandler.deleteFile(".", "user");
		}
		FileHandler.writeFile(".", "user", Encryption.encryptText(userName + "\n" + password));
	}

	public boolean authenticateUser(String userName, String password){
		return (USER.getName().equalsIgnoreCase(userName) && USER.getPassword().equalsIgnoreCase(password));
	}

	public boolean authenticateUser(User user){
		return (USER.getName().equalsIgnoreCase(user.getName()) && USER.getPassword().equalsIgnoreCase(user.getPassword()));
	}

	public User getUSER() {
		return USER;
	}
}
