import java.util.Date;

public class Employee extends Call{
	
	private int employeeID;
	private String name;
	private Date hiredDate;
	
	public Employee() {
	}
	
	public Employee(int employeeID) {
		this.employeeID = employeeID;
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
}
