import java.util.Date;

public class Call extends Customer {

	private String customerID;
	private int callID;
	private String description;
	private Date callDate;
	private boolean processed;
	private Employee processedBy;
	private Employee enteredBy;
	
	
	
	public Call(int customerID) {
		
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return callDate;
	}

	public void setDate(Date callDate) {
		this.callDate = callDate;
	}
	
	public boolean markProcessed() {
		
		return processed;
		
		}
	}

