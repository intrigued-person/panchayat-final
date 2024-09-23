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

import com.shakeel.model.Payment;
import com.shakeel.repos.PaymentRepoImp;

class PaymentImpTest {

    @InjectMocks
    private PaymentImp paymentService;

    @Mock
    private PaymentRepoImp paymentRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddPayment() {
        Payment payment = new Payment();
        when(paymentRepo.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(payment);

        assertNotNull(result);
        verify(paymentRepo, times(1)).save(payment);
    }

    @Test
    void testDelPayment() {
        int payId = 1;
        paymentService.delPayment(payId);
        verify(paymentRepo, times(1)).delete(payId);
    }

//    @Test
//    void testUpdatePayment() {
//        Payment payment = new Payment();
//        payment.setPaymentId(1);
//        when(paymentRepo.update(payment)).thenReturn(null); // Assuming update returns void
//
//        paymentService.updatePayment(payment);
//
//        verify(paymentRepo, times(1)).update(payment);
//    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment();
        Payment payment2 = new Payment();
        when(paymentRepo.getAllPayments()).thenReturn(Arrays.asList(payment1, payment2));

        List<Payment> payments = paymentService.getAllPayments();

        assertEquals(2, payments.size());
        verify(paymentRepo, times(1)).getAllPayments();
    }

    @Test
    void testFindById() {
        int payId = 1;
        Payment payment = new Payment();
        when(paymentRepo.findById(payId)).thenReturn(payment);

        Payment result = paymentService.findById(payId);

        assertNotNull(result);
        verify(paymentRepo, times(1)).findById(payId);
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Payment payment = new Payment();
        when(paymentRepo.findByUserId(userId)).thenReturn(payment);

        Payment result = paymentService.findByUserId(userId);

        assertNotNull(result);
        verify(paymentRepo, times(1)).findByUserId(userId);
    }
}
