package com.shakeel.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.ImageDatas;
import com.shakeel.model.User; 
import com.shakeel.serviceImp.StorageService;
import com.shakeel.serviceImp.UserImp;
import com.shakeel.utils.ImageUtils;

@RestController
@RequestMapping("/image")
@CrossOrigin("*")
public class Work {

	@Autowired
	private StorageService service;

	@Autowired
	private UserImp userService; 

	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("userId") int userId)
			throws IOException {
		User user = userService.findById(userId); 
		String uploadImage = service.uploadImage(file, user);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		byte[] imageData = service.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
	}

	@GetMapping("/check/{userId}")
	public boolean checkAvailability(@PathVariable("userId") int userId) {
		return service.deathCertificateDoesNotExist(userId);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getImageByUserId(@PathVariable int userId) {
		Optional<ImageDatas> imageData = service.getImageByUserId(userId);

		if (imageData.isPresent()) {
			byte[] imageBytes = ImageUtils.decompressImage(imageData.get().getImageData());
			return ResponseEntity.ok().contentType(MediaType.valueOf(imageData.get().getType())).body(imageBytes);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No image found for user ID: " + userId);
		}
	}

}
