package chat.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class FileUtils {
	public final static String TXT = "txt";
	
	public static String getExtension(File f) {
		String extension = null;
		String fileName = f.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0 && (i < fileName.length() - 1)) {
			extension = fileName.substring(i+1).toLowerCase();
		}
		return extension;
	}
	
	public static String readFile(File f) {
		String data = "";
		try {
			byte[] fileBytes = Files.readAllBytes(f.toPath());
			data = new String(fileBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public static void writeFile(File f, String data) {
		try {
			Files.write(f.toPath(), data.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
