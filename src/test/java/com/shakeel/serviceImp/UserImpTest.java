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

import com.shakeel.model.User;
import com.shakeel.repos.UserRepoImp;

class UserImpTest {

    @InjectMocks
    private UserImp userService;

    @Mock
    private UserRepoImp userRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        userService.addUser(user);
        verify(userRepo, times(1)).addUser(user);
    }

    @Test
    void testDelUser() {
        int userId = 1;
        userService.delUser(userId);
        verify(userRepo, times(1)).delUser(userId);
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUname("New Name");
        user.setUemail("new@example.com");
        user.setMobileNo("1234567890");

        User existingUser = new User();
        existingUser.setUserId(1);
        when(userRepo.findById(1)).thenReturn(existingUser);

        userService.updateUser(user, 1);

        verify(userRepo, times(1)).updateUser(existingUser);
        assertEquals("New Name", existingUser.getUname());
        assertEquals("new@example.com", existingUser.getUemail());
        assertEquals("1234567890", existingUser.getMobileNo());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User user = new User();
        when(userRepo.findById(1)).thenReturn(null);

        userService.updateUser(user, 1);

        verify(userRepo, never()).updateUser(any());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        User user2 = new User();
        when(userRepo.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.getAllUsers();

        assertEquals(2, users.size());
        verify(userRepo, times(1)).getAllUsers();
    }

    @Test
    void testFindById() {
        int userId = 1;
        User user = new User();
        when(userRepo.findById(userId)).thenReturn(user);

        User result = userService.findById(userId);

        assertNotNull(result);
        verify(userRepo, times(1)).findById(userId);
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        verify(userRepo, times(1)).findByEmail(email);
    }

    @Test
    void testLogin_Success() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        when(userRepo.Login(email, password)).thenReturn(user);

        User result = userService.Login(email, password);

        assertNotNull(result);
        verify(userRepo, times(1)).Login(email, password);
    }

    @Test
    void testLogin_Failure() {
        String email = "test@example.com";
        String password = "wrongPassword";
        when(userRepo.Login(email, password)).thenThrow(new RuntimeException("Login failed"));

        User result = userService.Login(email, password);

        assertNull(result);
        verify(userRepo, times(1)).Login(email, password);
    }
}
