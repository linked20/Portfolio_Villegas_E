import java.awt.BorderLayout;
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

public class InventoryPage extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	public static final String name = "InventoryPage";
	
	Model model;
	int vehicleID;
	
	ActionListener pageLoadDelegate;
	
	JLabel vin_label;
	JLabel vehicleType_label;
	JLabel make_label;
	JLabel model_label;
	JLabel year_label;
	JLabel trim_label;
	JLabel color_label;
	JLabel mileage_label;
	JLabel price_label;
	JLabel title;
	
	JTextField vin_text;
	JTextField vehicleType_text;
	JTextField make_text;
	JTextField model_text;
	JTextField year_text;
	JTextField trim_text;
	JTextField color_text;
	JTextField mileage_text;
	JTextField price_text;
	
	JButton save_button;
	JButton delete_button;
	JButton sell_button;
	
	
	InventoryPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		this.setLayout(new GridBagLayout());
		
		buildContentPane();
		buildNavPane();
	}
	
	void buildContentPane()
	{
		vin_label = new JLabel("Vin");
		vehicleType_label = new JLabel("Vehicle Type");
		make_label = new JLabel("Make");
		model_label = new JLabel("Model");
		year_label = new JLabel("Year");
		trim_label = new JLabel("Trim");
		color_label = new JLabel("Color");
		mileage_label = new JLabel("Mileage");
		price_label = new JLabel("Price");
		title = new JLabel("Vehicle Information");
		title.setFont(new Font("Seif", Font.PLAIN, 20));
		
		
		vin_text = new JTextField();
		vehicleType_text = new JTextField();
		make_text = new JTextField();
		model_text = new JTextField();
		year_text = new JTextField();
		trim_text = new JTextField();
		color_text = new JTextField();
		mileage_text = new JTextField();
		price_text = new JTextField();
		
		
		Dimension textFieldSize = new Dimension(100, 25);
		
		vin_text.setPreferredSize(textFieldSize);
		vehicleType_text.setPreferredSize(textFieldSize);
		make_text.setPreferredSize(textFieldSize);
		model_text.setPreferredSize(textFieldSize);
		year_text.setPreferredSize(textFieldSize);
		trim_text.setPreferredSize(textFieldSize);
		color_text.setPreferredSize(textFieldSize);
		mileage_text.setPreferredSize(textFieldSize);
		price_text.setPreferredSize(textFieldSize);

		
		GridBagConstraints c = new GridBagConstraints();
	
		c.anchor = GridBagConstraints.LINE_START;
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		this.add(title,c);
		c.gridwidth = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		this.add(vin_label,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(vin_text,c);
		c.gridwidth = 1;

		c.gridx = 0;
		c.gridy = 3;
		this.add(make_label,c);
		
		c.gridx = 0;
		c.gridy = 4;		
		this.add(make_text,c);
		
		c.anchor = GridBagConstraints.CENTER; 	
		
		c.gridx = 1;
		c.gridy = 3;
		this.add(model_label,c);
		
		c.gridx = 1;
		c.gridy = 4;		
		this.add(model_text,c);
		
		c.gridx = 2;
		c.gridy = 3;		
		this.add(vehicleType_label,c);
		
		c.gridx = 2;
		c.gridy = 4;
		this.add(vehicleType_text,c);
		
		c.anchor = GridBagConstraints.LINE_START;	
		
		c.gridx = 0;
		c.gridy = 5;
		this.add(year_label,c);
		
		c.gridx = 0;
		c.gridy = 6;		
		this.add(year_text,c);
		
		c.anchor = GridBagConstraints.CENTER; 
		
		c.gridx = 1;
		c.gridy = 5;
		this.add(trim_label,c);
		
		c.gridx = 1;
		c.gridy = 6;
		this.add(trim_text,c);

		c.gridx = 2;
		c.gridy = 5;
		this.add(color_label,c);
		
		c.gridx = 2;
		c.gridy = 6;
		this.add(color_text,c);
		
		c.anchor = GridBagConstraints.LINE_START;	
		
		c.gridx = 0;
		c.gridy = 8;
		this.add(mileage_label,c);
		
		c.gridx = 0;
		c.gridy = 9;
		this.add(mileage_text,c);
		
		c.gridx = 1;
		c.gridy = 8;
		this.add(price_label,c);
		
		c.gridx = 1;
		c.gridy = 9;
		this.add(price_text,c);
		
	}
	
	void buildNavPane()
	{
		save_button = new JButton("Save");
		save_button.setActionCommand("save");
		save_button.addActionListener(this);
		
		delete_button = new JButton("Delete");
		delete_button.setActionCommand("delete");
		delete_button.addActionListener(this);
		
		sell_button = new JButton("Sell");
		sell_button.setName("sell");
		sell_button.setActionCommand(sell_button.getName());
		sell_button.addActionListener(this);
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 2;
		c.gridy = 10;
		this.add(sell_button, c);
		
		c.gridx = 3;
		c.gridy = 10;
		this.add(save_button, c);
		
		c.gridx = 4;
		c.gridy = 10;
		this.add(delete_button, c);
	}
	
	void resetPage()
	{
		vin_text.setText("");
		vehicleType_text.setText("");
		make_text.setText("");
		model_text.setText("");
		year_text.setText("");
		trim_text.setText("");
		color_text.setText("");
		mileage_text.setText( String.valueOf("") );
		price_text.setText( String.valueOf("") );
		
		vin_text.setBackground(Color.white);
		vehicleType_text.setBackground(Color.white);
		make_text.setBackground(Color.white);
		model_text.setBackground(Color.white);
		color_text.setBackground(Color.white);
		year_text.setBackground(Color.white);
		trim_text.setBackground(Color.white);
		mileage_text.setBackground(Color.white);
		price_text.setBackground(Color.white);
	}
	
	public void loadVehicleInformation(int id)
	{
		resetPage();
		Vehicle vehicle = model.getVehicle(id);
		vehicleID = vehicle.id;
		
		if(vehicle.id != -1)
		{
			vin_text.setText(vehicle.vin);
			vehicleType_text.setText(vehicle.vehicleType);
			make_text.setText(vehicle.make);
			model_text.setText(vehicle.model);
			year_text.setText(String.valueOf(vehicle.year));
			trim_text.setText(vehicle.trim);
			color_text.setText(vehicle.color);
			mileage_text.setText( String.valueOf(vehicle.mileage) );
			price_text.setText( String.valueOf(vehicle.price) );
		}else
		{
			vin_text.setText("");
			vehicleType_text.setText("");
			make_text.setText("");
			model_text.setText("");
			year_text.setText("");
			trim_text.setText("");
			color_text.setText("");
			mileage_text.setText("");
			price_text.setText("");
		}
		
		
		
	}
	
	boolean saveVehicle()
	{
		InputValidator iv = InputValidator.sharedInstance;
		boolean valid = true;
		vin_text.setBackground(Color.white);
		vehicleType_text.setBackground(Color.white);
		make_text.setBackground(Color.white);
		model_text.setBackground(Color.white);
		color_text.setBackground(Color.white);
		year_text.setBackground(Color.white);
		trim_text.setBackground(Color.white);
		mileage_text.setBackground(Color.white);
		price_text.setBackground(Color.white);

		if(!iv.validateVehicleVIN(vin_text.getText()))
		{
			vin_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleType(vehicleType_text.getText()))
		{
			vehicleType_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleMakeModelColor(make_text.getText()))
		{
			make_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleMakeModelColor(model_text.getText()))
		{
			model_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleMakeModelColor(color_text.getText()))
		{
			color_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleYear(year_text.getText()))
		{
			year_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleTrim(trim_text.getText()))
		{
			trim_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehicleMileage(mileage_text.getText()))
		{
			mileage_text.setBackground(Color.red);
			valid = false;
		}
		if(!iv.validateVehiclePrice(price_text.getText()))
		{
			price_text.setBackground(Color.red);
			valid = false;
		}

		if(valid)
		{
			model.saveVehicle(new Vehicle( vehicleID, vin_text.getText(), vehicleType_text.getText(), make_text.getText(), model_text.getText(), Integer.valueOf(year_text.getText()),
					trim_text.getText(), color_text.getText(), Integer.valueOf(mileage_text.getText()), Integer.valueOf(price_text.getText())));
		}

		return valid;
	}
	
	void deleteVehicle()
	{
		model.deleteVehicle(vehicleID);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String command = e.getActionCommand();
		
		if(command.equals("save"))
		{
			if(saveVehicle())
				if(JOptionPane.showConfirmDialog(this, "Would you like to contiue editing records?", "Continue?", JOptionPane.YES_NO_OPTION) == 0)
				{//navigate to list page
						ActionEvent ae = new ActionEvent(this, 1, "goTo_" + InventoryListPage.name);
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
				deleteVehicle();
				ActionEvent ae = new ActionEvent(this, 1, "goTo_" + InventoryListPage.name);
				pageLoadDelegate.actionPerformed(ae);
			}
		}else if(command.equals(sell_button.getName()))
		{
			if(vehicleID == -1)
			{
				JOptionPane.showConfirmDialog(this, "Please save vehicle before selling.", "-_-", JOptionPane.PLAIN_MESSAGE);
			}else
			{
				ActionEvent ae = new ActionEvent(this, 1, "goTo_" + SaleRecordPage.name + "_new_" + vehicleID);
				pageLoadDelegate.actionPerformed(ae);
			}
		}
	}
}










