package com.project.showbooking;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import com.project.showbooking.repository.ShowRepository;
import com.project.showbooking.service.ShowService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ShowServiceTest {

    @InjectMocks
    private ShowService showService;

    @Mock
    private ShowRepository showRepository;

    @Mock
    Scanner scanner = new Scanner(System.in);

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testViewShowSuccess() {
        when(showRepository.getShow(anyInt())).thenReturn(createMockShow());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        showService.viewShow(anyInt());
        assertTrue(outContent.toString().contains("Seat Numbers allocated to the buyer"));
    }

    @Test
    public void testGetShowAvailabilitySuccess() {
        when(showRepository.getShow(anyInt())).thenReturn(createMockShow());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        showService.getShowAvailability(anyInt());
        assertTrue(outContent.toString().contains("A1 A2 A3 A4 A5 "));
    }

    @Test
    public void testCreateNewShowSuccess() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        showService.createNewShow(9733,5,5,1);
        assertTrue(outContent.toString().contains("added successfully!"));
        verify(showRepository, times(1)).addShow(any());
    }

    @Test (expected = Error.class)
    public void testCreateNewShowFailure() {
        showService.createNewShow(9733,99,99,1);
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
