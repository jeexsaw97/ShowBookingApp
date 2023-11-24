package com.project.showbooking.service;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import com.project.showbooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.showbooking.repository.ShowRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    ShowService showService;
    @Autowired
    ShowRepository showRepository;
    @Autowired
    BookingRepository bookingRepository;

    public void createBooking(int showNumber, String phoneNumber, Set<String> bookedSeats) {
        Show show = showRepository.getShow(showNumber);
        Map<String, Booking> bookings = show.getBookings();

        if (bookings.containsKey(phoneNumber)) {
          System.out.println("Error! Duplicate phone number");
          return;
        }

        Set<String> availableSeats = show.getAvailableSeats();
        if (availableSeats.containsAll(bookedSeats)) {
            availableSeats.removeAll(bookedSeats);
        } else {
            System.out.println("Seat numbers are invalid / unavailable!");
            return;
        }

        Random random = new Random();
        int ticketNumber = random.nextInt(10000);
        Booking booking = new Booking(ticketNumber, phoneNumber, showNumber, bookedSeats, new Date());
        show.getBookings().put(phoneNumber, booking);
        bookingRepository.addBooking(booking);
    }

    public void cancelBooking(String phoneNumber) {
        Booking booking = bookingRepository.getBooking(phoneNumber);
        Show show = showRepository.getShow(booking.getShowNumber());
        int cancellationWindowInMinutes = show.getCancellationWindowInMinutes();
        Date bookingTime = booking.getBookingTime();

        long minutesDiff = (new Date().getTime() - bookingTime.getTime()) / (60 * 1000);

        if (minutesDiff > cancellationWindowInMinutes) {
            System.out.println("Booking system timed out! You are not allowed to cancel the ticket");
            return;
        }
        show.getBookings().remove(phoneNumber);
        bookingRepository.removeBooking(phoneNumber);
    }
}
