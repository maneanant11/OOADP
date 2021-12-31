import java.time.LocalDateTime;

public class Flight {
	private String flightNumber;
	private String airlineName;
	private int capacity;
	private int noOfSeatsBooked;
	private String departureLocation;
	private String destinationLocation;
	private LocalDateTime departureDateTime;
	private LocalDateTime arrivalDateTime;

	public Flight(String flightNumber, String airlineName, String departureLocation, String destinationLocation,
			LocalDateTime departureDateTime, LocalDateTime arrivalDateTime, int capacity, int noOfSeatsBooked) {
		this.flightNumber = flightNumber;
		this.airlineName = airlineName;
		this.departureLocation = departureLocation;
		this.destinationLocation = destinationLocation;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.capacity = capacity;

	}

	public LocalDateTime getDepartureDateTime() {
		return departureDateTime;
	}

	public LocalDateTime getArrivalDateTime() {
		return arrivalDateTime;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setNoOfSeatsBooked(int noOfSeatsBooked) {
		this.noOfSeatsBooked = noOfSeatsBooked;
	}

	public int getNoOfSeatsBooked() {
		return noOfSeatsBooked;
	}

	public int getCurrentCapacity() {
		return capacity - noOfSeatsBooked;
	}

	public String getDepartureLocation() {
		return departureLocation;
	}

	public String getDestinationLocation() {
		return destinationLocation;
	}
}
