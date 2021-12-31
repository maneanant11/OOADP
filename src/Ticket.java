import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public abstract class Ticket {
	private long PNRNumber;
	private Flight flight;
	private Passenger passenger;
	private int seatNumber;
	protected double price;
	protected String status;

	public abstract void setPrice(double price);

	public Ticket(long PNRNumber, Flight flight, Passenger passenger, int seatNumber) {
		this.PNRNumber = PNRNumber;
		this.flight = flight;
		this.passenger = passenger;
		this.seatNumber = seatNumber;
	}

	public double getPrice() {
		return price;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getDepartureDateTime() {
		return flight.getDepartureDateTime();
	}

	/**
	 * 
	 * @param localDateTime
	 * @param localDateTime2
	 * <p> Calculation of time duration between date and time of departure   and   date and time of arrival
	 */
	public Duration getDurationOfJourney(LocalDateTime localDateTime, LocalDateTime localDateTime2) {
		return Duration.between(localDateTime, localDateTime2);
	}

	public void printDuration() {
		Duration duration = getDurationOfJourney(flight.getDepartureDateTime(), flight.getArrivalDateTime());
		System.out.println(duration.toMinutes());
	}

	public long getPNRNumber() {
		return PNRNumber;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public Passenger getPassenger() {
		return passenger;
	}

	public Flight getFlight() {
		return flight;
	}
}
