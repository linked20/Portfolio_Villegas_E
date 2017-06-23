import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaleRecordPage extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final String name = "SaleRecordPage";

	ActionListener pageLoadDelegate;
	
	Model model;
	int saleRecordID;
	int vehicleID;
	
	JLabel firstName_label;
	JLabel middleInitial_label;
	JLabel lastName_label;
	JLabel phone_label;
	JLabel address_label;
	JLabel salePrice_label;
	JLabel year_label;
	JLabel month_label;
	JLabel day_label;
	JLabel title;

	JTextField firstName_text;
	JTextField middleInitial_text;
	JTextField lastName_text;
	JTextField phone_text;
	JTextField address_text;
	JTextField salePrice_text;
	JTextField year_text;
	JTextField month_text;
	JTextField day_text;
	
	JButton save_button;
	JButton delete_button;

	
	SaleRecordPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;

		buildContentPane();
		buildNavPane();
	}
	
	void createNewSaleRecord(int vehicleID)
	{
		resetPage();
		Vehicle v = model.getVehicle(vehicleID);
		this.vehicleID = vehicleID;
		loadSaleRecordInformation(-1);
		salePrice_text.setText(String.valueOf(v.price));
	}
	
	void loadSaleRecordInformation(int srID)
	{
		resetPage();
		SaleRecord sr = model.getSaleRecord(srID); //getSales
		saleRecordID = sr.saleRecordID;

		if(sr.saleRecordID != -1)
		{
			firstName_text.setText(sr.firstName);
			middleInitial_text.setText(sr.middleInitial);
			lastName_text.setText(sr.lastName);
			phone_text.setText(sr.phone);
			address_text.setText(sr.address);
			salePrice_text.setText( String.valueOf(sr.salePrice ));
			year_text.setText(String.valueOf(sr.year));
			month_text.setText(String.valueOf(sr.month));
			day_text.setText(String.valueOf(sr.day));
		}else
		{
			firstName_text.setText("");
			middleInitial_text.setText("");
			lastName_text.setText("");
			phone_text.setText("");
			address_text.setText("");
			salePrice_text.setText("");
			year_text.setText("");
			month_text.setText("");
			day_text.setText("");
		}
	}
	
	private void resetPage()
	{
		firstName_text.setText("");
		middleInitial_text.setText("");
		lastName_text.setText("");
		phone_text.setText("");
		address_text.setText("");
		salePrice_text.setText("");
		year_text.setText("");
		month_text.setText("");
		day_text.setText("");
		
		firstName_text.setBackground(Color.white);
		middleInitial_text.setBackground(Color.white);
		lastName_text.setBackground(Color.white);
		phone_text.setBackground(Color.white);
		address_text.setBackground(Color.white);
		salePrice_text.setBackground(Color.white);
		year_text.setBackground(Color.white);
		month_text.setBackground(Color.white);
		day_text.setBackground(Color.white);
	}
	
	private void buildContentPane()
	{
		firstName_label = new JLabel("First");
		middleInitial_label = new JLabel("M");
		lastName_label = new JLabel("Last");
		phone_label = new JLabel("Phone");
		address_label= new JLabel("Address");
		salePrice_label = new JLabel("Sale Price");
		year_label = new JLabel("Year");
		month_label = new JLabel("Month");
		day_label = new JLabel("Day");
		title = new JLabel("Sale Record");
		title.setFont(new Font("Seif", Font.PLAIN, 20));

		
		firstName_text = new JTextField();
		middleInitial_text = new JTextField();
		lastName_text = new JTextField();
		phone_text = new JTextField();
		address_text = new JTextField();
		salePrice_text = new JTextField();
		year_text = new JTextField();
		month_text = new JTextField();
		day_text= new JTextField();
		
		
		Dimension textFieldSize_regular = new Dimension(100, 25);
		Dimension textFieldSize_large = new Dimension(250, 25);
		
		firstName_text.setPreferredSize(textFieldSize_regular);
		middleInitial_text.setPreferredSize(textFieldSize_regular);
		lastName_text.setPreferredSize(textFieldSize_regular);
		phone_text.setPreferredSize(textFieldSize_regular);
		address_text.setPreferredSize(textFieldSize_large);
		salePrice_text.setPreferredSize(textFieldSize_regular);
		year_text.setPreferredSize(textFieldSize_regular);
		month_text.setPreferredSize(textFieldSize_regular);
		day_text.setPreferredSize(textFieldSize_regular);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LINE_START; 
		
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		this.add(title,c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(firstName_label,c);
		
		c.gridx = 0;
		c.gridy = 2;
		this.add(firstName_text,c);
		
		c.gridx = 1;
		c.gridy = 1;
		this.add(middleInitial_label,c);
		
		c.gridx = 1;
		c.gridy = 2;
		this.add(middleInitial_text,c);
		
		c.gridx = 2;
		c.gridy = 1;
		this.add(lastName_label,c);
		
		c.gridx = 2;
		c.gridy = 2;
		this.add(lastName_text,c);
		
		c.gridx = 0;
		c.gridy = 4;
		this.add(phone_label,c);
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(phone_text,c);
		
		c.gridx = 1;
		c.gridy = 4;
		this.add(address_label,c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 2;
		this.add(address_text,c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 7;
		this.add(salePrice_label,c);
		
		c.gridx = 0;
		c.gridy = 8;
		this.add(salePrice_text,c);
		
		c.gridx = 0;
		c.gridy = 10;
		this.add(year_label,c);
		
		c.gridx = 0;
		c.gridy = 11;
		this.add(year_text,c);
		
		c.gridx = 1;
		c.gridy = 10;
		this.add(month_label,c);
		
		c.gridx = 1;
		c.gridy = 11;
		this.add(month_text,c);
		
		c.gridx = 2;
		c.gridy = 10;
		this.add(day_label,c);
		
		c.gridx = 2;
		c.gridy = 11;
		this.add(day_text,c);
	}
	
	private void buildNavPane()
	{
		save_button = new JButton("Save");
		save_button.setActionCommand("save");
		save_button.addActionListener(this);
		
		delete_button = new JButton("Delete");
		delete_button.setActionCommand("delete");
		delete_button.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 2;
		c.gridy = 12;
		this.add(save_button,c);
		
		c.gridx = 3;
		c.gridy = 12;
		this.add(delete_button,c);
		
	}
	
	private boolean saveSaleRecord()
	{
		InputValidator iv = InputValidator.sharedInstance;
		boolean valid = true;
		firstName_text.setBackground(Color.white);
		middleInitial_text.setBackground(Color.white);
		lastName_text.setBackground(Color.white);
		phone_text.setBackground(Color.white);
		address_text.setBackground(Color.white);
		salePrice_text.setBackground(Color.white);
		year_text.setBackground(Color.white);
		month_text.setBackground(Color.white);
		day_text.setBackground(Color.white);

		if(!iv.validateName(firstName_text.getText()))
		{
			firstName_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateMinit(middleInitial_text.getText()))
		{
			middleInitial_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateName(lastName_text.getText()))
		{
			lastName_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validatePhoneNumber(phone_text.getText()))
		{
			phone_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateAddress(address_text.getText()))
		{
			address_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehiclePrice(salePrice_text.getText()))
		{
			salePrice_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleYear(year_text.getText()))
		{
			year_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateMonth(month_text.getText()))
		{
			month_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateDay(day_text.getText()))
		{
			day_text.setBackground(Color.red);
			valid = false;
		}



		if(valid)
		{
			model.saveSaleRecord(new SaleRecord(saleRecordID, model.getCurrentUser().id, model.getSaleRecord(saleRecordID).customerID, vehicleID, firstName_text.getText(), middleInitial_text.getText(), 
					lastName_text.getText(), phone_text.getText(), address_text.getText(), Integer.valueOf(salePrice_text.getText()),
					Integer.valueOf(year_text.getText()), Integer.valueOf(month_text.getText()), Integer.valueOf(day_text.getText())   ));
		}

		return valid; 
	
	}
	
	private void deleteSaleRecord()
	{
		model.deleteSaleRecord(saleRecordID);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		if(command.equals("save"))
		{
			if(saveSaleRecord())
				if(JOptionPane.showConfirmDialog(this, "Would you like to contiue editing records?", "Continue?", JOptionPane.YES_NO_OPTION) == 0)
				{//navigate to list page
					
					ActionEvent ae = new ActionEvent(this, 1, "goTo_" + SaleRecordListPage.name);
					pageLoadDelegate.actionPerformed(ae);
				}else
				{//navigate to dashboard
					ActionEvent ae = new ActionEvent(this, 1, "goTo_" + DashboardPage.name);
					pageLoadDelegate.actionPerformed(ae);
				}
		}else if(command.equals("delete"))
		{
			if(JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete Confirmation", JOptionPane.YES_NO_OPTION) == 0)
			{
				deleteSaleRecord();
				ActionEvent ae = new ActionEvent(this, 1, "goTo_" + SaleRecordListPage.name);
				pageLoadDelegate.actionPerformed(ae);
			}	
		}
		
	}
	
}










