package com.shakeel.serviceImp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Approval;
import com.shakeel.repos.ApprovalRepoImp;

class ApprovalImpTest {

    @InjectMocks
    private ApprovalImp approvalService;

    @Mock
    private ApprovalRepoImp approvalRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllApprovals() {
        Approval approval1 = new Approval();
        Approval approval2 = new Approval();
        when(approvalRepo.getAllApprovals()).thenReturn(Arrays.asList(approval1, approval2));

        List<Approval> approvals = approvalService.getAllApprovals();

        assertEquals(2, approvals.size());
        verify(approvalRepo, times(1)).getAllApprovals();
    }

    @Test
    void testFindById() {
        int appId = 1;
        Approval approval = new Approval();
        when(approvalRepo.findById(appId)).thenReturn(approval);

        Approval result = approvalService.findById(appId);

        assertNotNull(result);
        verify(approvalRepo, times(1)).findById(appId);
    }

    @Test
    void testAddApproval() {
        // Arrange
        String mobileNo = "1234567890";
        String appName = "Test Approval";
        String gender = "Male";
        String doorNo = "123";
        String streetName = "Main St";
        String city = "City";
        String aadhar = "1234-5678-9101";
        String gst = "GST123";
        String tradeloc = "Trade Location";
        String validity = "2024-12-31";
        String type = "Type";
        String property = "Property";
        String status = "Pending";
        MultipartFile appImage = mock(MultipartFile.class);
        MultipartFile addressProof = mock(MultipartFile.class);
        MultipartFile propertyTax = mock(MultipartFile.class);
        MultipartFile noc = mock(MultipartFile.class);
        int userId = 1;
        int paymentId = 1;

        // Act
        approvalService.addApproval(mobileNo, appName, gender, doorNo, streetName, city, aadhar, gst, tradeloc, validity, type, property, status, appImage, addressProof, propertyTax, noc, userId, paymentId);

        // Assert
        verify(approvalRepo, times(1)).save(mobileNo, appName, gender, doorNo, streetName, city, aadhar, gst, tradeloc, validity, type, property, status, appImage, addressProof, propertyTax, noc, userId, paymentId);
    }

    @Test
    void testUpdateApplication_Success() {
        int appId = 1;
        String newStatus = "Approved";
        Approval approval = new Approval();
        approval.setAppId(appId);
        when(approvalRepo.findById(appId)).thenReturn(approval);

        boolean result = approvalService.updateApplication(appId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, approval.getStatus());
        verify(approvalRepo, times(1)).update(approval);
    }

    @Test
    void testUpdateApplication_NotFound() {
        int appId = 2;
        String newStatus = "Rejected";
        when(approvalRepo.findById(appId)).thenReturn(null);

        boolean result = approvalService.updateApplication(appId, newStatus);

        assertFalse(result);
        verify(approvalRepo, times(1)).findById(appId);
        verify(approvalRepo, never()).update(any());
    }

    @Test
    void testDelApproval() {
        int appId = 1;
        approvalService.delApproval(appId);
        verify(approvalRepo, times(1)).delete(appId);
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Approval approval = new Approval();
        when(approvalRepo.findByUserId(userId)).thenReturn(approval);

        Approval result = approvalService.findByUserId(userId);

        assertNotNull(result);
        verify(approvalRepo, times(1)).findByUserId(userId);
    }
}
