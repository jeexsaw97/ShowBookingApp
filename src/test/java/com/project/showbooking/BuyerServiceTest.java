package com.project.showbooking;

import com.project.showbooking.exception.InvalidInputException;
import com.project.showbooking.service.BookingService;
import com.project.showbooking.service.BuyerService;
import com.project.showbooking.service.ShowService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class BuyerServiceTest {

    @InjectMocks
    private BuyerService buyerService;
    @Mock
    private ShowService showService;
    @Mock
    private BookingService bookingService;
    @Mock
    Scanner scanner = new Scanner(System.in);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBookShowSuccess() throws Exception {
        when(scanner.nextLine()).thenReturn("Book 9733 97349624 A1,A2,A3").thenReturn("Return");
        buyerService.buyerPage();
        verify(bookingService, times(1)).createBooking(anyInt(), anyString(), anySet());
    }

    @Test
    public void testGetAvailabilitySuccess() throws Exception {
        when(scanner.nextLine()).thenReturn("Availability 9733").thenReturn("Return");
        buyerService.buyerPage();
        verify(showService, times(1)).getShowAvailability(anyInt());
    }

    @Test
    public void testCancelShowSuccess() throws Exception {
        when(scanner.nextLine()).thenReturn("Cancel 1234 97349624").thenReturn("Return");
        buyerService.buyerPage();
        verify(bookingService, times(1)).cancelBooking(any(),any());
    }

    @Test (expected = InvalidInputException.class)
    public void testInvalidInputException() throws Exception {
        when(scanner.nextLine()).thenReturn("asdsadads").thenReturn("Return");
        buyerService.buyerPage();
    }

}
