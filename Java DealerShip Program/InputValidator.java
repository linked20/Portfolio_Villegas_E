
public class InputValidator
{
	public static InputValidator sharedInstance = new InputValidator();
	private InputValidator(){};
	
	boolean validateName(String name)
	{
		return name.matches("[a-zA-Z]{2,30}");
	}
	
	boolean validateMinit(String minit)
	{
		return minit.matches("[a-zA-Z]?");
	}
	
	boolean validateSSN(String ssn)
	{
		return ssn.matches("\\d{9}");
	}
	
	boolean validateSalary(String salary)
	{
		return salary.matches("\\d{1,9}");
	}
	
	boolean validatePhoneNumber(String number)
	{
		return number.matches("\\d{10}");
	}
	
	boolean validateAddress(String addr) //TODO: add support for spaces in regex
	{
//		return addr.matches("[a-zA-Z0-9.]{2,60}"); 
		return true;
	}
	
	boolean validateMonth(String month)
	{
		return month.matches("(10|11|12|0?[1-9])");
	}
	
	boolean validateDay(String day)
	{
		return day.matches("(3[0|1]|[12][0-9]|0?[1-9])");
	}
	
	boolean validateVehicleVIN(String vin)
	{
		return vin.matches("[a-zA-Z0-9]{17}");
	}
	
	boolean validateVehicleType(String type)
	{
		return type.matches("[a-zA-Z]{4,15}");
	}
	
	boolean validateVehicleMakeModelColor(String name)
	{
		return name.matches("[a-zA-Z]{2,40}");
	}
	
	boolean validateVehicleYear(String year)
	{
		return year.matches("(19[0-9]{2}|20[0-9]{2})");
	}
	
	boolean validateVehicleTrim(String trim)
	{
		return trim.matches("[a-zA-Z]{1,20}");
	}
	
	boolean validateVehicleMileage(String miles)
	{
		return miles.matches("\\d{1,7}");
	}
	
	boolean validateVehiclePrice(String price)
	{
		return price.matches("\\d{1,9}");
	}

	boolean validateAccessLevel(String accessLevel)
	{
		return accessLevel.matches("[0-9]");
	}
	
	boolean validateUserName(String userName)
	{
		return userName.matches("[a-zA-Z0-9]{1,40}");
	}
	
	boolean validatePassword(String password)
	{
		return password.matches("[a-zA-Z0-9]{1,60}");
	}
	
}

// vin_text.getText()
// vehicleType_text.getText() 		- Possibly use a drop down menu instead of validating it
// make_text.getText() 				- Possibly another drop down menu
// model_text.getText() 			- Possible anotehr drop down menu
// color_text.getText() 			- Possible anotehr drop down menu
// year_text.getText()
// trim_text.getText()				- Possible anotehr drop down menu 
// mileage_text.getText()			
// price_text.getText()
