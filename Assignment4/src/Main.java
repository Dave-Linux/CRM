import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class Main {
	/**
	 * Author: David Garcia
	 * 
	 * Created Date: 10/23/2019
	 * @throws Exception 
	 * 
	 */

	public static void main(String[] args) throws Exception {
		
		Scanner input = new Scanner(System.in);	
		
		
		//Variable Declaration
		int option;
		int foundAt = 0;
		int deleteNumber = 0;
		ArrayList<Integer> multipleFounded = new ArrayList<Integer> (1);
		String fNameSearch;
		String lNameSearch;
		ArrayList<Customer> CustomerRoster = new ArrayList<Customer> (0);
		ArrayList<Employee> EmployeeList = new ArrayList<Employee> (0);
		
		programStartUp(EmployeeList, CustomerRoster);
		
		//Print Menu
		System.out.println("Welcome:");
		do {
		System.out.println("------------------");	
		System.out.println("1. Add Customer");
		System.out.println("2. Delete Customer");
		System.out.println("3. View Customer List");
		System.out.println("4. Find Customer");
		System.out.println("5. Load Customer List");
		System.out.println("6. Save Customer List");
		System.out.println("7. Quit");
				
		
			//Prompt user for a number between 1-7.
			System.out.println("Please select a valid number:");
			option = input.nextInt();
			
		
			switch(option) {
		
			case 1: //Increase CustomerRoster array, prompt user for name, add name to array
				
				addCustomerToCustomerRoster(CustomerRoster);

				break;
			
			case 2: //Prompt user for name and attempts to find name. If not found, notify user. If found, delete user and notify user
				
				deleteCustomer(input, multipleFounded, CustomerRoster);
				
				break;
				
			case 3: //Displays all information in CustomerRoster array
				System.out.println("Displaying List...");
					for(int i=0; i<CustomerRoster.size(); i++) {
						System.out.println((i+1) + "." + CustomerRoster.get(i));
					}
				break; 
				
			case 4: //Prompt user for name and attempts to find name. If not found, notify user. If found, notify user which line user was found
				
				searchCustomer(input, multipleFounded, CustomerRoster);
				
				break;
				
			case 5: //Select Active Employee
				
				chooseActiveEmployee(EmployeeList, input);
			
				break;
				
			case 6: //Enter CRM Call
				
				break;

			case 7: //Exit program
				programShutDown(CustomerRoster, EmployeeList);
				System.out.println("Goodbye!");
				System.exit(0);
				
			default: //Produces error if user entered a number that is not between 1-7
				System.out.println("ERROR: Number is not between 1 and 7.");
				
				}
			
		}while ((option >=1 || option >7));
			
				
			input.close();
	}

public static void enterCRMCall(Scanner input, ArrayList<Integer> multipleFounded, ArrayList<Customer> CustomerRoster, ArrayList<Call> callList) {
		
		int foundAt;
		String fNameSearch;
		String lNameSearch;
		foundAt = 0;
		System.out.println("Enter customer's first name");
		fNameSearch = input.next();
		System.out.println("Enter customer's last name");
		lNameSearch = input.next();
		Customer customerSearch = new Customer(fNameSearch, lNameSearch);
		foundAt = findCustomer(CustomerRoster, customerSearch, foundAt);
		if(foundAt == 0)
			System.out.println("Customer " + customerSearch.getName() + " does not exist.");
		
		multipleFounded.clear();
		multipleFounded.trimToSize();
		multipleFounded.add(foundAt);
		for(int i = foundAt -1; i>=0; i--)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
			
		}
		for(int i = foundAt +1; i < CustomerRoster.size(); i++)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
		}
		
		if(multipleFounded.size() > 1) {
			System.out.println("There are multiple customers with this name.");
		for(int i = 0; i<multipleFounded.size(); i++) {
			System.out.println(i + ". " + CustomerRoster.get(i));
			}
			System.out.println("Please select Customer for call:");
			int choice = input.nextInt();
			callList.add(CustomerRoster.indexOf(multipleFounded.get(choice)), null);
			
		}
		
		else {
			System.out.println("Customer " + customerSearch.getName() + " has been found!");
			Customer caller = customerSearch;
			callList.add(e);
		}
		
		System.out.print("Enter the Call Description: ");
		String description = input.next();
	}



	public static void deleteCustomer(Scanner input, ArrayList<Integer> multipleFounded, ArrayList<Customer> CustomerRoster) {
		
		int foundAt = 0;
		System.out.println("Enter customer's first name");
		String fNameSearch = input.next();
		System.out.println("Enter customer's last name");
		String lNameSearch = input.next();
		Customer customerDelete = new Customer(fNameSearch, lNameSearch);
		foundAt = findCustomer(CustomerRoster, customerDelete, foundAt);
		if(foundAt == 0)
			System.out.println("Customer " + customerDelete.getName() + " does not exist.");
		multipleFounded.clear();
		multipleFounded.trimToSize();
		multipleFounded.add(foundAt);
		for(int i = foundAt -1; i>=0; i--)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerDelete.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
			
		}
		for(int i = foundAt +1; i < CustomerRoster.size(); i++)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerDelete.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
		}
		if(multipleFounded.size() > 1) {
			System.out.println("There are multiple customers with this name.");
		for(int i = 0; i<multipleFounded.size(); i++) {
			System.out.println(i + ". " + CustomerRoster.get(i));
		}
		System.out.print("Please enter the number you wish to delete (-1 to cancel):");
		int deleteNumber = input.nextInt();
		if (deleteNumber == -1)
			return;
		
		CustomerRoster.remove(deleteNumber);
		System.out.println("Customer " + customerDelete.getName() + " has been deleted.");
		}
		
		else {
			CustomerRoster.remove(foundAt);
			System.out.println("Customer " + customerDelete.getName() + " has been deleted.");
			}
	}
	public static void searchCustomer(Scanner input, ArrayList<Integer> multipleFounded, ArrayList<Customer> CustomerRoster) {
		
		int foundAt;
		String fNameSearch;
		String lNameSearch;
		foundAt = 0;
		System.out.println("Enter customer's first name");
		fNameSearch = input.next();
		System.out.println("Enter customer's last name");
		lNameSearch = input.next();
		Customer customerSearch = new Customer(fNameSearch, lNameSearch);
		foundAt = findCustomer(CustomerRoster, customerSearch, foundAt);
		if(foundAt == 0)
			System.out.println("Customer " + customerSearch.getName() + " does not exist.");
		
		multipleFounded.clear();
		multipleFounded.trimToSize();
		multipleFounded.add(foundAt);
		for(int i = foundAt -1; i>=0; i--)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
			
		}
		for(int i = foundAt +1; i < CustomerRoster.size(); i++)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(multipleFounded.size()-1, i);
			}
			else
				break;
		}
		if(multipleFounded.size() > 1) {
			System.out.println("There are multiple customers with this name.");
		for(int i = 0; i<multipleFounded.size(); i++) {
			System.out.println(i + ". " + CustomerRoster.get(i));
			}
		}
		
		else 
			System.out.println("Customer " + customerSearch.getName() + " has been found on line " + foundAt + "!");
	}

	

	public static void programShutDown(ArrayList<Customer> CustomerRoster, ArrayList<Employee> EmployeeList) {
		saveCustomers(CustomerRoster);
		saveEmployees(EmployeeList);
	}
	
	//A list of commands to do on starting up the program
	public static void programStartUp(ArrayList<Employee> employees, ArrayList<Customer> customers) throws Exception{
		
		loadEmployees(employees);
		loadCustomers(customers);
		
	}
	
	public static void chooseActiveEmployee(ArrayList<Employee> employees, Scanner input) {
		Employee activeEmployee = new Employee();
		System.out.println("Employees Are:");
		for(int i=0; i<employees.size(); i++) {
			System.out.println((i+1) + "." + employees.get(i).getName());
		}
		System.out.print("Please select an employee number: ");
		int choice = input.nextInt();
		
		activeEmployee = employees.get(choice); 
	}

	public static void saveEmployees(ArrayList<Employee> employees) {
		try {
		FileWriter fileCustomerRoster = new FileWriter("c:/temp/Employees.csv");
		PrintWriter printCustomerRoster = new PrintWriter(fileCustomerRoster);
		for(int i=0; i<employees.size()-1; i++)
			printCustomerRoster.println(employees.get(i));
		fileCustomerRoster.close();
		printCustomerRoster.close();
		System.out.println("File saved at c:/temp/Employee List !");
		}
		catch(Exception ex)
		{
			System.out.println("Error Writing to File: " + ex.getMessage());
		}
	}
	
	public static void loadEmployees(ArrayList<Employee> array) throws FileNotFoundException, Exception {
		File loadFile = new File("c:\\temp\\Employees.csv");
		if(loadFile.exists()) {
			System.out.println("File has been found!");
			try(Scanner source = new Scanner(loadFile)){
				source.useDelimiter(System.getProperty("line.separator"));
				while(source.hasNext()) {
					String dataPoint = source.next();
					String [] info	= dataPoint.split(",");
					int ID = Integer.parseInt(info[0]);
					addEmployee(array, info [0], info[1], info[2]);
					
				}
			}
		}
		else
			throw new Exception("File doens't exist");
		System.out.println("Employee List Loaded!");
	}
	
	public static void addEmployee(ArrayList<Employee> array, String strID, String name, String strHiredDate) {
		int ID = Integer.parseInt(strID);
		Date hiredDate;
		try {
			hiredDate = new SimpleDateFormat("MM/dd/yyyy").parse(strHiredDate);
			Employee newEmployee = new Employee(ID);
			newEmployee.setName(name);
			newEmployee.setHiredDate(hiredDate);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void saveCustomers(ArrayList<Customer> CustomerRoster) {
		try {
		FileWriter fileCustomerRoster = new FileWriter("c:/temp/CustomerRoster.csv");
		PrintWriter printCustomerRoster = new PrintWriter(fileCustomerRoster);
		for(int i=0; i<CustomerRoster.size()-1; i++)
			printCustomerRoster.println(CustomerRoster.get(i));
		fileCustomerRoster.close();
		printCustomerRoster.close();
		System.out.println("File saved at c:/temp/Customer Roster !");
		}
		catch(Exception ex)
		{
			System.out.println("Error Writing to File: " + ex.getMessage());
		}
	}
	
	//loads customers found from file onto the array of customers
	public static void loadCustomers(ArrayList<Customer> array) throws Exception {
		
		String firstName = null;
		String lastName = null;
		String streetAddress = null;
		String city = null;
		String state = null;
		int zip = 0;
		String phone = null;
		String email = null;
		
		File loadFile = new File("c:\\temp\\CustomerRoster.csv");
		if(loadFile.exists()) {
			System.out.println("File has been found!");
			try(Scanner source = new Scanner(loadFile)){
				source.useDelimiter(System.getProperty("line.separator"));
				while(source.hasNext()) {
					String dataPoint = source.next();
					String [] info	= dataPoint.split(",");
					
					firstName = info[0];
					lastName = info[1];
					streetAddress = info[2];
					city = info[3];
					state = info[4];
					zip = Integer.parseInt(info[5]);
					phone = info[6];
					email = info[7];
				}
			}
			
		}
		else
			throw new Exception("File doens't exist");
		
		Customer newCustomer = new Customer(firstName, lastName);
		newCustomer.setAddress(streetAddress, city, state);
		newCustomer.setZip(zip);
		newCustomer.setPhone(phone);
		newCustomer.setEmail(email);
		
		addCustomer(array, newCustomer);
	}
	//Adds customer to the array of customers
	public static void addCustomerToCustomerRoster(ArrayList<Customer> array) {
		Scanner input = new Scanner(System.in);
		input.useDelimiter(System.lineSeparator());
		
		System.out.print("First Name:");
		String firstName = input.next();
		System.out.print("Last Name:");
		String lastName = input.next();
		Customer newCustomer = new Customer(firstName, lastName);
		input.nextLine();
		System.out.print("Street Address:");
		String streetAddress = input.nextLine();
		System.out.print("City:");
		String city = input.next();
		System.out.print("State:");
		String state = input.next();
		System.out.print("Zip Code:");
		tryZipCode(input, newCustomer);
		System.out.print("Phone Number:");
		String phone = input.next();
		System.out.print("Email Address:");
		String email = input.next();
		
		
		newCustomer.setAddress(streetAddress, city, state);
		newCustomer.setPhone(phone);
		newCustomer.setEmail(email);
		
		addCustomer(array, newCustomer);
		
		System.out.println("Customer " + firstName + " " + lastName + " has been added");
		}
	
	public static void addCustomer(ArrayList<Customer> array, Customer newCustomer) {
		boolean added = false;
		for(int i=0; i < array.size(); i++)
			if(array.get(i).getName().compareTo(newCustomer.getName()) == 1) 
			{
				array.add(i, newCustomer);
				added=true;
				break;
			}
		if(!added)
			array.add(newCustomer);
	}
	
	public static void tryZipCode(Scanner input, Customer newCustomer) {
		String stringZip="";
		
		do
		{
			try 
			{
				stringZip = input.next();
				if(!stringZip.isEmpty())
				{
					int zip = 0;
					zip = Integer.valueOf(stringZip);
					newCustomer.setZip(zip);
					stringZip="";
				}
			}
			catch(Exception ex)
			{
				System.out.println("Invalid Zip Code: " +ex.getMessage());
				System.out.print("Zip Code:");
				stringZip="N.A";
			}
		}while(!stringZip.isEmpty());
	}
	
		//Increases CustomerRoster array size by 1. Inspired by professor's example
	public static Customer[] increaseCustomerRosterSize(Customer[] array) {
		Customer[] newArray = new Customer[array.length+1];
		
		for(int i=0; i<array.length; i++) {
			newArray[i] = array[i];
			}		
		return newArray;

		}
	//Increases int array size by 1
	public static int[] increaseArraySize(int[] array) {
		int[] newArray = new int[array.length+1];
		
		for(int i=0; i<array.length; i++) {
			newArray[i] = array[i];
			}		
		return newArray;

		}
	//Resets int array size to 0
	public static int[] resetArraySize(int[] array) {
		int[] newArray = new int[0];
		return newArray;

		}
	
		//Decreases CustomerRoster array size by 1.
	public static Customer[] decreaseCustomerRosterSize(Customer[] array) {
		Customer[] newArray = new Customer[array.length-1];
		
		for(int i=0; i<newArray.length; i++) {
			newArray[i] = array[i];
			}		
		return newArray;

		}
	
	//Sorts array and then finds key term within array. Returns position number if found. Returns 0 if not found.
	public static int findCustomer(ArrayList<Customer> array, Customer key, int foundAt) {
	
		int low=0;
		int high=array.size()-1;
		
				
		while(low < high) {
			int mid=(low + high)/2;
			
			if (key.compareTo(array.get(mid)) == 0)
				
				return foundAt = mid;
			
			else if(key.compareTo(array.get(mid)) > 0)
				high = mid-1;
			
			else if (key.compareTo(array.get(mid)) < 0) 
				low = mid+1;
		}
		
		return foundAt;
	}
		
	
}

