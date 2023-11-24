package com.project.showbooking.service;

import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.showbooking.repository.ShowRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ShowService {

    @Autowired
    ShowRepository showRepository;

    public void createNewShow(int showNumber, int rows, int seatsPerRow, int cancellationWindowInMinutes) {
        if(rows > 26 || seatsPerRow > 10) {
            System.out.println("No of seats exceeded capacity! Please try again");
            throw new Error("Max_Capacity");
        }
        Show newShow = new Show(showNumber,rows,seatsPerRow,cancellationWindowInMinutes);
        showRepository.addShow(newShow);
        System.out.println("Show number " + showNumber + " added successfully!\n");
    }

    public void getShowAvailability(int showNumber) {
        Show currentShow = showRepository.getShow(showNumber);
        Set<String> set = currentShow.getAvailableSeats();
        List<String> sortedSeats = new ArrayList<>(set);
        Collections.sort(sortedSeats);

        int counter = 1;
        for(String seat : sortedSeats) {
            if(counter > currentShow.getSeatsPerRow()) {
                System.out.println();
                counter = 1;
            }
            System.out.print(seat + " ");
            counter++;
        }
        System.out.println();
    }

    public void viewShow(int showNumber) {
        Show show =  showRepository.getShow(showNumber);
        if (show != null) {
            System.out.println("Show Number: " + show.getShowNumber());
            System.out.println("----------------------------");
            for (Booking booking : show.getBookings().values()) {
                System.out.println("Ticket#: " + booking.getTicketNumber());
                System.out.println("Buyer Phone#: " + booking.getPhone());
                System.out.println("Seat Numbers allocated to the buyer: " + booking.getSeatNumbers());
                System.out.println("------------\n");
            }
        }
    }
}
