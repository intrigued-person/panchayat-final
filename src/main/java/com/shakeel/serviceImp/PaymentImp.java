package com.shakeel.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shakeel.model.Payment;
import com.shakeel.repos.PaymentRepoImp;
import com.shakeel.service.PaymentService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentImp implements PaymentService {

	@Autowired
	PaymentRepoImp repo;

	@Override
	public Payment addPayment(Payment pay) {
		Payment pt = repo.save(pay);
		return pt;
	}

	@Override
	public void delPayment(int payId) {
		repo.delete(payId);
	}

	@Override
	public void updatePayment(Payment pay) {
		repo.update(pay);
	}

	@Override
	public List<Payment> getAllPayments() {
		return repo.getAllPayments();
	}

	@Override
	public Payment findById(int payId) {
		return repo.findById(payId);
	}

	@Override
	public Payment findByUserId(int userId) {
		return repo.findByUserId(userId);
	}

}
