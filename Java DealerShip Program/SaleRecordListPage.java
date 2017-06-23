import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;


public class SaleRecordListPage extends JPanel implements ActionListener {


	private static final long serialVersionUID = 1L;
	public static final String name = "SaleRecordListPage";

	Model model;
	
	DefaultTableModel tableModel;
	JTable table;
	JButton newSaleRecord_button;
	JButton editSaleRecord_button;
	JLabel title;
	
	// Column widths
	TreeMap<String, Integer> columnWidths;
	
	ActionListener pageLoadDelegate;

	SaleRecordListPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		
		
		//title
		title = new JLabel("Sales Records");
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
		String[] columnNames = SaleRecord.saleRecordNames; // change to ?
		tableModel.setColumnIdentifiers(columnNames);
		
		for(int i=0; i<4; i++)
			table.removeColumn(table.getColumnModel().getColumn(0));
		
		// column widths
		columnWidths = new TreeMap<String, Integer>();
		columnWidths.put("M", 10);
		columnWidths.put("Address", 225);
		columnWidths.put("Price", 50);
		columnWidths.put("Year", 20);
		columnWidths.put("Month", 10);
		columnWidths.put("Day", 10);
		
		TableColumnModel tm = table.getColumnModel();
		
		for(Map.Entry<String, Integer> entry : columnWidths.entrySet())
			tm.getColumn(tm.getColumnIndex(entry.getKey())).setPreferredWidth(entry.getValue());
	

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
					loadSaleRecordPage(id);
				}
			}
		});

		newSaleRecord_button = new JButton("New");
		newSaleRecord_button.setName("newSaleRecord");
		newSaleRecord_button.setActionCommand(newSaleRecord_button.getName());
		newSaleRecord_button.addActionListener(this);
//		this.add(newSaleRecord_button);
		
		editSaleRecord_button = new JButton("Edit");
		editSaleRecord_button.setName(editSaleRecord_button.getName());
		editSaleRecord_button.setActionCommand(editSaleRecord_button.getName());
		editSaleRecord_button.addActionListener(this);
		this.add(editSaleRecord_button);
		
		populateSaleRecordList();
		
	}
	
	void resetPage()
	{
		tableModel.setRowCount(0);
	}
	
	void populateSaleRecordList() 
	{
		resetPage();
		
		Vector<SaleRecord> SaleRecords = model.getSaleRecords();
		for(SaleRecord s : SaleRecords)
		{
			addRecord(s);
		}
	}
	
	void addRecord(SaleRecord s)
	{
		Object[] rowData = { s.saleRecordID, s.employeeID, s.customerID, s.vehicleID, s.firstName, s.middleInitial, s.lastName, s.phone, 
				s.address, s.salePrice, s.year, s.month, s.day};
		tableModel.addRow(rowData);
	}

	void loadSaleRecordPage(int id)
	{
		ActionEvent ae = new ActionEvent(this, 1, "goTo_" + SaleRecordPage.name + "_" + "existing_" + id); 
		pageLoadDelegate.actionPerformed(ae);
	}

	void createBlankRow()
	{
		Object[] rowData = {"-1","--", "--", "--", "--", "--", "--", "--", "--", "--", "--"};
		tableModel.addRow(rowData);
		tableModel.fireTableDataChanged();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		if(e.getActionCommand().equals(newSaleRecord_button.getName()))
		{
			loadSaleRecordPage(-1);
		}
		else if(e.getActionCommand().equals(editSaleRecord_button.getActionCommand()));
		{
			int row = table.getSelectedRow();
			int id;
			if(row >= 0)
			{
				id =  Integer.valueOf( String.valueOf( tableModel.getValueAt(row, 0) ) );
				loadSaleRecordPage(id);
			}
		}
	}
	
	
	
	
	
	
}