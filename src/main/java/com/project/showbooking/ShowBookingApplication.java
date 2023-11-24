package com.project.showbooking;

import com.project.showbooking.exception.InvalidInputException;
import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
import com.project.showbooking.service.AdminService;
import com.project.showbooking.service.BuyerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.project.showbooking.service.BookingService;
import com.project.showbooking.service.ShowService;

import java.util.*;

@SpringBootApplication
@ComponentScan(basePackages = "com.project.showbooking")
public class ShowBookingApplication implements CommandLineRunner {

	@Autowired
	ShowService showService;
	@Autowired
	BookingService bookingService;
	@Autowired
	AdminService adminService;
	@Autowired
	BuyerService buyerService;
	Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		SpringApplication.run(ShowBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Please select user type \n 1 for Admin \n 2 for Buyer");
		try {
			String userType = scanner.nextLine();
			if (StringUtils.equals(userType, "1")) {
				// Admin flow
				adminService.adminPage();
			} else if (StringUtils.equals(userType, "2")) {
				buyerService.buyerPage();
			} else {
				throw new InvalidInputException();
			}
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
		} finally {
			run();
		}
	}
}
