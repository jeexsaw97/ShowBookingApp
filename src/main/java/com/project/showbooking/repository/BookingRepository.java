package com.project.showbooking.repository;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BookingRepository {

    private Map<String, Booking> bookings = new HashMap<>();

    public void addBooking(Booking booking) {
        bookings.put(booking.getPhone(), booking);
    }

    public Booking getBooking(String phoneNumber) {
        return bookings.get(phoneNumber);
    }

    public void removeBooking(String phoneNumber) {
        bookings.remove(phoneNumber);
    }
}
