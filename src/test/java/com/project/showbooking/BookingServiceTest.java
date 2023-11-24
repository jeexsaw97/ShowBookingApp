package com.project.showbooking;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import com.project.showbooking.repository.BookingRepository;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @InjectMocks
    BookingService bookingService;
    @Mock
    ShowRepository showRepository;
    @Mock
    BookingRepository bookingRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNewBookingSuccess() {
        Show show = createMockShow();
        when(showRepository.getShow(anyInt())).thenReturn(show);
        bookingService.createBooking(123,"973496244",anySet());
        verify(bookingRepository, times(1)).addBooking(any());
    }

    @Test
    public void testCreateNewBookingFailureWithDuplicateNumberFailure() {
        Show show = createMockShow();
        when(showRepository.getShow(anyInt())).thenReturn(show);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        bookingService.createBooking(123,"97349624",anySet());
        assertTrue(outContent.toString().startsWith("Error! Duplicate phone number"));
    }

    @Test
    public void cancelBookingSuccess() {
        Show show = createMockShow();
        Booking booking = createMockBooking();
        when(bookingRepository.getBooking("97349624")).thenReturn(booking);
        when(showRepository.getShow(anyInt())).thenReturn(show);
        bookingService.cancelBooking("123", "97349624");
        verify(bookingRepository, times(1)).removeBooking("97349624");
    }
    private Show createMockShow() {
        Show show = new Show(9733,5,5,1);
        Booking booking = createMockBooking();
        Map<String, Booking> bookings = new HashMap<>();
        bookings.put("97349624",booking);
        show.setBookings(bookings);
        return show;
    }

    private Booking createMockBooking() {
        Set<String> hs = new HashSet<>();
        hs.add("A1");
        hs.add("A2");
        Booking booking = new Booking(3333, "97349624", 9733, hs, new Date());
        return booking;
    }

}
