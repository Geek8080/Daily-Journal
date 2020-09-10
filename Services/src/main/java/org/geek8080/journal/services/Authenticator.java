package org.geek8080.journal.services;

import org.geek8080.journal.entities.User;
import org.geek8080.journal.utils.Encryption;
import org.geek8080.journal.utils.FileHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Authenticator {

	private static final Logger LOGGER = LogManager.getLogger(Authenticator.class);

	private static User USER;
	private static String OTP;

	private static Authenticator authenticator;

	private Authenticator() throws RuntimeException{

		OTP = "" + (new Random().nextInt(899999) + 100000);
		LOGGER.info("OTP initialised.");

		if (FileHandler.exists(".", "user")) {
			List<String> encryptedUserInfoList = null;
			try {
				LOGGER.info("Opening file: user");
				encryptedUserInfoList = FileHandler.readFile(".", "user");
			} catch (IOException ex) {
				LOGGER.fatal("Encountered and error while opening the file...", ex);
				throw new RuntimeException("Encountered and error while opening the file...", ex);
			}

			LOGGER.info("Decrypting File Content: user");
			String encryptedUserInfo = "";
			for (String text : encryptedUserInfoList) {
				encryptedUserInfo += text;
			}

			String userInfo[] =  Encryption.decryptText(encryptedUserInfo).split("\n");
			USER = new User(userInfo[0], userInfo[1]);

			LOGGER.info("User data initialised.");
		}

		LOGGER.info("This is the first time use.");
	}

	public static Authenticator getInstance() throws RuntimeException{
		if(authenticator == null){
			authenticator = new Authenticator();
		}
		return authenticator;
	}

	public void generateOTP(String emailID) throws RuntimeException{

		LOGGER.info("Setting properties for sending the mail.");

		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		LOGGER.info("Properties set successfully.");
		LOGGER.info("Getting session onject.");
		Session session = Session.getDefaultInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("prashant202.98@gmail.com","Prince@02");
					}
				});
		LOGGER.info("Session started. Constructing message.");

		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailID));
			message.setSubject("Journal-Daily OTP");
			message.setText("Your OTP for password generation is: " + OTP + ". Please don't share this information for your own privacy.");
			LOGGER.info("Message constructed successfully. Sending Message...");
			Transport.send(message);
			LOGGER.info("message sent successfully");
		} catch (MessagingException ex) {
			LOGGER.fatal("Couldn't send message...", ex);
			throw new RuntimeException("Couldn't send message...", ex);
		}
	}

	public boolean authenticateOTP(String otp){
		return OTP.equalsIgnoreCase(otp);
	}

	public void saveUser(String userName, String password) throws RuntimeException{
		User user = new User(userName, password);

		LOGGER.info("Attempting to write USER info to disk...");

		if (FileHandler.exists(".", "user")){

			LOGGER.info("User already exists. Attempting to Deleting USER");

			try {
				FileHandler.deleteFile(".", "user");
				LOGGER.info("USER info deletd successfully.");
			} catch (IOException ex) {
				LOGGER.fatal("Encountered an error while deleting the USER info.", ex);
				throw new RuntimeException("Encountered an error while deleting the USER info.", ex);
			}

		}

		try {
			LOGGER.info("Attempting to write USER info File.");
			FileHandler.writeFile(".", "user", Encryption.encryptText(userName + "\n" + password));
			LOGGER.info("USER info written to disk successfully.");
		} catch (IOException ex) {
			LOGGER.fatal("Couldn't write USER info to disk.", ex);
			throw new RuntimeException("Couldn't write USER info to disk.", ex);
		}

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
