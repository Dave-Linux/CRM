import java.text.SimpleDateFormat;
import java.util.Date;

public class Employee{
	
	private int employeeID;
	private String name;
	private Date hiredDate;
	
	public Employee() {
	}
	
	public Employee(int employeeID) {
		this.setEmployeeID(employeeID);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(Date hiredDate) {
		this.hiredDate = hiredDate;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	public String dateToString(Date date) {
		 SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy");
		 String strDate = simpleFormat.format(date);
		 return strDate;
		 
	}
	
	
	@Override
	public String toString() {
		return employeeID + "," + name + "," + dateToString(hiredDate);
	}
}

