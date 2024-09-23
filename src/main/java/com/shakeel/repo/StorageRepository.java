package com.shakeel.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shakeel.model.ImageDatas;

public interface StorageRepository extends JpaRepository<ImageDatas, Long> {

	Optional<ImageDatas> findByName(String fileName);

	@Query("SELECT i FROM ImageDatas i WHERE i.user.id = :userId AND i.name = 'death-certificate.jpeg'")
	Optional<ImageDatas> findDeathCertificate(@Param("userId") int userId);

	@Query("SELECT i FROM ImageDatas i WHERE i.user.id = :userId")
	Optional<ImageDatas> findImageByUserId(@Param("userId") int userId);

}
