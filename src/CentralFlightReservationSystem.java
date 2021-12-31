import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;

public class CentralFlightReservationSystem {
	
	private static List<Flight> listOfFlights = new ArrayList<>();
	
	private static Map<Passenger, List<Ticket>> passengerTicket = new HashMap<>();

	static Scanner sc = new Scanner(System.in);

	/**
	 * 
	 * @param passenger 
	 * <p> If the passenger is not registered, passenger object is added to the map passengerTicket</p>
	 * <p>If passenger is already registered, Account already registered is printed
	 */
	public static void registerPassenger(Passenger passenger) {
		if (!passengerTicket.containsKey(passenger)) {
			passengerTicket.put(passenger, null);
		} else {
			System.out.println("Account already registered ");
		}
	}

	/**
	 * 
	 * @param emailId
	 * @param password
	 * @return whether user is authenticated
	 *<p>User is authenticated if user name and password is correct
	 */
	public static boolean validateUser(String emailId, String password) {
		if (passengerTicket != null) {
			Set<Passenger> passengerSet = passengerTicket.keySet();

			for (Passenger p : passengerSet) {
				if (p.getEmailID().equals(emailId) && p.getPassword().equals(password)) {
					return true;
				}
			}
		}
		System.out.println("Invalid credentials..");
		return false;
	}

	/**
	 * 
	 * @param emailId
	 * @return whether email id exists
	 */
	public static boolean emailIDExists(String emailId) {
		if (passengerTicket != null) {
			Set<Passenger> passengerSet = passengerTicket.keySet();

			for (Passenger p : passengerSet) {
				if (p.getEmailID().equals(emailId)) {
					return true;
				}
			}
		}
		return false;
	}

	
	public static void addFlights(List<Flight> flightList) {
		listOfFlights.addAll(flightList);
	}

	public static void addPassengerTicket(Map<Passenger, List<Ticket>> map) {
		passengerTicket.putAll(map);
		
	}
	
	/**
	 * @param index - the serial number of flight to be booked
	 * @param passenger 
	 * @param TicketType
	 * <p> If the ticket type is Regular ticket, accept the special services as user input.
	 * Create RegularTicket object. Increment the number of seats booked in the map of[Passenger, List of Tickets]</p>
	 * <p>If the ticket type is Tourist Ticket, accept a max of 5 tourist locations as user input.
	 * Create Tourist ticket object.  Increment the number of seats booked in the map of[Passenger, List of Tickets]</p>
	 */
	public static void bookSeat(int index, Passenger passenger, String TicketType) {
		List<Flight> availableFlights = getAvailableFlights();
		List<String> services = new ArrayList<>();
		String service;
		Ticket ticket = null;
		
		if (index < availableFlights.size()) {

			int seatNumber;
			System.out.println("enter seat number ");
			Scanner sc = new Scanner(System.in);
			seatNumber = sc.nextInt();

			if (TicketType.equals("RegularTicket")) {

				System.out.println("Choose the services ... ");

				service = sc.next();
				services.add(service);

				while (true) {
					System.out.println("want to add one more service?...true or false");
					boolean option = sc.nextBoolean();
					if (!option)
						break;

					else {
						System.out.println("Choose the services ... ");

						service = sc.next();
						services.add(service);
					}
				}

				ticket = new RegularTicket((long)(Math.random() * 1000), availableFlights.get(index), passenger, seatNumber,
						2000, services);
				ticket.setStatus("Confirmed");
				
				 int listIndex=listOfFlights.indexOf(availableFlights.get(index));
				 int noOfSeatsBooked = listOfFlights.get(listIndex).getNoOfSeatsBooked();
				listOfFlights.get(listIndex).setNoOfSeatsBooked(noOfSeatsBooked + 1);

			}

			else if (TicketType.equals("TouristTicket")) {
				String city, street, state;
				System.out.println(" Enter hotel address ");
				System.out.println("enter street ");
				street = sc.next();
				System.out.println("enter city ");
				city = sc.next();
				System.out.println("enter state ");
				state = sc.next();

				Address hotelAddress = new Address(street, city, state);

				System.out.println("enter tourist places ...........");

				List<String> touristLocationsList = new ArrayList<>();
				String location;

				int i = 0;
				while (i < TouristTicket.MAX_NO_LOCATIONS) {
					System.out.println("add tourist place ");
					location = sc.next();
					touristLocationsList.add(location);
					i++;

					if (i == TouristTicket.MAX_NO_LOCATIONS)
						break;

					System.out.println("want to add one more locations?...true or false");
					boolean option = sc.nextBoolean();
					if (!option)
						break;
				}

				ticket = new TouristTicket((long)(Math.random() * 1000), availableFlights.get(index), passenger, seatNumber,
						3000, hotelAddress, touristLocationsList);

				System.out.println("These are the added tourist locations ");
				System.out.println(((TouristTicket) ticket).getAllTouristLocations());

				ticket.setStatus("Confirmed");
				int listIndex=listOfFlights.indexOf(availableFlights.get(index));
				 int noOfSeatsBooked = listOfFlights.get(listIndex).getNoOfSeatsBooked();
				listOfFlights.get(listIndex).setNoOfSeatsBooked(noOfSeatsBooked + 1);
			}

		}

		if (ticket != null)
			storeTicketDetails(passenger, ticket);
	}

	/**
	 * 
	 * @param passenger
	 * @param ticket
	 * <p>Add the ticket to the map of [Passenger, List of Ticket]</p>
	 */
	public static void storeTicketDetails(Passenger passenger, Ticket ticket) {
		List<Ticket> list = passengerTicket.get(passenger);
		if (list != null) {
			list.add(ticket);
		}

		else {
			list = new ArrayList<>();
			list.add(ticket);
		}
		passengerTicket.put(passenger, list);
	}

	/**
	 * Print all the flights which have vacant seats
	 */
	public static void printAvailableFlights() {
		List<Flight> availableFlights = getAvailableFlights();

		System.out.println("These are available flights ");
		
		for (int i = 0; i < availableFlights.size(); i++) {
			System.out.println("serial number " + i + ", flight: " + availableFlights.get(i).getAirlineName()
					+ ", seats remaining:  " + availableFlights.get(i).getCurrentCapacity());
		}
	}

	/**
	 * 
	 * @return list of all flights with vacant seats
	 */
	public static List<Flight> getAvailableFlights() {
		
		List<Flight> availableFlights = new ArrayList<>();
	
		for (int i = 0; i < listOfFlights.size(); i++) {
			if (listOfFlights.get(i).getNoOfSeatsBooked() < listOfFlights.get(i).getCapacity()) {
				availableFlights.add(listOfFlights.get(i));
			}
		}

		return availableFlights;
	}

/**
 * 
 * @param PNRNumber
 * @param passenger
 * @return whether ticket was cancelled
 * <p> Search for the ticket with the PNR number. If the current date and time is before the departure date and time,
 * cancel the ticket
 */
	public static boolean CancelTicket(double PNRNumber, Passenger passenger) {
		int i = 0;
		boolean isCancelled = false;
		List<Ticket> TicketList = passengerTicket.get(passenger);

		for (Ticket ticket : TicketList) {
			if (ticket.getPNRNumber() == PNRNumber && LocalDateTime.now().isBefore(ticket.getDepartureDateTime())) {
				TicketList.get(i).setStatus("Cancelled");
				isCancelled = true;
				break;
			}
			i++;
		}
		return isCancelled;
	}

	public static List<Ticket> getAllTickets(Passenger passenger) {
		return passengerTicket.get(passenger);
	}

	/**
	 * 
	 * @param Passenger object
	 * @param pNRNumber
	 * <p>Search for the ticket with a specific PNR number. Prints all data of the ticket</p>
	 */
	public static void printTicketDetails(Passenger p, double pNRNumber) {
		List<Ticket> listOfTickets = getAllTickets(p);
		if (listOfTickets == null)
			System.out.println("no tickets found ");

		else {
			for (Ticket ticket : listOfTickets) {
				if (ticket.getPNRNumber() == pNRNumber && !ticket.getStatus().equals("Cancelled")) {
					System.out.println("PNR number: " + ticket.getPNRNumber());
					System.out.println("seat number: " + ticket.getSeatNumber());

					printPassengerDetails(p);
					System.out.println("Flight number: " + ticket.getFlight().getFlightNumber());
					System.out.println("Flight airline name: " + ticket.getFlight().getAirlineName());
					System.out.println("Departure Location: " + ticket.getFlight().getDepartureLocation());
					System.out.println("Destination location: " + ticket.getFlight().getDestinationLocation());
					System.out.println(
							"Departure Date and time: " + ticket.getFlight().getDepartureDateTime().getDayOfMonth()
									+ "- " + ticket.getFlight().getDepartureDateTime().getMonth() + "- "
									+ ticket.getFlight().getDepartureDateTime().getYear() + "\n");
					System.out.println("Time: " + ticket.getFlight().getDepartureDateTime().getHour() + " "
							+ ticket.getFlight().getDepartureDateTime().getMinute() + "\n");

					System.out
							.println("Arrival Date and time: " + ticket.getFlight().getArrivalDateTime().getDayOfMonth()
									+ "- " + ticket.getFlight().getArrivalDateTime().getMonth() + "- "
									+ ticket.getFlight().getArrivalDateTime().getYear() + "\n");
					System.out.println("Time: " + ticket.getFlight().getArrivalDateTime().getHour() + " "
							+ ticket.getFlight().getArrivalDateTime().getMinute() + "\n");

					System.out.println("Price: " + ticket.getPrice());
					System.out.println("Status: " + ticket.getStatus());

					System.out.println("extra details: " + "\n");

					if (ticket instanceof RegularTicket) {
						System.out.println("Services availed.. ");
						List<String> str = ((RegularTicket) ticket).getServices();
						if (str != null) {
							for (String service : str) {
								System.out.println(service);
							}
						}
					}

					if (ticket instanceof TouristTicket) {
						System.out.println("Hotel address: " + ((TouristTicket) ticket).getHotelAddress());
						System.out.println("Tourist locations..");

						List<String> locations = ((TouristTicket) ticket).getAllTouristLocations();
						for (String loc : locations) {
							System.out.println(loc);
						}
					}
				}

			}
		}

	}

	public static void printPassengerDetails(Passenger p) {
		System.out.println(" Passenger id: " + p.getId());
		System.out.println("Passenger address: " + p.getAddress());
		System.out.println("Passenger contact details: " + p.getContact() + "\n");
	}

	/**
	 * 
	 * @param PNRNumber
	 * @param p
	 * @return status of the ticket - Cancelled or Confirmed . Returns not found is ticket with PNR number is not present
	 */
	public static String checkStatus(double PNRNumber, Passenger p) {
		List<Ticket> ticket = CentralFlightReservationSystem.getAllTickets(p);
		for (Ticket t : ticket) {
			if (t.getPNRNumber() == PNRNumber) {
				return t.getStatus();
			}
		}
		return "Not found";
	}

	/**
	 * 
	 * @param PNRNumber
	 * @param p [Passenger] 
	 * <p> Prints duration of the flight </p>
	 */
	
	public static void printDuration(double PNRNumber, Passenger p) {
		List<Ticket> ticket = CentralFlightReservationSystem.getAllTickets(p);
		for (Ticket t : ticket) {
			if (t.getPNRNumber() == PNRNumber) {
				t.printDuration();
			}
		}
	}

	/**
	 * 
	 * @param pNRNumber
	 * @param  p [Passenger]
	 * <p> Prints all special services availed by the passenger</p>
	 */
	public static void printServices(double pNRNumber, Passenger p) {
		List<Ticket> ticket = CentralFlightReservationSystem.getAllTickets(p);
		for (Ticket t : ticket) {
			if (t.getPNRNumber() == pNRNumber) {
				if (t instanceof RegularTicket) {
					System.out.println(((RegularTicket) t).getServices());
					break;
				}

				else {
					System.out.println("Not a regular ticket");
				}
			}
		}

	}

	/**
	 * 
	 * @param op2
	 * @param pNRNumber
	 * @param p [Passenger]
	 * <p> option 1: add a new special service</p>
	 * <p> option 2: updating a special service</p>
	 * <p> option 3: Deleting a special service</p>
	 */
	public static void updateServices(int op2, double pNRNumber, Passenger p) {

		System.out.println("enter service");
		String service = sc.next();
		List<Ticket> ticket = CentralFlightReservationSystem.getAllTickets(p);
		for (Ticket t : ticket) {
			if (t.getPNRNumber() == pNRNumber) {
				if (t instanceof RegularTicket) {
					switch (op2) {

					case 1:
						((RegularTicket) t).addService(service);
						break;

					case 2:
						System.out.println("enter index of updation..");
						int index = sc.nextInt();
						((RegularTicket) t).setServices(index, service);
						break;

					case 3:
						((RegularTicket) t).deleteService(service);
						break;
					}
				}

				else {
					System.out.println("Not a regular ticket");
				}

			}
		}
	}

	/**
	 * 
	 * @param op2
	 * @param pNRNumber
	 * @param p [Passenger]
	 * <p> option 1: add a new tourist location</p>
	 * <p> option 2: delete a tourist location</p>
	 */
	public static void updateTouristLocation(int op2, double pNRNumber, Passenger p) {
		System.out.println("enter location");
		String location = sc.next();
		List<Ticket> ticket = CentralFlightReservationSystem.getAllTickets(p);
		for (Ticket t : ticket) {
			if (t.getPNRNumber() == pNRNumber) {
				if (t instanceof TouristTicket) {
					switch (op2) {

					case 1:
						((TouristTicket) t).addTouristLocation(location);
						break;

					case 2:
						((TouristTicket) t).removeTouristLocation(location);
						break;
					}
				}

				else {
					System.out.println("Not a tourist ticket");
				}

			}
		}

	}

	
}
