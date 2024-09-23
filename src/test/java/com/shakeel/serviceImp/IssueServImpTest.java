package com.shakeel.serviceImp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.shakeel.model.Issues;
import com.shakeel.repos.IssueRepoImp;

class IssueServImpTest {

    @InjectMocks
    private IssueServImp issueService;

    @Mock
    private IssueRepoImp issueRepo;

    @Mock
    private MultipartFile proofImage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddIssues() throws IOException {
        issueService.addIssues("Noise Complaint", "Description", "Location", "Open", proofImage, 1);
        verify(issueRepo, times(1)).addIssues("Noise Complaint", "Description", "Location", "Open", proofImage, 1);
    }

    @Test
    void testDelIssue() {
        int issueId = 1;
        issueService.delIssue(issueId);
        verify(issueRepo, times(1)).delete(issueId);
    }

    @Test
    void testGetAllIssues() {
        Issues issue1 = new Issues();
        Issues issue2 = new Issues();
        when(issueRepo.getAllIssues()).thenReturn(Arrays.asList(issue1, issue2));

        List<Issues> issues = issueService.getAllIssues();

        assertEquals(2, issues.size());
        verify(issueRepo, times(1)).getAllIssues();
    }

    @Test
    void testFindById() {
        int issueId = 1;
        Issues issue = new Issues();
        when(issueRepo.findById(issueId)).thenReturn(issue);

        Issues result = issueService.findById(issueId);

        assertNotNull(result);
        verify(issueRepo, times(1)).findById(issueId);
    }

    @Test
    void testUpdateStatus_Success() {
        int issueId = 1;
        String newStatus = "Resolved";
        Issues issue = new Issues();
        issue.setIssueId(issueId);
        when(issueRepo.findById(issueId)).thenReturn(issue);

        boolean result = issueService.update(issueId, newStatus);

        assertTrue(result);
        assertEquals(newStatus, issue.getStatus());
        verify(issueRepo, times(1)).update(issue);
    }

    @Test
    void testUpdateStatus_NotFound() {
        int issueId = 2;
        String newStatus = "Resolved";
        when(issueRepo.findById(issueId)).thenReturn(null);

        boolean result = issueService.update(issueId, newStatus);

        assertFalse(result);
        verify(issueRepo, times(1)).findById(issueId);
        verify(issueRepo, never()).update(any());
    }

    @Test
    void testFindByUserId() {
        int userId = 1;
        Issues issue1 = new Issues();
        Issues issue2 = new Issues();
        when(issueRepo.findByUserId(userId)).thenReturn(Arrays.asList(issue1, issue2));

        List<Issues> issues = issueService.findByUserId(userId);

        assertEquals(2, issues.size());
        verify(issueRepo, times(1)).findByUserId(userId);
    }
}
