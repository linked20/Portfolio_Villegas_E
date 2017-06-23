
public class Employee {

	int id = -1, salary, accessLevel;
	String ssn, firstName, MI, lastName, title, workPhone, personalPhone, userName, password;
	final static String[] employeeAttributeNames = {"ID", "SSN", "FirstName", "MI", "LastName", "Title", "WorkPhone", "PersonalPhone", "Salary"};
	
	Employee(int id,
			String ssn,
			String firstName,
			String MI,
			String lastName,
			String title,
			String workPhone,
			String personalPhone,
			int salary,
			int accessLevel,
			String userName,
			String password)
	{
		this.id = id;
		this.ssn = ssn;
		this.firstName = firstName;
		this.MI = MI;
		this.lastName = lastName;
		this.title = title;
		this.workPhone = workPhone;
		this.personalPhone = personalPhone;
		this.salary = salary;
		this.accessLevel = accessLevel;
		this.userName = userName;
		this.password = password;
	}
	
	Employee(Employee e)
	{
		this(e.id, e.ssn, e.firstName, e.MI, e.lastName, e.title, e.workPhone, e.personalPhone, e.salary, e.accessLevel, e.userName, e.password);
	}
	
	Employee()
	{//use this when an Employee needs to be initialized, but live data is not yet available / applicable
		this(-1, "error", "error", "error", "error", "error", "error", "error", 0, -1, "error", "error");
	}
	
	
}
