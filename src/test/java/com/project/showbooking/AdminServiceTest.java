package com.project.showbooking;

import com.project.showbooking.exception.InvalidInputException;
import com.project.showbooking.service.AdminService;
import com.project.showbooking.service.ShowService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;
    @Mock
    private ShowService showService;
    @Mock
    Scanner scanner = new Scanner(System.in);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewShowSuccess() throws Exception {
        int showNumber = 9733;
        int numOfRows = 5;
        int seatsPerRow = 5;
        int cancellationWindowInMinutes = 1;
        when(scanner.nextLine()).thenReturn("Setup 9733 5 5 1").thenReturn("Return");
        adminService.adminPage();
        verify(showService, times(1)).createNewShow(showNumber,numOfRows,seatsPerRow,cancellationWindowInMinutes);
    }

    @Test (expected = InvalidInputException.class)
    public void testCreateNewShowFailureWithInvalidInputFailure() throws Exception {
        when(scanner.nextLine()).thenReturn("asdadsadas");
        adminService.adminPage();
    }

    @Test
    public void testCreateNewShowFailureWithMoreThanMaxCapacityFailure() throws Exception {
        int showNumber = 9733;
        int numOfRows = 77;
        int seatsPerRow = 77;
        int cancellationWindowInMinutes = 1;
        when(scanner.nextLine()).thenReturn("Setup 9733 77 77 1").thenReturn("Return");
        Mockito.doThrow(new Error("Max_Capacity")).when(showService).createNewShow(showNumber,numOfRows,seatsPerRow,cancellationWindowInMinutes);
        adminService.adminPage();
        verify(showService, times(1)).createNewShow(showNumber,numOfRows,seatsPerRow,cancellationWindowInMinutes);
    }

    @Test
    public void testViewAvailabilitySuccess() throws Exception {
        int showNumber = 9733;
        when(scanner.nextLine()).thenReturn("View 9733").thenReturn("Return");
        adminService.adminPage();
        verify(showService, times(1)).viewShow(showNumber);
    }

}
