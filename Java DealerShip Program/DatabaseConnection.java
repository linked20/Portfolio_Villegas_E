import java.util.Hashtable;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection
{

	Connection c = null;
	Statement stmt = null;
	ResultSet rs = null;

	public void saveVehicle(Vehicle v)
	{
		if(v.id == -1)
			addNewVehicle(v);
		else
			updateExistingVehicle(v);
	}
	
	public void deleteVehicle(int id)
	{
		connect();
		
		String sql = "delete from car where Car_ID = " + id + "";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}
	
	private Boolean connect()
	{
		try
		{
			if(c != null)
				c.close();
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite::resource:IHAA.db");
		} catch (Exception e)
		{
			System.out.println(e.getClass().getName() + " : " + e.getMessage());
			return false;
		}
		return true;
	}
	
	private void disconnect()
	{
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addNewVehicle(Vehicle v)
	{
		connect();
		
		String sql = "INSERT INTO CAR (VIN, Car_Type, Make, Model, Model_Year, Model_Trim, Color, Mileage, Price) VALUES"
			+ "('" + v.vin + "', '" + v.vehicleType + "', '" + v.make + "', '" + v.model + "', " + v.year + ", '" + v.trim + "', '" + v.color + "', " + v.mileage + ", " + v.price + ");";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}
	
	private void updateExistingVehicle(Vehicle v)
	{
		connect();
		
		String sql = "UPDATE Car "
				+ "SET VIN = '" + v.vin + "', Car_Type = '" + v.vehicleType + "', Make = '" +v.make  + "', Model = '" + v.model + "', Model_Year = " + v.year + ", Model_Trim = '" + v.trim + "', Color = '" + v.color + "', Mileage = " + v.mileage + ", Price = " + v.price + " "
				+ "WHERE Car_ID = " + v.id +";";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}
	
	public Vector<Vehicle> getInventory()
	{
		
		connect();
		
		Vector<Vehicle> i = new Vector<Vehicle>();
		
		String sql = "SELECT * FROM Car;";
		
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				i.add(new Vehicle(rs.getInt("Car_ID"), rs.getString("VIN"), rs.getString("Car_Type"), rs.getString("Make"),
						rs.getString("Model"), rs.getInt("Model_Year"), rs.getString("Model_Trim"), rs.getString("Color"), rs.getInt("Mileage"), rs.getInt("Price")));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();

		return i;
	}
	
	public Vector<Employee> getEmployees()
	{
		connect();
		
		Vector<Employee> e = new Vector<Employee>();
		
		String sql = "SELECT * FROM employee;";
		
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				e.add(new Employee(rs.getInt("emp_id"), rs.getString("ssn"), rs.getString("fname"), rs.getString("minit"), rs.getString("lname"),
						rs.getString("title"), rs.getString("wphone"), rs.getString("pphone"), rs.getInt("salary"), rs.getInt("accessLevel"),
						rs.getString("username"), rs.getString("password") ));
			}
		} catch (SQLException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
		}

		disconnect();

		return e;
	}

	private void addNewEmployee(Employee e)
	{
		connect();

		String sql = "INSERT INTO employee (ssn, fname, minit, lname, title, wphone, pphone, salary, AccessLevel, UserName, Password) VALUES"
			+ "('" + e.ssn + "', '" + e.firstName + "', '" + e.MI + "', '" + e.lastName + "', '" + e.title + "', '" + e.workPhone + "', '" + e.personalPhone
			+ "', " + e.salary + ", " + e.accessLevel + ", '" + e.userName + "', '" + e.password +  "');";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
		}

		disconnect();
	}
	
	private void updateExistingEmployee(Employee e)
	{
		connect();
		
		String sql = "UPDATE employee "
				+ "SET ssn = '" + e.ssn + "', fname = '" + e.firstName + "', Minit = '" + e.MI  + "', lname = '" + e.lastName + "', title = '" + e.title
				+ "', wphone = '" + e.workPhone + "', pphone = '" + e.personalPhone + "', salary = " + e.salary + " where emp_id =" + e.id + ";";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException error) {
			// TODO Auto-generated catch block
			error.printStackTrace();
		}

		disconnect();
	}
	
	public void saveEmployee(Employee e)
	{
		if(e.id == -1)
			addNewEmployee(e);
		else
			updateExistingEmployee(e);
	}
	
	public void deleteEmployee(int id)
	{
		connect();
		
		String sql = "delete from employee where Emp_ID = " + id + "";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}

	
	// --- Sale Records ---
	
	void deleteSaleRecord(int id)
	{
		connect();
		
		String sql = "delete from sale where Sale_ID = " + id + "";
		
		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}

	void addNewSaleRecord(SaleRecord sr)
	{
		connect();
		
		String sql_1 = "INSERT INTO Customer (FName, Minit, LName, Phone, Address) VALUES"
			+ "('" + sr.firstName + "', '" + sr.middleInitial + "', '" + sr.lastName + "', '" + sr.phone + "', '" + sr.address + "');";
		
		try
		{
			stmt = c.createStatement();
			stmt.executeUpdate(sql_1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sql_2 = "INSERT INTO Sale (EID, CID, Sold_Car_ID, Date_Year,Date_Month,Date_Day, PriceSold) VALUES"
			+ "('" + sr.employeeID + "', '" + getMostRecentCustID() + "', '" + sr.vehicleID + "', '" + sr.year + "', " + sr.month + ", '" + sr.day + "', '" + sr.salePrice + "');";
		
		
		try
		{
			stmt = c.createStatement();
			stmt.executeUpdate(sql_2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}
	
	int getMostRecentCustID() //TODO: make this private and/or non esixtant 
	{
		int id = -1;
		
//		connect();

		String sql = "SELECT Cust_ID FROM CUSTOMER WHERE Cust_ID IN (SELECT last_insert_rowid());";

		
		try
		{
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);

			id = rs.getInt("Cust_ID");
			
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		disconnect();
		return id;
	}

	void updateExistingSaleRecord(SaleRecord sr) 
	{
		connect();
		
		String sql1 = "UPDATE Sale "
				+ "SET EID = '" + sr.employeeID + "', CID = '" + sr.customerID + "', Sold_Car_ID = '" + sr.vehicleID  + "', Date_Year = '" + sr.year + "', Date_Month = " + sr.month + ", Date_Day = '" + sr.day + "', PriceSold = '" + sr.salePrice + "' "
				+ "WHERE Sale_ID = " + sr.saleRecordID +";";
		
		String sql2 = "UPDATE Customer "
				+ "SET FName = '" + sr.firstName + "', Minit = '" + sr.middleInitial + "', LName = '" + sr.lastName + "', Phone = '" + sr.phone + "', Address = '" + sr.address + "' "
				+ "WHERE Cust_ID = " + sr.customerID +";";

		try {
			stmt = c.createStatement();
			stmt.executeUpdate(sql1);
			stmt.executeUpdate(sql2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();
	}
	
	Vector<SaleRecord> getSaleRecords() 
	{
		
		connect();
		
		Vector<SaleRecord> i = new Vector<SaleRecord>();
		
		String sql = "Select * from Sale Inner Join Customer on Sale.CID = Customer.Cust_ID;";
		
		try {
			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next())
			{
				i.add(new SaleRecord(rs.getInt("Sale_ID"), rs.getInt("EID"), rs.getInt("CID"), rs.getInt("Sold_Car_ID"), rs.getString("FName"), 
					rs.getString("Minit"), rs.getString("LName"), rs.getString("Phone"), rs.getString("Address"), rs.getInt("PriceSold"),
					rs.getInt("Date_Year"), rs.getInt("Date_Month"), rs.getInt("Date_Day") ));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		disconnect();

		return i;
	}

	
	
	
	
	
}









