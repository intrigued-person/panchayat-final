package com.shakeel.serviceImp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Birth;
import com.shakeel.model.Payment;
import com.shakeel.repos.BirthRepoImp;

class BirthImpTest {

    @InjectMocks
    private BirthImp birthService;

    @Mock
    private BirthRepoImp birthRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBirths() {
        Birth birth1 = new Birth();
        Birth birth2 = new Birth();
        when(birthRepo.getAllBirths()).thenReturn(Arrays.asList(birth1, birth2));

        List<Birth> births = birthService.getAllBirths();

        assertEquals(2, births.size());
        verify(birthRepo, times(1)).getAllBirths();
    }

    @Test
    void testFindById() {
        int birthId = 1;
        Birth birth = new Birth();
        when(birthRepo.findById(birthId)).thenReturn(birth);

        Birth result = birthService.findById(birthId);

        assertNotNull(result);
        verify(birthRepo, times(1)).findById(birthId);
    }

    @Test
    void testAddBirth() {
        // Arrange
        String district = "District";
        String mobile = "1234567890";
        String emailId = "test@example.com";
        String dob = "2024-01-01";
        String gender = "Male";
        String childName = "Child Name";
        String fatherName = "Father Name";
        String motherName = "Mother Name";
        String address = "Address";
        String state = "State";
        String placeOfBirth = "Hospital";
        String hospitalName = "Hospital Name";
        String town = "Town";
        String religion = "Religion";
        String focup = "Focup";
        String mocup = "Mocup";
        String motherMrgYr = "2000";
        String motherBirthYr = "1975";
        String certificateType = "Type";
        String status = "Pending";
        MultipartFile hospitalImg = mock(MultipartFile.class);
        Integer userId = 1;
        String reason = "Reason";
        Integer paymentId = 1;

        Birth savedBirth = new Birth(); // Assume a Birth object is returned
        when(birthRepo.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(savedBirth);

        // Act
        Birth result = birthService.addBirth(district, mobile, emailId, dob, gender, childName, fatherName,
                motherName, address, state, placeOfBirth, hospitalName, town, religion, focup, mocup,
                motherMrgYr, motherBirthYr, certificateType, status, hospitalImg, userId, reason, paymentId);

        // Assert
        assertNotNull(result);
        verify(birthRepo, times(1)).save(any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(),
                any(), any(), any());
    }

    @Test
    void testUpdate() {
        int birthId = 1;
        String newStatus = "Approved";
        Birth birth = new Birth();
        birth.setBirthId(birthId);
        when(birthRepo.findById(birthId)).thenReturn(birth);

        boolean result = birthService.update(birthId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, birth.getStatus());
        verify(birthRepo, times(1)).update(birth);
    }

    @Test
    void testUpdate_NotFound() {
        int birthId = 2;
        String newStatus = "Rejected";
        when(birthRepo.findById(birthId)).thenReturn(null);

        boolean result = birthService.update(birthId, newStatus);

        assertFalse(result);
        verify(birthRepo, times(1)).findById(birthId);
        verify(birthRepo, never()).update(any());
    }

    @Test
    void testDelBirth() {
        int birthId = 1;
        birthService.delBirth(birthId);
        verify(birthRepo, times(1)).delete(birthId);
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Birth birth = new Birth();
        when(birthRepo.findByUserId(userId)).thenReturn(birth);

        Birth result = birthService.findByUserId(userId);

        assertNotNull(result);
        verify(birthRepo, times(1)).findByUserId(userId);
    }

    @Test
    void testFindApprovedBirthsByAdmin() {
        Birth birth1 = new Birth();
        Birth birth2 = new Birth();
        when(birthRepo.findApprovedBirthsByAdmin()).thenReturn(Arrays.asList(birth1, birth2));

        List<Birth> approvedBirths = birthService.findApprovedBirthsByAdmin();

        assertEquals(2, approvedBirths.size());
        verify(birthRepo, times(1)).findApprovedBirthsByAdmin();
    }

    @Test
    void testGetPaymentsByUserId() {
        int userId = 1;
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        when(birthRepo.findPaymentsByUserId(userId)).thenReturn(Arrays.asList(payment1, payment2));

        List<Payment> payments = birthService.getPaymentsByUserId(userId);

        assertEquals(2, payments.size());
        verify(birthRepo, times(1)).findPaymentsByUserId(userId);
    }
}
