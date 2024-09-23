package com.shakeel.repos;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Death;
import com.shakeel.model.Payment;
import com.shakeel.model.User;
import com.shakeel.repo.DeathRepo;

import jakarta.persistence.EntityManager;

@Repository
public class DeathRepoImp implements DeathRepo {

	@Autowired
	EntityManager em;

	@Override
	public void delete(int deathId) {
		Death dt = em.find(Death.class, deathId);
		if (dt != null) {
			em.remove(dt);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Death> getAllDeaths() {
		return em.createQuery("from Death").getResultList();
	}

	@Override
	public void update(Death dth) {
		em.merge(dth);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Death findById(int deathId) {
		Query<Death> q = (Query<Death>) em.createQuery("from Death where deathId = ?1");
		q.setParameter(1, deathId);
		return (Death) q.getSingleResult();

	}

	@Override
	public void save(String district, String state, String address, String mobile, String gender, String dname,
			String nominee, String nomineeName, String placeOfBirth, String hospitalName, String date, String time,
			MultipartFile deathImg, String status, int userId, int paymentId) {

		User reg = em.find(User.class, userId);
		Payment pay = em.find(Payment.class, paymentId);

		try {
			Death death = new Death(); 
			death.setDistrict(district);
			death.setState(state);
			death.setAddress(address);
			death.setMobile(mobile);
			death.setGender(gender);
			death.setDname(dname); 
			death.setNominee(nominee);
			death.setNomineeName(nomineeName);
			death.setPlaceOfBirth(placeOfBirth);
			death.setHospitalName(hospitalName);
			death.setDate(date); 
			death.setTime(time); 
			death.setDeathImg(deathImg.getBytes()); 
			death.setStatus(status);
			death.setUser(reg); 
			death.setPayment(pay); 

			em.persist(death);

		} catch (Exception e) {
			System.out.println("Death Not added");
		}
	}

	@Override
	public Death findByUserId(int userId) {
		try {
			Query<Death> query = (Query<Death>) em.createQuery("from Death where user.userId = ?1", Death.class)
					.setParameter(1, userId);
			return query.getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Death> findApprovedDeathsByAdmin() {
		try {
			Query query = (Query) em.createQuery("SELECT d FROM Death d WHERE d.status = 'approved'");

			List<Death> results = query.getResultList();

			return results;
		} catch (Exception e) {
			return null;
		}
	}

}
