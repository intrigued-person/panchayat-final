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

import com.shakeel.model.Tax;
import com.shakeel.repos.TaxRepoImp;

class TaxImpTest {

    @InjectMocks
    private TaxImp taxService;

    @Mock
    private TaxRepoImp taxRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTax() {
        Tax tax = new Tax();
        taxService.addTax(tax);
        verify(taxRepo, times(1)).save(tax);
    }

    @Test
    void testDelTax() {
        int taxId = 1;
        taxService.delTax(taxId);
        verify(taxRepo, times(1)).delete(taxId);
    }

    @Test
    void testUpdateTax() {
        Tax tax = new Tax();
        tax.setId(1);
        taxService.updateTax(tax);
        verify(taxRepo, times(1)).update(tax);
    }

    @Test
    void testGetAllTaxes() {
        Tax tax1 = new Tax();
        Tax tax2 = new Tax();
        when(taxRepo.getAllTaxes()).thenReturn(Arrays.asList(tax1, tax2));

        List<Tax> taxes = taxService.getAllTaxes();

        assertEquals(2, taxes.size());
        verify(taxRepo, times(1)).getAllTaxes();
    }

    @Test
    void testFindById() {
        int taxId = 1;
        Tax tax = new Tax();
        when(taxRepo.findById(taxId)).thenReturn(tax);

        Tax result = taxService.findById(taxId);

        assertNotNull(result);
        verify(taxRepo, times(1)).findById(taxId);
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Tax tax = new Tax();
        when(taxRepo.findByUserId(userId)).thenReturn(tax);

        Tax result = taxService.findByUserId(userId);

        assertNotNull(result);
        verify(taxRepo, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdateTaxStatus_Success() {
        int taxId = 1;
        String newStatus = "Paid";
        Tax tax = new Tax();
        tax.setId(taxId);
        when(taxRepo.findById(taxId)).thenReturn(tax);

        boolean result = taxService.updateTax(taxId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, tax.getStatus());
        verify(taxRepo, times(1)).update(tax);
    }

    @Test
    void testUpdateTaxStatus_NotFound() {
        int taxId = 2;
        String newStatus = "Paid";
        when(taxRepo.findById(taxId)).thenReturn(null);

        boolean result = taxService.updateTax(taxId, newStatus);

        assertFalse(result);
        verify(taxRepo, times(1)).findById(taxId);
        verify(taxRepo, never()).update(any());
    }
}
