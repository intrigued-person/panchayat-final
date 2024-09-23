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

import com.shakeel.model.Employee;
import com.shakeel.repos.EmpRepoImp;

class EmpImpTest {

    @InjectMocks
    private EmpImp empService;

    @Mock
    private EmpRepoImp empRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEmployee() {
        Employee employee = new Employee();
        empService.addEmployee(employee);
        verify(empRepo, times(1)).addEmployee(employee);
    }

    @Test
    void testGetAllEmployee() {
        Employee employee1 = new Employee();
        Employee employee2 = new Employee();
        when(empRepo.getAllEmployee()).thenReturn(Arrays.asList(employee1, employee2));

        List<Employee> employees = empService.getAllEmployee();

        assertEquals(2, employees.size());
        verify(empRepo, times(1)).getAllEmployee();
    }

    @Test
    void testUpdateEmployee() {
        Employee employee = new Employee();
        when(empRepo.updateEmployee(employee)).thenReturn(true);

        boolean result = empService.updateEmployee(employee);

        assertTrue(result);
        verify(empRepo, times(1)).updateEmployee(employee);
    }

    @Test
    void testDeleteEmployee() {
        int empId = 1;
        empService.deleteEmployee(empId);
        verify(empRepo, times(1)).deleteEmployee(empId);
    }

    @Test
    void testEmployeeLogin_Success() {
        String empName = "testUser";
        String password = "password123";
        Employee employee = new Employee();
        when(empRepo.employeeLogin(empName, password)).thenReturn(employee);

        Employee result = empService.employeeLogin(empName, password);

        assertNotNull(result);
        verify(empRepo, times(1)).employeeLogin(empName, password);
    }

    @Test
    void testEmployeeLogin_Failure() {
        String empName = "testUser";
        String password = "wrongPassword";
        when(empRepo.employeeLogin(empName, password)).thenThrow(new RuntimeException("Login failed"));

        Employee result = empService.employeeLogin(empName, password);

        assertNull(result);
        verify(empRepo, times(1)).employeeLogin(empName, password);
    }
}
