import java.util.ArrayList;

public class Customer{
	
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private int zip;
	private String phoneNumber;
	private String emailAddress;
	private int customerID;
	private ArrayList<Call> crmCalls;
	
	public Customer() {
		
	}
	
	public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void setName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;		
	}
	
	public String getName() {
		return this.firstName + this.lastName;
	}
	
	public void setAddress(String streetAddress, String city, String state) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		}
	
	public String getAddress() {
		return this.streetAddress + this.city + this.state + this.getZip();
	}
	
	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
	
	public void setPhone(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhone() {
		return this.phoneNumber;
	}
	
	public void setEmail(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getEmail() {
		return this.emailAddress;
	}
	
	public int compareTo(Customer c) {
		int result=0; 
		result = this.getName().compareToIgnoreCase(c.getName());
		if (result == 0)
			if(!this.equals(c))
					result =-1;
		return result;
	}
	
	public boolean equals(Customer c) {
		if(c.firstName.compareToIgnoreCase(this.firstName) == 0 && c.lastName.compareToIgnoreCase(this.lastName) == 0 && c.emailAddress.compareToIgnoreCase(this.emailAddress) == 0 && c.city.compareToIgnoreCase(this.city) == 0 && c.state.compareToIgnoreCase(this.state) == 0)
			return true;
		else
			return false;
	}
	
	@Override
	public String toString() {
		return firstName + ", " + lastName + ", " + streetAddress + ", " + city + ", " + state + ", " + getZip() + ", " + phoneNumber + ", " + emailAddress;
	}


}
