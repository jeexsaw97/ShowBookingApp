package com.project.showbooking.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AdminService {

    @Autowired
    ShowService showService;
    @Autowired
    BookingService bookingService;

    Scanner scanner = new Scanner(System.in);

    public void adminPage() throws Exception {
        System.out.println("Welcome Admin user! \nPlease enter one of the following commands \n" +
                "- Setup <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes> \n" +
                "- View <Show Number>\n" +
                "- Return (To return to user selection page)\n");
        String command = scanner.nextLine();
        String[] input = command.split("\\s+");
        if(command.startsWith("Setup")) {
            createNewShow(input);
        } else if (command.startsWith("View")) {
            viewShow(input);
        } else if (command.startsWith("Return")) {
            return;
        }
        adminPage();
    }

    public void createNewShow(String[] input) throws Exception {
        int showNumber = Integer.parseInt(input[1]);
        int numRows = Integer.parseInt(input[2]);
        int seatsPerRow = Integer.parseInt(input[3]);
        int cancellationWindowInMinutes = Integer.parseInt(input[4]);
        try {
            showService.createNewShow(showNumber, numRows, seatsPerRow, cancellationWindowInMinutes);
        } catch (Error e) {
            if (StringUtils.equals(e.getMessage(), "Max_Capacity")) {
                adminPage();
            }
        }
    }

    public void viewShow(String[] input) {
        int showNumber = Integer.parseInt(input[1]);
        showService.viewShow(showNumber);
    }
}
