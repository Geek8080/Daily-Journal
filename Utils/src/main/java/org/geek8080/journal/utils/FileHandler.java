package org.geek8080.journal.utils;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileHandler {

	public static boolean exists(String location, String fileName){
		Path filePath = Path.of(location + "/" + fileName);

		return (Files.exists(filePath) && !Files.isDirectory(filePath));
	}

	public static void writeFile(String location, String fileName, String text) throws IOException {
		OutputStream out = Files.newOutputStream(Paths.get(location + "/" + fileName), StandardOpenOption.CREATE_NEW, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		out.write(text.getBytes());
		out.close();
	}

	public static List<String> readFile(String location, String fileName) throws IOException {
		Path filePath = Path.of(location + "/" + fileName);
		return Files.readAllLines(filePath);
	}

	public static boolean deleteFile(String location, String fileName) throws IOException {
		Path filePath = Path.of(location + "/" + fileName);
		return Files.deleteIfExists(filePath);
	}
}
