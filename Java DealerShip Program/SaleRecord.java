
public class SaleRecord{

	int saleRecordID = -1, employeeID=-1, customerID= -1, vehicleID =-1, salePrice, year, month, day;
	String firstName, middleInitial, lastName, phone, address;
	final static String[] saleRecordNames = {"saleRecordID","employeeID","customerID","vehicleID", "First Name", "M", "Last Name", "Phone", "Address", "Price", "Year", "Month", "Day"};
	
	
	SaleRecord(int saleRecordID, int employeeID, int customerID, int vehicleID, String firstName, String middleInitial, 
			String lastName, String phone, String address, int salePrice, int year, int month, int day)
	{
		this.saleRecordID = saleRecordID;
		this.employeeID = employeeID; //foreign key
		this.customerID = customerID;
		this.vehicleID =vehicleID;
		this.firstName = firstName; //customer
		this.middleInitial = middleInitial; //customer
		this.lastName = lastName; //customer
		this.phone = phone; //customer
		this.address = address;
		this.salePrice = salePrice;
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	SaleRecord(SaleRecord sr)
	{
		this(sr.saleRecordID, sr.employeeID, sr.customerID, sr.vehicleID, sr.firstName, sr.middleInitial, sr.lastName, sr.phone, 
				sr.address, sr.salePrice, sr.year, sr.month, sr.day);
	}
	
	SaleRecord()
	{
		this(-1,-1,-1,-1, "error", "error", "error", "error", "error", 0,0,0,0);
	}
	
} 
