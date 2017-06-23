import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EmployeeListPage extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;
	public static final String name = "EmployeeListPage";

	Model model;
	
	DefaultTableModel tableModel;
	JTable table;
	JButton newEmployee_button;
	JButton editEmployee_button;
	JLabel title;
	
	ActionListener pageLoadDelegate;

	EmployeeListPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		
		
		
		//title
				title = new JLabel("Employees");
				title.setFont(new Font("Seif", Font.PLAIN, 20));
				GridBagConstraints c = new GridBagConstraints();
				c.anchor = GridBagConstraints.PAGE_START;
				c.gridx = 0;
				c.gridy = 0;
				this.add(title,c);
		
		
		
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
		String[] columnNames = Employee.employeeAttributeNames;
		tableModel.setColumnIdentifiers(columnNames);
		table.getColumnModel().getColumn(3).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.removeColumn(table.getColumn("ID"));
		table.removeColumn(table.getColumn("SSN"));
		table.removeColumn(table.getColumn("Salary"));

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
					loadEmployeePage(id);
				}
			}
		});

		//table
		newEmployee_button = new JButton("New");
		newEmployee_button.setName("newEmployee");
		newEmployee_button.setActionCommand(newEmployee_button.getName());
		newEmployee_button.addActionListener(this);
		add(newEmployee_button);
		
		editEmployee_button = new JButton("Edit");
		editEmployee_button.setName("editEmployee");
		editEmployee_button.setActionCommand(editEmployee_button.getName());
		editEmployee_button.addActionListener(this);
		add(editEmployee_button);
		
		populateEmployeeList();
		
	}
	
	void resetPage()
	{
		tableModel.setRowCount(0);
	}
	
	void populateEmployeeList()
	{
		resetPage();
		
		Vector<Employee> employees = model.getEmployees();
		for(Employee e : employees)
		{
			addEmployee(e);
		}
	}
	
	void addEmployee(Employee e)
	{
		Object[] rowData = {e.id, e.ssn, e.firstName, e.MI, e.lastName, e.title, e.workPhone, e.personalPhone, e.salary};
		tableModel.addRow(rowData);
	}
	
	void loadEmployeePage(int id)
	{
		ActionEvent ae = new ActionEvent(this, 1, "goTo_" + EmployeePage.name + "_"+id);
		pageLoadDelegate.actionPerformed(ae);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
			
		if(e.getActionCommand().equals(newEmployee_button.getName()))
		{
				loadEmployeePage(-1);
		}
		else if(e.getActionCommand().equals(editEmployee_button.getName()))
		{
			int row = table.getSelectedRow();
			int id;
			if(row >= 0)
			{
				id =  Integer.valueOf( String.valueOf( tableModel.getValueAt(row, 0) ) );
				loadEmployeePage(id);
			}
		}
	}
	
	
	
	
	
	
}
