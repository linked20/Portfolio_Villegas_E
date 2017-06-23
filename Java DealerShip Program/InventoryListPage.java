import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


//import InventoryListPage.InventoryEntry;

public class InventoryListPage extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public static final String name = "InventoryListPage";
	
	Model model;
	
	DefaultTableModel tableModel;
	JTable table;
	JButton newVehicle_button;
	JButton editVehicle_button;
	JLabel title;
	
	ActionListener pageLoadDelegate;

	InventoryListPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		
		//title
		title = new JLabel("Inventory List");
		title.setFont(new Font("Seif", Font.PLAIN, 20));
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridy = 0;
		this.add(title,c);
		
		
		//table
		tableModel = new DefaultTableModel()
		{
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(790, 400));
		this.add(new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		String[] columnNames = Vehicle.vehicleAttributeNames;
		tableModel.setColumnIdentifiers(columnNames);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.removeColumn(table.getColumnModel().getColumn(0));

		//mouse listener
		table.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me)
			{
				JTable table = (JTable) me.getSource();
				if(me.getClickCount() == 2)
				{
					Point p = me.getPoint();
					int row = table.rowAtPoint(p);
					int id =  Integer.valueOf( String.valueOf( tableModel.getValueAt(row, 0) ) );
					loadInventoryPage(id);
				}
			}
		});

		newVehicle_button = new JButton("New");
		newVehicle_button.setName("newVehicle");
		newVehicle_button.setActionCommand(newVehicle_button.getName());
		newVehicle_button.addActionListener(this);
		add(newVehicle_button);
		
		editVehicle_button = new JButton("Edit");
		editVehicle_button.setName("editVehicle");
		editVehicle_button.setActionCommand(editVehicle_button.getName());
		editVehicle_button.addActionListener(this);
		add(editVehicle_button);
		
		populateInventoryList();
	}
	
	void resetPage()
	{
		tableModel.setRowCount(0);
	}
	
	void populateInventoryList()
	{
		resetPage();
		
		Vector<Vehicle> inventory = model.getInventory();
		for(Vehicle v : inventory)
		{
			addVehicle(v);
		}
	}
	
	void addVehicle(Vehicle v)
	{
		Object[] rowData = {v.id, v.vin, v.vehicleType, v.make, v.model, v.year, v.trim, v.color, v.mileage, v.price};
		tableModel.addRow(rowData);
	}
	
	void loadInventoryPage(int id)
	{// use -1 for the id when creating a new vehicle
		ActionEvent ae = new ActionEvent(this, 1, "goTo_" + InventoryPage.name + "_"+id);
		pageLoadDelegate.actionPerformed(ae);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		if(e.getActionCommand().equals(newVehicle_button.getName()))
		{
			loadInventoryPage(-1);
		}else if(e.getActionCommand().equals(editVehicle_button.getName()))
		{
			int row = table.getSelectedRow();
			int id;
			if(row >= 0)
			{
				id =  Integer.valueOf( String.valueOf( tableModel.getValueAt(row, 0) ) );
				loadInventoryPage(id);
			}
			
		}
	}
	
	
}














