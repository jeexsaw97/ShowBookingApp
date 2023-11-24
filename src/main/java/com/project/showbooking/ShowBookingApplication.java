package com.project.showbooking;

import com.project.showbooking.exception.InvalidInputException;
import com.project.showbooking.model.Booking;
import com.project.showbooking.model.Show;
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
				// ADMIN flow
				adminPage();
			} else if (StringUtils.equals(userType, "2")) {
				// Buyer flow
				System.out.println("Welcome User user! \nPlease enter one of the following commands \n" +
						"- Availability  <Show Number> \n" +
						"- Book  <Show Number> <Phone#> <Comma separated list of seats> \n" +
						"- Cancel  <Ticket#>  <Phone#>");
				String command = scanner.nextLine();
				if (command.startsWith("Availability")) {
					getShowAvailability(command);
				} else if (command.startsWith("Book")) {
					int showNumber = bookShow(command);
					run();
                    buyerPostPurchasePage(showNumber);
				}
				run();
			} else {
				throw new InvalidInputException();
			}
		} catch (InvalidInputException e) {
			System.out.println(e.getMessage());
			run();
		}
	}

	public void adminPage() throws Exception {
		System.out.println("Welcome Admin user! \nPlease enter one of the following commands \n" +
				"- Setup <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes> \n" +
				"- View <Show Number>");
		String command = scanner.nextLine();
		if(command.startsWith("Setup")) {
			createNewShow(command);
		} else if (command.startsWith("View")) {
			viewShow(command);
		}
		run();
	}

    public void buyerPostPurchasePage(int showNumber) {
        int cancellationWindowInMintues = shows.get(showNumber).getCancellationWindowInMinutes();
        long endTime = System.currentTimeMillis() + (cancellationWindowInMintues*60*1000);

        System.out.println("Would you like to cancel your booking?");
        String command = scanner.nextLine();
        if (command.startsWith("Cancel")) {
            cancelShow(command, showNumber, endTime);
        }
    }
    public void cancelShow(String input, int showNumber, long endTime) {
        String[] tokens = input.split("\\s+");
        int ticketNumber = Integer.parseInt(tokens[1]);
        String phoneNumber = tokens[2];
        bookingService.cancelBooking(showNumber, phoneNumber, endTime);
    }

	public void getShowAvailability(String input) {
		String[] tokens = input.split("\\s+");
		int showNumber = Integer.parseInt(tokens[1]);
		Show currentShow = showService.getShowAvailability(showNumber);

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

	public Integer bookShow(String input) {
		String[] tokens = input.split("\\s+");
		int showNumber = Integer.parseInt(tokens[1]);
		String phoneNumber = tokens[2];
		String[] seats = tokens[3].split(",");
		Set<String> seatsSet = new HashSet<>(Arrays.asList(seats));
		bookingService.createBooking(showNumber,phoneNumber,seatsSet);
		return showNumber;
	}

	public void createNewShow(String input) throws Exception {
		String[] tokens = input.split("\\s+");
		int showNumber = Integer.parseInt(tokens[1]);
		int numRows = Integer.parseInt(tokens[2]);
		int seatsPerRow = Integer.parseInt(tokens[3]);
		int cancellationWindowInMinutes = Integer.parseInt(tokens[4]);
		try {
			showService.createNewShow(showNumber, numRows, seatsPerRow, cancellationWindowInMinutes);
		} catch (Error e) {
			if (StringUtils.equals(e.getMessage(), "Max_Capacity")) {
				run();
			}
		}
	}

	public void viewShow(String input) {
		String[] tokens = input.split("\\s+");
		int showNumber = Integer.parseInt(tokens[1]);

		Show show = showService.viewShow(showNumber);

		if (show != null) {
			System.out.println("Show Number: " + show.getShowNumber());
			System.out.println("----------------------------");
			for (Booking booking : show.getBookings().values()) {
				System.out.println("Ticket#: " + booking.getTicketNumber());
				System.out.println("Buyer Phone#: " + booking.getPhone());
				System.out.println("Seat Numbers allocated to the buyer: " + booking.getSeatNumbers());
				System.out.println("------------");
			}
		}
	}
}
