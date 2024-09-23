package com.shakeel.serviceImp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Approval;
import com.shakeel.repos.ApprovalRepoImp;
import com.shakeel.service.ApprovalService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ApprovalImp implements ApprovalService {

	@Autowired
	ApprovalRepoImp repo;

	@Override
	public void delApproval(int appId) {
		repo.delete(appId);
	}

	@Override
	public void updateApproval(Approval app) {
		repo.update(app);
	}

	@Override
	public List<Approval> getAllApprovals() {
		return repo.getAllApprovals();
	}

	@Override
	public Approval findById(int appId) {
		return repo.findById(appId);
	}

	@Override
	public void addApproval(String mobileNo, String appName, String gender, String doorNo, String streetName,
			String city, String aadhar, String gst, String tradeloc, String validity, String type, String property,
			String status, MultipartFile appImage, MultipartFile addressProof, MultipartFile propertyTax,
			MultipartFile noc, int userId, int paymentId) {
		repo.save(mobileNo, appName, gender, doorNo, streetName, city, aadhar, gst, tradeloc, validity, type, property,
				status, appImage, addressProof, propertyTax, noc, userId, paymentId);

	}

	@Override
	public boolean updateApplication(int appId, String status) {
		try {
			Approval app = repo.findById(appId);
			if (app != null) {
				app.setStatus(status);
				repo.update(app);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Approval findByUserId(int userId) {

		return repo.findByUserId(userId);
	}

}
