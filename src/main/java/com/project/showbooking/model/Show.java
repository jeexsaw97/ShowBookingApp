package com.project.showbooking.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Show {

    private Integer showNumber;
    private int numRows;
    private int seatsPerRow;
    private Set<String> availableSeats;
    private Map<String, Booking> bookings;
    private int cancellationWindowInMinutes;

    public Show(int showNumber, int numRows, int seatsPerRow, int cancellationWindowInMinutes) {
        this.showNumber = showNumber;
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
        this.availableSeats = generateAvailableSeats();
        this.bookings = new HashMap<>();
    }

    private Set<String> generateAvailableSeats() {
        Set<String> seats = new HashSet<>();
        for (int row = 1; row <= numRows; row++) {
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                char seatLetter = (char) ('A' + seatNum - 1);
                seats.add(String.format("%c%d", seatLetter, row));
            }
        }
        return seats;
    }

    public Integer getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(Integer showNumber) {
        this.showNumber = showNumber;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public int getCancellationWindowInMinutes() {
        return cancellationWindowInMinutes;
    }

    public void setCancellationWindowInMinutes(int cancellationWindowInMinutes) {
        this.cancellationWindowInMinutes = cancellationWindowInMinutes;
    }

    public Set<String> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Set<String> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Map<String, Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Map<String, Booking> bookings) {
        this.bookings = bookings;
    }
}
