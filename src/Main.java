import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) {

		boolean isValidated = false;
		Scanner sc = new Scanner(System.in);
		int option = 0;
		Passenger p = null;

		addFlights();
		addPassengerTickets();

		while (!isValidated || option == 3) {
			System.out.println("Welcome to flight reservation system. ");
			System.out.println("enter option 1 for logging in.. ");
			System.out.println("enter option 2 for new registration..");
			System.out.println("enter option 3 for exit..");

			option = sc.nextInt();
			
			if (option == 1) {
				System.out.println("Enter email ID");
				String emailID = sc.next();
				System.out.println("Enter password");
				String password = sc.next();
				isValidated = CentralFlightReservationSystem.validateUser(emailID, password);
				if (isValidated)
					System.out.println("Successfully logged in");
			} else if (option == 2) {
				System.out.println("Enter passenger details: ");
				System.out.println(" Enter name, password, phoneNumber, emailId ");
				String name = sc.next();
				String password = sc.next();
				long phoneNumber = sc.nextLong();
				String emailId = sc.next();

				boolean exists = CentralFlightReservationSystem.emailIDExists(emailId);
				if (exists) {
					System.out.println("email id already exists ");
					continue;
				}

				System.out.println("enter address details - street,city,state");
				String street = sc.next();
				String city = sc.next();
				String state = sc.next();
				Address address = new Address(street, city, state);
				p = new Passenger(password, name, phoneNumber, emailId, address);
				CentralFlightReservationSystem.registerPassenger(p);
				System.out.println("registration succesful...please login ");
			} else {
				System.exit(0);
			}
		}

		while (true) {
			System.out.println("1. print available flights ");
			System.out.println("2. Book flights ");
			System.out.println("3. show all the tickets... ");
			System.out.println("4. View passenger details..");
			System.out.println("5. Update contact and address details...");
			System.out.println("6.exit");

			option = sc.nextInt();

			if (option == 1)

			{
				CentralFlightReservationSystem.printAvailableFlights();
			}

			else if (option == 2) {
				System.out.println("enter serial number of the flight to be booked ");
				int index = sc.nextInt();

				System.out.println("Enter ticket type.. ");
				System.out.println("1] RegularTicket 2]TouristTicket ");
				String ticketType = sc.next();

				CentralFlightReservationSystem.bookSeat(index, p, ticketType);
			}

			else if (option == 3) {
				List<Ticket> tickets = CentralFlightReservationSystem.getAllTickets(p);
				if (tickets != null) {
					for (Ticket ticket : tickets) {
						if (!ticket.getStatus().equals("Cancelled"))
							System.out.println("PNR number: " + ticket.getPNRNumber());
					}
				}

				else {
					System.out.println("no tickets found");
				}

				System.out.println();
				System.out.println("1. print ticket details.. ");
				System.out.println("2. Cancel a ticket..");
				System.out.println("3. Check status of ticket..");
				System.out.println("4. Check duration of journey");
				System.out.println("5. Get the services availed by passenger: ");
				System.out.println("6. Update special services availed by passenger");
				System.out.println("7. Update tourist locations");
				System.out.println("8  .go back..");

				int op1 = 0;
				double PNRNumber = 0;
				op1 = sc.nextInt();
				if (op1 == 1) {
					System.out.println("Enter the ticket's PNR number");
					PNRNumber = sc.nextDouble();
					CentralFlightReservationSystem.printTicketDetails(p, PNRNumber);
				}

				else if (op1 == 2) {
					System.out.println("Enter the ticket's PNR number to be cancelled");
					PNRNumber = sc.nextDouble();
					boolean status = CentralFlightReservationSystem.CancelTicket(PNRNumber, p);
					if (status)
						System.out.println("Ticket with PNR number: " + PNRNumber + " successfully cancelled");
					else
						System.out.println("Ticket with PNR number: " + PNRNumber + " couldn't be cancelled");
				}

				else if (op1 == 3) {
					System.out.println("Enter the ticket's PNR number to check the status");
					PNRNumber = sc.nextDouble();
					String status = CentralFlightReservationSystem.checkStatus(PNRNumber, p);
					System.out.println("Status is : " + status);
				}

				else if (op1 == 4) {
					System.out.println("Enter PNR number");
					PNRNumber = sc.nextDouble();
					CentralFlightReservationSystem.printDuration(PNRNumber, p);
				}

				else if (op1 == 5) {
					System.out.println("Enter PNR number");
					PNRNumber = sc.nextDouble();
					CentralFlightReservationSystem.printServices(PNRNumber, p);
				}

				else if (op1 == 6) {
					System.out.println("Enter PNR number");
					PNRNumber = sc.nextDouble();

					System.out.println("Do u want to 1.add a service  2. update a service  3.delete a service?");
					int op2 = sc.nextInt();
					CentralFlightReservationSystem.updateServices(op2, PNRNumber, p);
				}

				else if (op1 == 7) {
					System.out.println("Enter PNR number");
					PNRNumber = sc.nextDouble();

					System.out.println("Do u want to 1.add a tourist location 2. remove tourist location?");
					int op2 = sc.nextInt();
					CentralFlightReservationSystem.updateTouristLocation(op2, PNRNumber, p);
				}
			}

			else if (option == 4) {
				CentralFlightReservationSystem.printPassengerDetails(p);
			}

			else if (option == 5) {
				System.out.println("Do u want to update the address? true/false");
				boolean input = sc.nextBoolean();
				if (input) {
					System.out.println("enter street,city, state");
					String street = sc.next();
					String city = sc.next();
					String state = sc.next();
					Address address = new Address(street, city, state);
					p.updateAddress(address);
				}

				System.out.println("Do u want to update the contact details? true/false");
				input = sc.nextBoolean();
				if (input) {
					System.out.println("Enter name, phone number, email ID");
					String name = sc.next();
					long phoneNumber = sc.nextLong();
					String emailId = sc.next();
					p.updateContact(name, phoneNumber, emailId);
				}

			}
			
			else {
				System.exit(0);
			}
		}
	}

	/**
	 * Creating initial data i.e. flight objects for program execution
	 */
	static void addFlights() {
		LocalDate departureDate = LocalDate.of(2021, 12, 26);
		LocalTime departureTime = LocalTime.of(11, 30);

		LocalDateTime departureDateTime = LocalDateTime.of(departureDate, departureTime);

		LocalDate arrivalDate = LocalDate.of(2021, 12, 26);
		LocalTime arrivalTime = LocalTime.of(14, 30);

		LocalDateTime arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);

		Flight flight1 = new Flight("1", "Indigo", "New Delhi", "Bangalore", departureDateTime, arrivalDateTime, 150,
				0);

		departureDate = LocalDate.of(2021, 12, 28);
		departureTime = LocalTime.of(14, 30);

		departureDateTime = LocalDateTime.of(departureDate, departureTime);

		arrivalDate = LocalDate.of(2021, 12, 28);
		arrivalTime = LocalTime.of(16, 30);

		arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
		Flight flight2 = new Flight("2", "Vistara", "New Delhi", "Bangalore", departureDateTime, arrivalDateTime, 100,
				0);

		departureDate = LocalDate.of(2021, 12, 29);
		departureTime = LocalTime.of(14, 30);

		departureDateTime = LocalDateTime.of(departureDate, departureTime);

		arrivalDate = LocalDate.of(2021, 12, 29);
		arrivalTime = LocalTime.of(16, 30);

		arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);

		Flight flight3 = new Flight("3", "Indigo", "New Delhi", "Bangalore", departureDateTime, arrivalDateTime, 50, 0);

		departureDate = LocalDate.of(2021, 12, 27);
		departureTime = LocalTime.of(15, 30);

		departureDateTime = LocalDateTime.of(departureDate, departureTime);

		arrivalDate = LocalDate.of(2021, 12, 27);
		arrivalTime = LocalTime.of(17, 00);

		arrivalDateTime = LocalDateTime.of(arrivalDate, arrivalTime);
		Flight flight4 = new Flight("4", "Air Asia", "New Delhi", "Bangalore", departureDateTime, arrivalDateTime, 150,
				0);

		departureDate = LocalDate.of(2021, 12, 27);
		departureTime = LocalTime.of(5, 00);

		departureDateTime = LocalDateTime.of(departureDate, departureTime);

		arrivalDate = LocalDate.of(2021, 12, 27);
		arrivalTime = LocalTime.of(7, 00);

		Flight flight5 = new Flight("5", "Indigo", "New Delhi", "Bangalore", departureDateTime, arrivalDateTime, 15, 0);

		List<Flight> flights = new ArrayList<>();
		flights.add(flight1);
		flights.add(flight2);
		flights.add(flight3);
		flights.add(flight4);
		flights.add(flight5);
		CentralFlightReservationSystem.addFlights(flights);

	}

	/**
	 * Creating initial objects for program execution.
	 * Creating passenger objects and Ticket objects.
	 * Adding passenger and corresponding list of tickets to the Map of [Passenger, List of Ticket]
	 */
	static void addPassengerTickets() {
		//initializing Passenger- ticket 1
		Map<Passenger, List<Ticket>> map = new HashMap<>();
		List<Ticket> ticketList1 = new ArrayList<>();
		Address address1 = new Address("street1", "Bangalore", "Karnataka");
		Passenger passenger1 = new Passenger("abc", "Mahi", 2541, "mahi@gmail.com", address1);

		Address hotelAddress1 = new Address("street3", "Chennai", "TM");

		Ticket t1 = new TouristTicket(12, CentralFlightReservationSystem.getAvailableFlights().get(1), passenger1, 2,
				2000, hotelAddress1, Arrays.asList("loc1", "loc2"));
		Ticket t2 = new RegularTicket(13, CentralFlightReservationSystem.getAvailableFlights().get(0), passenger1, 2,
				2050, Arrays.asList("service1", "service2"));
		ticketList1.add(t1);
		ticketList1.add(t2);
		map.put(passenger1, ticketList1);
		
		////initializing Passenger- ticket 2
		List<Ticket> ticketList2 = new ArrayList<>();
		Address address2 = new Address("street2", "Hyderabad", "AP");
		Passenger passenger2 = new Passenger("abcd", "Snowy", 2549, "snowy@gmail.com", address2);
		Ticket t3 = new TouristTicket(15, CentralFlightReservationSystem.getAvailableFlights().get(1), passenger1, 2,
				1000, hotelAddress1, Arrays.asList("loc5", "loc2"));
		Ticket t4 = new RegularTicket(16, CentralFlightReservationSystem.getAvailableFlights().get(0), passenger1, 2,
				20000, Arrays.asList("service3", "service2"));
		ticketList2.add(t3);
		ticketList2.add(t4);
		map.put(passenger2, ticketList2);
		CentralFlightReservationSystem.addPassengerTicket(map);
	}

}
