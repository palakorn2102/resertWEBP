package com.convertimage;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.github.biezhi.webp.WebpIO;

@RestController
@SpringBootApplication
public class ConvertImageApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvertImageApplication.class, args);
	}
	
	@PostMapping("/convertWEBP")
	public String convertWEBP(HttpServletResponse response,@RequestParam("image") MultipartFile multipartFile) {
		String type = multipartFile.getContentType().split("/")[0];
		if(type.equalsIgnoreCase("image")) {
			String fileName = multipartFile.getOriginalFilename();
	        String oututImage = this.getClass().getClassLoader().getResource("image").getPath()+"/"+fileName+".webp";
	        try {
	        	File fileimage = convert(multipartFile);
		        File filewebp = new File(oututImage);
	        	WebpIO.create().toWEBP(fileimage, filewebp);
	        	fileimage.delete();
	        	return "Success >> "+oututImage;
	        } catch (Exception ex) {
	            System.out.println("Error during converting image.");
	            ex.printStackTrace();
	            return "Error "+ex;
	        }
		}else {
			return "ERROR : file not image";
		}
		
	}
	
	public File convert(MultipartFile file) throws Exception {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
