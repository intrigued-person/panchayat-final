package com.shakeel.repos;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Birth;
import com.shakeel.model.Payment;
import com.shakeel.model.User;
import com.shakeel.repo.BirthRepo;

import jakarta.persistence.EntityManager;

@Repository
public class BirthRepoImp implements BirthRepo {

	@Autowired
	EntityManager em;

	@Override
	public void delete(int birthId) {
		Birth bt = em.find(Birth.class, birthId);
		if (bt != null) {
			em.remove(bt);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Birth> getAllBirths() {
		return em.createQuery("from Birth").getResultList();
	}

	@Override
	public void update(Birth bth) {
		em.merge(bth);
	}

	@Override
	public Birth findById(int birthId) {
		Query<Birth> q = (Query<Birth>) em.createQuery("SELECT b FROM Birth b WHERE b.birthId = :birthId", Birth.class);
		q.setParameter("birthId", birthId);
		return q.getSingleResult();
	}

	@Override
	public Birth save(String district, String mobile, String emailId, String dob, String gender, String childName,
			String fatherName, String motherName, String address, String state, String placeOfBirth,
			String hospitalName, String town, String religion, String focup, String mocup, String motherMrgYr,
			String motherBirthYr, String certificateType, String status, MultipartFile hospitalImg, Integer userId,
			String reason, Integer paymentId) {

		User reg = em.find(User.class, userId);
		Payment pay = em.find(Payment.class, paymentId);

		try {
			Birth bth = new Birth();
			bth.setDistrict(district);
			bth.setMobile(mobile);
			bth.setEmailId(emailId);
			bth.setDob(dob);
			bth.setGender(gender);
			bth.setChildName(childName);
			bth.setFatherName(fatherName);
			bth.setMotherName(motherName);
			bth.setAddress(address);
			bth.setState(state);
			bth.setPlaceOfBirth(placeOfBirth);
			bth.setHospitalName(hospitalName);
			bth.setTown(town);
			bth.setReligion(religion);
			bth.setFocup(focup);
			bth.setMocup(mocup);
			bth.setMotherMrgYr(motherMrgYr);
			bth.setMotherBirthYr(motherBirthYr);
			bth.setCertificateType(certificateType);
			bth.setStatus(status);
			bth.setHospitalImg(hospitalImg.getBytes());
			bth.setUser(reg);
			bth.setReason(reason);
			bth.setPayment(pay);
			em.persist(bth);
			return bth;

		} catch (Exception e) {
			System.out.println("Birth Not added");
			return null;
		}
	}

	@Override
	public Birth findByUserId(int userId) {
		try {
			Query<Birth> query = (Query<Birth>) em.createQuery("from Birth where user.userId = ?1", Birth.class)
					.setParameter(1, userId);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		}

	}

	public List<Birth> findApprovedBirthsByAdmin() {
		try {
			Query query = (Query) em.createQuery("SELECT b FROM Birth b WHERE b.status = 'approved'");

			List<Birth> results = query.getResultList();

			return results;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Payment> findPaymentsByUserId(int userId) {
		try {
			Query query = (Query) em.createQuery("SELECT b.payment FROM Birth b WHERE b.user.userId = :userId");
			query.setParameter("userId", userId);

			List<Payment> results = query.getResultList();
			return results;
		} catch (Exception e) {
			return null; 
		}
	}

}
