
public class Customer {
	int customerID;
	String firstName, middleInitial, lastName, phone, address;
	
	Customer(int customerID, String firstName, String middleInitial, String lastName, String phone, String address)
	{
		this.customerID = customerID;
		this.firstName = firstName;
		this.middleInitial = middleInitial;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}
	
	Customer(Customer c)
	{
		customerID = c.customerID;
		firstName = c.firstName;
		middleInitial = c.middleInitial;
		lastName = c.lastName;
		phone = c.phone;
		address = c.address;
	}
	
	Customer()
	{
		customerID = -1;
		firstName = middleInitial = lastName = phone = address = "nil";
	}
}
