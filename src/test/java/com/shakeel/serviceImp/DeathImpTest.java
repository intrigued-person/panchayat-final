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

import com.shakeel.model.Death;
import com.shakeel.repos.DeathRepoImp;

class DeathImpTest {

    @InjectMocks
    private DeathImp deathService;

    @Mock
    private DeathRepoImp deathRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDeaths() {
        Death death1 = new Death();
        Death death2 = new Death();
        when(deathRepo.getAllDeaths()).thenReturn(Arrays.asList(death1, death2));

        List<Death> deaths = deathService.getAllDeaths();

        assertEquals(2, deaths.size());
        verify(deathRepo, times(1)).getAllDeaths();
    }

    @Test
    void testFindById() {
        int deathId = 1;
        Death death = new Death();
        when(deathRepo.findById(deathId)).thenReturn(death);

        Death result = deathService.findById(deathId);

        assertNotNull(result);
        verify(deathRepo, times(1)).findById(deathId);
    }

    @Test
    void testAddDeath() {
        // Arrange
        String district = "District";
        String state = "State";
        String address = "Address";
        String mobile = "1234567890";
        String gender = "Male";
        String dname = "Deceased Name";
        String nominee = "Nominee";
        String nomineeName = "Nominee Name";
        String placeOfBirth = "Hospital";
        String hospitalName = "Hospital Name";
        String date = "2024-01-01";
        String time = "10:00 AM";
        MultipartFile deathImg = mock(MultipartFile.class);
        String status = "Pending";
        int userId = 1;
        int paymentId = 1;

        // Act
        deathService.addDeath(district, state, address, mobile, gender, dname, nominee, nomineeName,
                placeOfBirth, hospitalName, date, time, deathImg, status, userId, paymentId);

        // Assert
        verify(deathRepo, times(1)).save(district, state, address, mobile, gender, dname, nominee, nomineeName,
                placeOfBirth, hospitalName, date, time, deathImg, status, userId, paymentId);
    }

    @Test
    void testUpdate() {
        int deathId = 1;
        String newStatus = "Approved";
        Death death = new Death();
        death.setDeathId(deathId);
        when(deathRepo.findById(deathId)).thenReturn(death);

        boolean result = deathService.update(deathId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, death.getStatus());
        verify(deathRepo, times(1)).update(death);
    }

    @Test
    void testUpdate_NotFound() {
        int deathId = 2;
        String newStatus = "Rejected";
        when(deathRepo.findById(deathId)).thenReturn(null);

        boolean result = deathService.update(deathId, newStatus);

        assertFalse(result);
        verify(deathRepo, times(1)).findById(deathId);
        verify(deathRepo, never()).update(any());
    }

    @Test
    void testDelDeath() {
        int deathId = 1;
        deathService.delDeath(deathId);
        verify(deathRepo, times(1)).delete(deathId);
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Death death = new Death();
        when(deathRepo.findByUserId(userId)).thenReturn(death);

        Death result = deathService.findByUserId(userId);

        assertNotNull(result);
        verify(deathRepo, times(1)).findByUserId(userId);
    }

    @Test
    void testFindApprovedDeathsByAdmin() {
        Death death1 = new Death();
        Death death2 = new Death();
        when(deathRepo.findApprovedDeathsByAdmin()).thenReturn(Arrays.asList(death1, death2));

        List<Death> approvedDeaths = deathService.findApprovedDeathsByAdmin();

        assertEquals(2, approvedDeaths.size());
        verify(deathRepo, times(1)).findApprovedDeathsByAdmin();
    }
}
