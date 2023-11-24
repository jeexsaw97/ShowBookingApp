package com.project.showbooking.service;

import com.project.showbooking.model.Show;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.showbooking.repository.ShowRepository;

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
        System.out.println("Show number " + showNumber + " added successfully!");
    }

    public Show getShowAvailability(int showNumber) {
        return showRepository.getShow(showNumber);
    }

    public Show viewShow(int showNumber) {
        return showRepository.getShow(showNumber);
    }

}
