package com.project.showbooking.service;

import com.project.showbooking.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Service
public class BuyerService {

    @Autowired
    BookingService bookingService;
    @Autowired
    ShowService showService;

    Scanner scanner = new Scanner(System.in);

    public void buyerPage() throws Exception {
        // Buyer flow
        System.out.println("Welcome Buyer! \nPlease enter one of the following commands \n" +
                "- Availability  <Show Number> \n" +
                "- Book  <Show Number> <Phone#> <Comma separated list of seats> \n" +
                "- Cancel  <Ticket#>  <Phone#>\n" +
                "- Return (To return to user selection page)");
        String command = scanner.nextLine();
        String[] input = command.split("\\s+");
        if (command.startsWith("Availability")) {
            getShowAvailability(input);
        } else if (command.startsWith("Book")) {
            bookShow(input);
        } else if (command.startsWith("Cancel")) {
            cancelShow(input);
        } else if (command.startsWith("Return")) {
            return;
        } else {
            throw new InvalidInputException();
        }
        buyerPage();
    }

    private void cancelShow(String[] input) {
        String ticketNumber = input[1];
        String phoneNumber = input[2];
        bookingService.cancelBooking(ticketNumber, phoneNumber);
    }

    private void getShowAvailability(String[] input) {
        int showNumber = Integer.parseInt(input[1]);
        showService.getShowAvailability(showNumber);
    }

    private void bookShow(String[] input) {
        int showNumber = Integer.parseInt(input[1]);
        String phoneNumber = input[2];
        String[] seats = input[3].split(",");
        Set<String> seatsSet = new HashSet<>(Arrays.asList(seats));
        bookingService.createBooking(showNumber,phoneNumber,seatsSet);
    }

}
