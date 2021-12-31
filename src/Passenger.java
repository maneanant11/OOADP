public class Passenger {

	private static int idCounter;
	private int id;
	private String password;
	private Address address;
	private Contact contact = new Contact();

	public Passenger(String password, String name, long phoneNumber, String emailId, Address address) {
		this.password = password;
		this.address = address;
		this.contact.name = name;
		this.contact.phoneNumber = phoneNumber;
		this.contact.emailId = emailId;

		this.id = idCounter;
		idCounter++;
	}

	public Address getAddress() {
		return address;
	}

	public Contact getContact() {
		return contact;
	}

	public void updateAddress(Address address) {
		this.address = address;
	}

	public void updateContact(String name, long phoneNumber, String emailId) {
		this.contact.name = name;
		this.contact.phoneNumber = phoneNumber;
		this.contact.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public String getEmailID() {
		return contact.emailId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public static int getPassengerCount() {
		return idCounter;
	}

	private static class Contact {
		private String name;
		private long phoneNumber;

		@Override
		public String toString() {
			return "Contact [name=" + name + ", phoneNumber=" + phoneNumber + ", emailId=" + emailId + "]";
		}

		private String emailId;
	}

}
