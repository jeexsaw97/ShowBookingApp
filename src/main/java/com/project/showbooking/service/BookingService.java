package com.project.showbooking.service;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.showbooking.repository.ShowRepository;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    ShowService showservice;
    @Autowired
    ShowRepository showRepository;
    public void createBooking(int showNumber, String phoneNumber, Set<String> bookedSeats) {
        Show show = showservice.getShowAvailability(showNumber);
        Map<String, Booking> bookings = show.getBookings();
        if (bookings.containsKey(phoneNumber)) {
          System.out.println("Error! Duplicate phone number");
          return;
        }
        Random random = new Random();
        int ticketNumber = random.nextInt(10000);
        Booking booking = new Booking(ticketNumber, phoneNumber, showNumber, bookedSeats);
        Set<String> availableSeats = show.getAvailableSeats();
        if (availableSeats.containsAll(bookedSeats)) {
            availableSeats.removeAll(bookedSeats);
        } else {
            System.out.println("Seat numbers are invalid / unavailable!");
            return;
        }
        show.getBookings().put(phoneNumber, booking);
    }

    public void cancelBooking(int showNumber, String phone, long endTime) {
        Show show = showRepository.getShow(showNumber);
        Map<String, Booking> bookings = show.getBookings();
        System.out.println("startTime = " + new Timestamp(System.currentTimeMillis()) +  " endTime = " + new Timestamp(endTime));
        if (System.currentTimeMillis() > endTime) {
            System.out.println("Booking system timed out! You are not allowed to cancel the ticket");
            return;
        }

        // Check phone
        bookings.remove(phone);

    }
}
