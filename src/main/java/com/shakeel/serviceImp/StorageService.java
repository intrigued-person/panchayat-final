package com.shakeel.serviceImp;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.ImageDatas;
import com.shakeel.model.User;
import com.shakeel.repo.StorageRepository;
import com.shakeel.utils.ImageUtils;

@Service
public class StorageService {

	@Autowired
	private StorageRepository repository;

	public String uploadImage(MultipartFile file, User user) throws IOException {
		ImageDatas imageData = new ImageDatas();
		imageData.setName(file.getOriginalFilename());
		imageData.setType(file.getContentType());
		imageData.setImageData(ImageUtils.compressImage(file.getBytes()));
		imageData.setUser(user); 

		repository.save(imageData);

		return "File uploaded successfully: " + file.getOriginalFilename();
	}

	public byte[] downloadImage(String fileName) {
		Optional<ImageDatas> dbImageData = repository.findByName(fileName);
		return dbImageData.map(imageData -> ImageUtils.decompressImage(imageData.getImageData())).orElse(null);
	}

	public boolean deathCertificateDoesNotExist(int userId) {
		return repository.findDeathCertificate(userId).isEmpty();
	}

	public Optional<ImageDatas> getImageByUserId(int userId) {
		return repository.findImageByUserId(userId);
	}

}
