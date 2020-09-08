package org.geek8080.journal.utils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class FileEncryptionTest {
	public static void main(String[] args) throws IOException {
		String location = ".";
		String filename = "hello.txt";
		System.out.println("The file at location(" + Path.of(location + "/" + filename) + ") exists:" +FileHandler.exists(location, filename));
		//FileHandler.writeFile(location, filename, Encryption.encryptText("prashant\n1024"));
		List<String> stringsInFile = FileHandler.readFile(location, filename);
		System.out.println("The file contents are: ");
		for(String string: stringsInFile){
			System.out.println(string + Encryption.decryptText(string));
		}
		FileHandler.deleteFile(location, filename);
	}
}
