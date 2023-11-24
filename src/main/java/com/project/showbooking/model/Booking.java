package com.project.showbooking.model;

import java.util.Set;

public class Booking {

    private int ticketNumber;
    private String phone;
    private int showNumber;
    private Set<String> seatNumbers;

    public Booking(int ticketNumber, String phone, int showNumber, Set<String> seatNumbers) {
        this.ticketNumber = ticketNumber;
        this.phone = phone;
        this.showNumber = showNumber;
        this.seatNumbers = seatNumbers;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(int showNumber) {
        this.showNumber = showNumber;
    }

    public Set<String> getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(Set<String> seatNumbers) {
        this.seatNumbers = seatNumbers;
    }
}
