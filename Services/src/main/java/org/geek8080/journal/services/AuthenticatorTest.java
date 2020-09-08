package org.geek8080.journal.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AuthenticatorTest {
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			Authenticator authenticator = Authenticator.getInstance();
			if (authenticator.getUSER() == null){
				System.out.println("It seems this is your first time. \nEnter your e-mail id to get OTP for first use: ");
				String mail = reader.readLine().trim();
				authenticator.generateOTP(mail);
				System.out.print("Enter the OTP sent to your mail: ");
				String otp = reader.readLine().trim();
				while (!authenticator.authenticateOTP(otp)){
					System.out.println("Incorrect OTP. Exitting app");
					System.exit(0);
				}
				System.out.println("OTP verified. Enter details: ");
				System.out.print("User Name: ");
				String userName = reader.readLine().trim();
				System.out.print("Password: ");
				String password = reader.readLine().trim();
				authenticator.saveUser(userName, password);
			}else{
				System.out.print("Welcome Back, Enter details to log in;\nUser Name: ");
				String userName = reader.readLine().trim();
				System.out.print("Password: ");
				String password = reader.readLine().trim();
				int turn = 0;
				while (!authenticator.authenticateUser(userName, password)){
					if (turn>3){
						System.out.println("Too many failed attempts... Exitting app...");
						System.exit(0);
					}
					System.out.print("Oops... Incorrect password or username. Enter again to log in.\nUser Name: ");
					userName = reader.readLine().trim();
					System.out.print("Password: ");
					password = reader.readLine().trim();
					turn++;
				}
				System.out.println("Successfully Logged In...");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
