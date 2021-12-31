import java.time.ZonedDateTime;
import java.util.Iterator;
import java.util.List;

public class TouristTicket extends Ticket {

	public static final int MAX_NO_LOCATIONS = 5;
	private Address hotelAddress;
	private List<String> touristLocationsList;

	public TouristTicket(long PNRNumber, Flight flight, Passenger passenger, int seatNumber, int price,
			Address hotelAddress, List<String> touristLocationsList) {
		super(PNRNumber, flight, passenger, seatNumber);
		setPrice(price);
		setStatus("Cancelled");
		this.hotelAddress = hotelAddress;
		this.touristLocationsList = touristLocationsList;
	}

	@Override
	public void setPrice(double price) {
		this.price = price;
	}

	public Address getHotelAddress() {
		return hotelAddress;
	}

	public void setHotelAddress(Address hotelAddress) {
		this.hotelAddress = hotelAddress;
	}

	public List<String> getAllTouristLocations() {
		return touristLocationsList;
	}

	/**
	 * 
	 * @param touristLocation
	 * <p> add a tourist location if the number of current locations < 5
	 */
	public void addTouristLocation(String touristLocation) {
		if (touristLocationsList.size() < MAX_NO_LOCATIONS) {
			touristLocationsList.add(touristLocation);
		}

		else {
			System.out.println("max no of tourist spots already added");
		}
	}

	/**
	 * 
	 * @param touristLocation
	 *                        <p>
	 *                        it takes a tourist location as input, searches for
	 *                        that location in the list and removes it.
	 */
	public void removeTouristLocation(String touristLocation) {
		Iterator iterator = touristLocationsList.iterator();
		String temp;

		while (iterator.hasNext()) {
			temp = (String) iterator.next();
			if (temp.equals(touristLocation)) {
				iterator.remove();
			}
		}
	}
}
