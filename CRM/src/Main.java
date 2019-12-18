import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.text.SimpleDateFormat;



public class Main{
	/**
	 * Author: David Garcia
	 * 
	 * Created Date: 10/27/2019
	 * @throws Exception 
	 * 
	 */

	public static void main(String[] args) throws Exception {
		
		Scanner input = new Scanner(System.in);	
		
		
		//Variable Declaration
		int option;
		int numOfCustomers=0;
		ArrayList<Integer> multipleFounded = new ArrayList<Integer> (1);
		ArrayList<Customer> CustomerRoster = new ArrayList<Customer> (0);
		ArrayList<Employee> EmployeeList = new ArrayList<Employee> (0);
		Employee activeEmployee = new Employee();
		
		try {
		numOfCustomers = programStartUp(EmployeeList, CustomerRoster, numOfCustomers);
		
		//Print Menu
		System.out.println("Welcome:");
		System.out.println(numOfCustomers + " Customers have been loaded!");
		do {
		System.out.println("Active Employee: " + activeEmployee.getName());	
		System.out.println("------------------");	
		System.out.println("1. Add Customer");
		System.out.println("2. Delete Customer");
		System.out.println("3. View Customer List");
		System.out.println("4. Find Customer");
		System.out.println("5. Select Active Employee");
		System.out.println("6. Enter CRM Call");
		System.out.println("7. Process Call");
		System.out.println("8. Quit");
		
			//Prompt user for a number between 1-8.
			System.out.println("Please select a valid number:");
			option = input.nextInt();
			
		
			switch(option) {
		
			case 1: //Increase CustomerRoster array, prompt user for name, add name to array
				
				addCustomerToCustomerRoster(CustomerRoster, numOfCustomers);

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
				
				chooseActiveEmployee(EmployeeList, activeEmployee, input);
			
				break;
				
			case 6: //Enter CRM Call
				enterCRMCall(multipleFounded, CustomerRoster, activeEmployee, input);
				break;
				
			case 7: //Process Calls
				processCall(input, CustomerRoster, activeEmployee);
				break;

			case 8: //Exit program
				programShutDown(CustomerRoster, EmployeeList);
				System.out.println("Goodbye!");
				System.exit(0);
				
			default: //Produces error if user entered a number that is not between 1-8
				System.out.println("ERROR: Number is not between 1 and 8.");
				
				}
			
		}while ((option >=1 || option >8));
			
				
			input.close();
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	
	//Commands to execute when shutting down
	public static void programShutDown(ArrayList<Customer> CustomerRoster, ArrayList<Employee> EmployeeList) {
		
		saveCustomers(CustomerRoster);
		saveEmployees(EmployeeList);
		saveCallList(CustomerRoster);
	}
	
	//A list of commands to do on starting up the program
	public static int programStartUp(ArrayList<Employee> employees, ArrayList<Customer> customers, int numOfCustomers) throws Exception{
		
		numOfCustomers = loadCustomers(customers, numOfCustomers);
		loadEmployees(employees);
		loadCallList(customers,employees);
		
		return numOfCustomers;
	}
	
	//Loads the list of calls from the file
	public static void loadCallList(ArrayList<Customer> CustomerRoster, ArrayList<Employee> EmployeeList) {
		File loadCallList = new File("c:\\temp\\CallList.csv");
		
		if(loadCallList.exists()) {
			System.out.println("File has been found!");
			try(Scanner source = new Scanner(loadCallList)){
				source.useDelimiter(System.getProperty("line.separator"));
				while(source.hasNext()) {
					String dataPoint = source.next();
					String [] info = dataPoint.split(",");			
					String callID = info[0];
					String customerID = info[1];
					String description = info[2];
					String callDate = info[3];
					String processedBy = info[4];
					String isProcessed = info[5];
					String enteredBy = info[6];
				
				addCalls(CustomerRoster, EmployeeList, callID, customerID, description, callDate, processedBy, isProcessed, enteredBy);
				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		}
		else {
			try {
			System.out.println("File doens't exist");
			FileWriter fileCallList = new FileWriter("c:/temp/CallList.csv");
			
				fileCallList.close();
			
			System.out.println("Call List has been created at c:/temp/CallList.csv");
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		System.out.println("CallList List Loaded!");
	}
	}
	
	//Adds the loaded call to the designated customer
	public static void addCalls(ArrayList<Customer> CustomerRoster, ArrayList<Employee> EmployeeList, String strCallID, String strCustomerID, String description, String strCallDate, String strProcessedBy, String strIsProcessed, String strEnteredBy) {
		int callID = Integer.parseInt(strCallID);
		int customerID = Integer.parseInt(strCallID);
		int processedBy = Integer.parseInt(strProcessedBy);
		int enteredBy = Integer.parseInt(strEnteredBy);
		int foundCustomerAt;
		int foundEmployeeAt;
		Date CallDate;
		boolean isProcessed;
		try {
			CallDate = new SimpleDateFormat("MM/dd/yyyy").parse(strCallDate);
			if(strIsProcessed.equalsIgnoreCase("true"))
				isProcessed = true;
			else
				isProcessed = false;
			
			foundCustomerAt = findCustomerID(CustomerRoster, customerID);
			
			Call incomingCall = new Call(customerID);
			incomingCall.setCallID(callID);
			incomingCall.setDescription(description);
			foundEmployeeAt = findEmployeeID(EmployeeList, processedBy);
			incomingCall.setProcessedBy(EmployeeList.get(foundEmployeeAt));
			incomingCall.setProcessed(isProcessed);
			foundEmployeeAt = findEmployeeID(EmployeeList, enteredBy);
			incomingCall.setEnteredBy(EmployeeList.get(foundEmployeeAt));
			incomingCall.setDate(CallDate);
			
			CustomerRoster.get(foundCustomerAt).getCrmCalls().add(incomingCall);
			
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
	//Saves the current list of calls to a .csv file
	public static void saveCallList(ArrayList<Customer> CustomerRoster) {
		try {
			FileWriter fileCallList = new FileWriter("c:/temp/CallList.csv");
			PrintWriter printCallList = new PrintWriter(fileCallList);
			for(int i=0; i < CustomerRoster.size(); i++) {
				for(int j=0; j < CustomerRoster.get(i).getCrmCalls().size(); j++) {
					printCallList.println(CustomerRoster.get(i).getCrmCalls().get(j));
				}
			}
			
			fileCallList.close();
			printCallList.close();
			System.out.println("File saved at c:/temp/Call List !");
			}
			catch(Exception ex)
			{
				System.out.println("Error Writing to File: " + ex.getMessage());
			}
		}
	
	//Loads a list of unprocessed calls (processedBy = -1) and asks user to process them
	public static void processCall(Scanner input, ArrayList<Customer> CustomerRoster, Employee activeEmployee) {
		int callID=0;
		boolean Y= true;
		boolean N = false;
		String name;
		String description;
		Date callDate;
		ArrayList <Call> pendingCallList = new ArrayList<Call> (0);
		
		
		System.out.println("These calls are pending:");
		System.out.println("Call ID, Customer, Short Description, Call Date");
		for(int i=0; i < CustomerRoster.size(); i++) {
			for(int j=0; j < CustomerRoster.get(i).getCrmCalls().size(); j++) {
				if(CustomerRoster.get(i).getCrmCalls().get(j).isProcessed() == false) {
					
					callID = CustomerRoster.get(i).getCrmCalls().get(j).getCallID();
					name = CustomerRoster.get(i).getName();
					description = CustomerRoster.get(i).getCrmCalls().get(j).getDescription();
					callDate = CustomerRoster.get(i).getCrmCalls().get(j).getDate();
					
					Call pendingCall = new Call(CustomerRoster.get(i).getCustomerID());
					pendingCall.setCustomerIndex(i);
					pendingCall.setCallIndex(j);
					pendingCall.setCallID(callID);
					pendingCall.setDescription(description);
					pendingCall.setDate(callDate);
					
					pendingCallList.add(pendingCall);
				}
			}
		}
		do {
		for(int i=0; i < pendingCallList.size(); i++)
			System.out.println((i+1) + ". " + pendingCallList.get(i).getCallID() + "," + CustomerRoster.get(pendingCallList.get(i).getCustomerIndex()).getName() + "," + pendingCallList.get(i).getDescription() + "," + pendingCallList.get(i).getDate());
		
		System.out.print("Select a Call to Process (-1 to exit): ");
		int choice = input.nextInt();
		if(choice == -1)
			return;
		else {
			choice--;
			System.out.print("Customer: ");
			System.out.println(CustomerRoster.get(pendingCallList.get(choice).getCustomerIndex()).getName());
			System.out.print("Description: ");
			System.out.println(pendingCallList.get(choice).getDescription());
			System.out.print("Process Call Y/N: ");	
			String yOrN;
			boolean yesOrNo = false;
			do {
				yOrN = input.next();
			if(yOrN.equalsIgnoreCase("Y"))
				yesOrNo = true;
			else if(yOrN.equalsIgnoreCase("N"))
				yesOrNo = false;
			else
				System.out.println("Please enter either Y or N.");
			}while(!"Y".equalsIgnoreCase(yOrN) && !"N".equalsIgnoreCase(yOrN));
			
			if(yesOrNo) {
				CustomerRoster.get(pendingCallList.get(choice).getCustomerIndex()).getCrmCalls().get(pendingCallList.get(choice).getCallIndex()).setProcessed(true);
				pendingCallList.remove(choice);
				//return;
				}
			}
		}while(pendingCallList.size() > 0);
		//return;
	}
	
	//Checks to see if an Active Employee has been selected (Won't run without it), prompts user for customer's first name and last name, and searches for customer. Once found, it will ask for description of call then attach call to customer.
	public static void enterCRMCall(ArrayList<Integer> multipleFounded, ArrayList<Customer> CustomerRoster, Employee activeEmployee, Scanner input) {
		
		if(activeEmployee.getEmployeeID() == 0) {
			System.out.print("ERROR: NO ACTIVE EMPLOYEE SELECTED");
			return;
		}
		int foundAt;
		String fNameSearch;
		String lNameSearch;
		foundAt = 0;
		System.out.println("Enter customer's first name");
		fNameSearch = input.next();
		System.out.println("Enter customer's last name");
		lNameSearch = input.next();
		Customer customerSearch = new Customer(fNameSearch, lNameSearch);
		foundAt = findCustomer(CustomerRoster, customerSearch);
		if(foundAt == -1)
			System.out.println("Customer " + customerSearch.getName() + " does not exist.");
		multipleFounded.clear();
		multipleFounded.trimToSize();
		multipleFounded.add(foundAt);
		for(int i = foundAt -1; i>=0; i--)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(i);
			}
			else
				break;
			
		}
		for(int i = foundAt +1; i < CustomerRoster.size(); i++)
		{
			if(CustomerRoster.get(i).getName().compareToIgnoreCase(customerSearch.getName()) == 0)
			{
				multipleFounded.add(i);
			}
			else
				break;
		}
		
		if(multipleFounded.size() > 1) {
			System.out.println("There are multiple customers with this name.");
		for(int i = 0; i<multipleFounded.size(); i++) {
			System.out.println(multipleFounded.get(i) + ". " + CustomerRoster.get(multipleFounded.get(i)));
			}
			System.out.println("Please select Customer for call:");
			int choice = input.nextInt();
			Customer caller = CustomerRoster.get(choice);
			Call currentCall = new Call(CustomerRoster.get(choice).getCustomerID());
			System.out.print("Enter the Call Description: ");
			String description = input.nextLine();
			Date callDate = new Date();
			currentCall.setEnteredBy(activeEmployee);
			currentCall.setDescription(description);
			currentCall.setDate(callDate);
			CustomerRoster.get(choice).getCrmCalls().add(currentCall);
			System.out.println("Call added!");
			
		}
		
		else {
			System.out.println("Customer " + customerSearch.getName() + " has been found!");
			//Customer caller = customerSearch;
			Call currentCall = new Call(CustomerRoster.get(foundAt).getCustomerID());
			System.out.print("Enter the Call Description: ");
			input.nextLine();
			String description = input.nextLine();
			Date callDate = new Date();
			currentCall.setEnteredBy(activeEmployee);
			currentCall.setDescription(description);
			currentCall.setDate(callDate);
			CustomerRoster.get(foundAt).getCrmCalls().add(currentCall);
			System.out.println("Call added!");
		}
		
	}
	
	//Prompt user for name and attempts to find name. If not found, notify user. If found, delete user and notify user
	public static void deleteCustomer(Scanner input, ArrayList<Integer> multipleFounded, ArrayList<Customer> CustomerRoster) {
		
		int foundAt = 0;
		System.out.println("Enter customer's first name");
		String fNameSearch = input.next();
		System.out.println("Enter customer's last name");
		String lNameSearch = input.next();
		Customer customerDelete = new Customer(fNameSearch, lNameSearch);
		foundAt = findCustomer(CustomerRoster, customerDelete);
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
			System.out.println(multipleFounded.get(i) + ". " + CustomerRoster.get(multipleFounded.get(i)));
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
	
	//Prompt user for name and attempts to find name. If not found, notify user. If found, notify user which line user was found
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
		foundAt = findCustomer(CustomerRoster, customerSearch);
		if(foundAt == 0)
			System.out.println("Customer " + customerSearch.getName() + " does not exist.");
		
		multipleFounded.clear();
		multipleFounded.trimToSize();
		multipleFounded.add(foundAt);
		for(int i = foundAt-1; i>=0; i--)
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
			System.out.println(multipleFounded.get(i) + ". " + CustomerRoster.get(multipleFounded.get(i)));
			}
		}
		
		else 
			System.out.println("Customer " + customerSearch.getName() + " has been found on line " + foundAt + "!");
	}
	
	//Displays list of employees and prompts for user to select one. Active Employee will be displayed on menu
	public static void chooseActiveEmployee(ArrayList<Employee> employees, Employee activeEmployee, Scanner input) throws CloneNotSupportedException {
		System.out.println("Employees Are:");
		for(int i=0; i<employees.size(); i++) {
			System.out.println((i+1) + "." + employees.get(i).getName());
		}
		System.out.print("Please select an employee number: ");
		int choice = input.nextInt() -1;
		
		activeEmployee.setEmployeeID(employees.get(choice).getEmployeeID());
		activeEmployee.setHiredDate(employees.get(choice).getHiredDate());
		activeEmployee.setName(employees.get(choice).getName());
		
		System.out.println(activeEmployee.getName() + " is now the active employee!");
		}
	
	//Saves the current list of employees to a .csv file
	public static void saveEmployees(ArrayList<Employee> employees) {
		try {
		FileWriter fileEmployeeList = new FileWriter("c:/temp/Employees.csv");
		PrintWriter printEmployeeList = new PrintWriter(fileEmployeeList);
		for(int i=0; i<employees.size(); i++)
			printEmployeeList.println(employees.get(i));
		fileEmployeeList.close();
		printEmployeeList.close();
		System.out.println("File saved at c:/temp/Employee List !");
		}
		catch(Exception ex)
		{
			System.out.println("Error Writing to File: " + ex.getMessage());
		}
	}
	
	//Loads the list of employees from a file
	public static void loadEmployees(ArrayList<Employee> array) throws FileNotFoundException, Exception {
		File loadFile = new File("c:\\temp\\Employees.csv");
		
		if(loadFile.exists()) {
			System.out.println("File has been found!");
			try(Scanner source = new Scanner(loadFile)){
				source.useDelimiter(System.getProperty("line.separator"));
				while(source.hasNext()) {
					String dataPoint = source.next();
					String [] info = dataPoint.split(",");			
					String ID = info[0];
					String name = info[1];
					String date = info[2];
				
				addEmployee(array, ID, name, date);
				}
			}
		}
		else {
			System.out.println("File doens't exist");
			FileWriter fileEmployeeList = new FileWriter("c:/temp/Employees.csv");
			fileEmployeeList.close();
			System.out.println("Employee List has been created at c:/temp/Employees.csv");
			
		}
		
	}
	
	//Adds the loaded employee to the EmployeeList array
	public static void addEmployee(ArrayList<Employee> array, String strID, String name, String strHiredDate) {
		int ID = Integer.parseInt(strID);
		Date hiredDate;
		boolean added = false;
		try {
			hiredDate = new SimpleDateFormat("MM/dd/yyyy").parse(strHiredDate);
			Employee newEmployee = new Employee(ID);
			newEmployee.setName(name);
			newEmployee.setHiredDate(hiredDate);
			
			
			for(int i=0; i < array.size(); i++)
				if(array.get(i).getName().compareTo(newEmployee.getName()) == 1) 
				{
					array.add(i, newEmployee);
					added=true;
					break;
				}
			if(!added)
				array.add(newEmployee);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
	}
	
	//saves the current list of customers to a .csv file
	public static void saveCustomers(ArrayList<Customer> CustomerRoster) {
		try {
		FileWriter fileCustomerRoster = new FileWriter("c:/temp/CustomerRoster.csv");
		PrintWriter printCustomerRoster = new PrintWriter(fileCustomerRoster);
		for(int i=0; i<CustomerRoster.size(); i++)
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
	public static int loadCustomers(ArrayList<Customer> array, int numOfCustomers) throws Exception {
		
		int customerID = 0;
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
					
					customerID = Integer.parseInt(info[0]);
					firstName = info[1];
					lastName = info[2];
					streetAddress = info[3];
					city = info[4];
					state = info[5];
					zip = Integer.parseInt(info[6]);
					phone = info[7];
					email = info[8];
				
		
		Customer newCustomer = new Customer(firstName, lastName);
		newCustomer.setAddress(streetAddress, city, state);
		newCustomer.setZip(zip);
		newCustomer.setPhone(phone);
		newCustomer.setEmail(email);
		newCustomer.setCustomerID(customerID);
		
		addCustomer(array, newCustomer);
		numOfCustomers++;
				}
			}
			
		}
		else
			throw new Exception("File doens't exist");
		return numOfCustomers;
		
	}
	
	//Adds customer to the array of customers
	public static int addCustomerToCustomerRoster(ArrayList<Customer> array, int numOfCustomers) {
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
		int customerID = numOfCustomers + 1;
		int found =-1;
		do {
		found = findCustomerID(array, customerID);
			if(found != -1)
			customerID++;
		}while(found!=-1);	
		newCustomer.setCustomerID(customerID);
		
		addCustomer(array, newCustomer);
		numOfCustomers++;
		System.out.println("Customer " + firstName + " " + lastName + " has been added");
		
		return numOfCustomers;
		}
	
	//Adds the loaded customer to the array list CustomerRoster
	public static void addCustomer(ArrayList<Customer> array, Customer newCustomer) {
		boolean added = false;
		for(int i=0; i < array.size(); i++)
			if(array.get(i).getName().compareTo(newCustomer.getName()) >0) 
			{
				array.add(i, newCustomer);
				added=true;
				break;
			}
		if(!added)
			array.add(newCustomer);
	}
	
	//attempts to load the zip code
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
	
	//Attempts to find an employee by employeeID (key). returns 0 if not found.
	public static int findEmployeeID(ArrayList<Employee> array, int key) {
		int foundAt=0;
		int low=0;
		int high=array.size()-1;
		
				
		while(low < high) {
			int mid=(low + high)/2;
			
			if (key == (array.get(mid).getEmployeeID()))
				
				return foundAt = mid;
			
			else if(key > (array.get(mid).getEmployeeID()))
				high = mid-1;
			
			else if (key < (array.get(mid).getEmployeeID())) 
				low = mid+1;
		}
		
		return foundAt;
	}
	
	//Attempts to find an customerID (key). Returns 0 if not found. 
	public static int findCustomerID(ArrayList<Customer> array, int key) {
		int foundAt=0;
		
		for(int i=0; i < array.size(); i++) {
			if (key == (array.get(i).getCustomerID()))
				return i;
		}
		return foundAt;
	}
	
	//Finds key term within array. Returns position number if found. Returns -1 if not found.
	public static int findCustomer(ArrayList<Customer> array, Customer key) {
		int foundAt=-1;
		int low=0;
		int high=array.size()-1;
		
				
		while(low <= high) {
			int mid=(low + high)/2;
			
			if (array.get(mid).getName().compareToIgnoreCase(key.getName()) == 0)
				
				return foundAt = mid;
			
			else if(array.get(mid).getName().compareToIgnoreCase(key.getName()) > 0)
				high = mid-1;
			
			else if (array.get(mid).getName().compareToIgnoreCase(key.getName()) < 0) 
				low = mid+1;
		}
		
		return foundAt;
	}
}
