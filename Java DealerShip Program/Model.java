import java.awt.List;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class Model
{
	
	static final Model sharedInstance = new Model();
	
	private DatabaseConnection db;
	
	private Vector<Vehicle> inventory; //TODO: get rid of this. database should be only thing that owns inventory information
	private int currentUser = -1; //value of < 0 means no user is logged in	
	
 	private Model()
	{
		db = new DatabaseConnection();
		
		inventory = new Vector<Vehicle>();
		populateInventory();
	}
	
	
	// --- vehicles ---
	private void populateInventory()
	{//TODO: load inventory from database class
		inventory.clear();
		inventory = db.getInventory();
	}
	
	Vector<Vehicle> getInventory()
	{
		return new Vector<Vehicle>(inventory);
	}
	
	Vehicle getVehicle(int id)
	{
		for(Vehicle v : inventory)
		{
			if(v.id == id)
			{
				return new Vehicle(v);
			}
		}
		
		return new Vehicle();
	}
	
	void saveVehicle(Vehicle v)
	{
		db.saveVehicle(v);
		populateInventory();
	}

	void deleteVehicle(int id)
	{
		db.deleteVehicle(id);
		populateInventory();
	}
	
	
	// --- employees ---

	Vector<Employee> getEmployees()
	{
		return db.getEmployees();
	}
		
	Employee getEmployee(int id)
	{
		Employee ret = new Employee();
		for(Employee e : getEmployees())
		{
			if(e.id == id)
			{
				ret = new Employee(e);
			}
		}
		return ret;
	}

	void saveEmployee(Employee e)
	{
		db.saveEmployee(e);
	}

	void deleteEmployee(int id)
	{
		db.deleteEmployee(id);
	}
	
	
	// --- authentication ---
	
	boolean logIn(String username, String password)
	{
		for(Employee e : getEmployees())
		{
			if(username.equals(e.userName) && password.equals(e.password))
			{
				currentUser = e.id;
				return true;
			}
		}
		
		currentUser = -1;
		return false;
	}
	
	void logOut()
	{
		currentUser = -1;
	}
	
	boolean isLoggedIn()
	{
		return currentUser != -1;
	}
	
	Employee getCurrentUser()
	{
		return getEmployee(currentUser);
	}
	
	boolean checkPrivelage(int requiredAccessLevel)
	{//use this to see if the current user has permission to do a specific action
		if(getCurrentUser().accessLevel >= requiredAccessLevel)
			return true;
		else
			return false;
	}
	
	
	// --- sale records ---
	
	Vector<SaleRecord> getSaleRecords()
	{
		return db.getSaleRecords();
	}
	
	void saveSaleRecord(SaleRecord sr)
	{
		if(sr.saleRecordID < 0)
		{
			db.addNewSaleRecord(sr);
			deleteVehicle(sr.vehicleID);
		}
		else
			db.updateExistingSaleRecord(sr);
	}

	SaleRecord getSaleRecord(int id)
	{
		SaleRecord ret = new SaleRecord();
		for(SaleRecord sr : getSaleRecords())
		{
			if(sr.saleRecordID == id)
			{
				ret = new SaleRecord(sr);
			}
		}
		return ret;
	}

	int getMostRecentCustomerID()
	{
		return db.getMostRecentCustID();
	}
	
	void deleteSaleRecord(int id)
	{
		db.deleteSaleRecord(id);
	}

	
	// --- privelages ---
	
	boolean canViewEmployeeListPage()
	{
		return getCurrentUser().accessLevel >= 5;
	}
	
	
}















