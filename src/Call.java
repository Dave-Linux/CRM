import java.text.SimpleDateFormat;
import java.util.Date;

public class Call extends Customer {

	private int customerID;
	private int callID;
	private String description;
	private int customerIndex;
	private int callIndex;
	private Date callDate;
	private boolean processed;
	private Employee processedBy = new Employee(-1);
	private Employee enteredBy;
	
	public Call() {
		
	}
	
	public Call(int customerID) {
		this.customerID = customerID;
		
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
		
		return isProcessed();
		
		}

	public Employee getProcessedBy() {
		return processedBy;
	}

	public void setProcessedBy(Employee processedBy) {
		this.processedBy = processedBy;
	}

	public Employee getEnteredBy() {
		return enteredBy;
	}

	public void setEnteredBy(Employee enteredBy) {
		this.enteredBy = enteredBy;
	}
	
	public String dateToString(Date date) {
		 SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");
		 String strDate = simpleFormat.format(date);
		 return strDate;
		 
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public int getCallID() {
		return callID;
	}

	public void setCallID(int callID) {
		this.callID = callID;
	}
	
	public int getCustomerIndex() {
		return customerIndex;
	}

	public void setCustomerIndex(int customerIndex) {
		this.customerIndex = customerIndex;
	}

	public int getCallIndex() {
		return callIndex;
	}

	public void setCallIndex(int callIndex) {
		this.callIndex = callIndex;
	}
	
	@Override
	public String toString() {
		String Date = dateToString(callDate);
		return callID + "," + customerID + "," + description + "," + Date + "," + processedBy.getEmployeeID() + "," + isProcessed() + "," + enteredBy.getEmployeeID();
	}

	

}

