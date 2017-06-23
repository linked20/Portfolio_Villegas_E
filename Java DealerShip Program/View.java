import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class View extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	Model model;
	
	//nav bar
	JPanel navBarPane;
	TreeMap<String, JButton> navButtons;
	TreeMap<String, String> navButton_titles;
	JButton logOut_button;
	Dimension buttonSize = new Dimension(25, 25);
	
	//content pane
	JPanel contentPane;
	CardLayout contentPaneCardLayout;
	
	String homePage = InventoryListPage.name; //TODO: make this do something
	
	
	View()
	{
		super("In-house Algorithm Applicaiton");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.model = Model.sharedInstance;
		
		buildNavBar();
		buildContentPane();

		this.setLayout(new BorderLayout());
		add(navBarPane, BorderLayout.NORTH);
		add(contentPane, BorderLayout.CENTER);
		this.setSize(800, 555);
		this.setVisible(true);
		this.setResizable(false);
		
		loadPage("LoginPage");
	}
	
	void buildNavBar()
	{	
		// navigation buttons
		navButtons = new TreeMap<String, JButton>();
		navButton_titles = new TreeMap<String, String>(); //<name on button, name of page>
		navButton_titles.put("Dashboard", DashboardPage.name);
		navButton_titles.put("Inventory List", InventoryListPage.name);
		navButton_titles.put("Employee Management", EmployeeListPage.name);
		navButton_titles.put("Sale Records", SaleRecordListPage.name);
		
		navBarPane = new JPanel(new BorderLayout());
		navBarPane.setBackground(Color.LIGHT_GRAY);
		((BorderLayout) navBarPane.getLayout()).setVgap(0);
		
		JPanel navBarPageButtons = new JPanel();
		navBarPageButtons.setBackground(Color.LIGHT_GRAY);
		((FlowLayout) navBarPageButtons.getLayout()).setVgap(0);

		for(Map.Entry<String, String> entry : navButton_titles.entrySet())
		{
			JButton newButton = new JButton(entry.getKey());
			newButton.setName(entry.getKey());
			newButton.addActionListener(this);
			newButton.setActionCommand("goTo_" + entry.getValue());
			newButton.setFocusable(false);
			
			try {
				BufferedImage bi = ImageIO.read(getClass().getResource( entry.getKey() + ".png"));
				newButton.setIcon(new ImageIcon(bi.getScaledInstance(buttonSize.width, buttonSize.width, Image.SCALE_SMOOTH)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			navButtons.put(entry.getKey(), newButton);
			navBarPageButtons.add(newButton);
		}
		navBarPane.add(navBarPageButtons, BorderLayout.LINE_START);
				
		
		//logout button
		logOut_button = new JButton("Log Out");

		try {
			BufferedImage bi = ImageIO.read(getClass().getResource("logout.png"));
			logOut_button.setIcon(new ImageIcon(bi.getScaledInstance(buttonSize.width, buttonSize.width, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logOut_button.setName("logOut");
		logOut_button.setActionCommand(logOut_button.getName());
		logOut_button.addActionListener(this);
		logOut_button.setFocusable(false);
		navBarPane.add(logOut_button, BorderLayout.LINE_END);
	}
	
	void buildContentPane()
	{
		contentPane = new JPanel(new CardLayout());
		contentPane.add(new LoginPage(this), LoginPage.name);
		contentPane.add(new InventoryListPage(this), InventoryListPage.name);
		contentPane.add(new InventoryPage(this), InventoryPage.name);
		contentPane.add(new EmployeeListPage(this), EmployeeListPage.name);
		contentPane.add(new EmployeePage(this), EmployeePage.name);
		contentPane.add(new SaleRecordListPage(this), SaleRecordListPage.name);
		contentPane.add(new SaleRecordPage(this), SaleRecordPage.name);
		contentPane.add(new DashboardPage(this), DashboardPage.name);
	}
	
	void loadPage(String named)
	{
		//check for privileges
		
		CardLayout cl = (CardLayout) contentPane.getLayout();
		if(model.isLoggedIn())
			cl.show(contentPane, named);
		else
			cl.show(contentPane, LoginPage.name);
	}

	private Object getInstanceOfClass(String name)
	{
		Object pageInstance = null;
		for(Component c : contentPane.getComponents())
		{
			if(c.getName() == name)
			{
				pageInstance = c;
			}
		}
		return pageInstance;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().matches("goTo_" + InventoryPage.name + "_.+"))
		{//loading a vehicle to the inventory page    command format: "goTo_InventoryPage_VIN"
			String[] command = e.getActionCommand().split("_", 3);
			int id = Integer.valueOf(command[2]);
			loadPage(command[1]);
			
			InventoryPage pageInstance = (InventoryPage) getInstanceOfClass(InventoryPage.name);
			if(pageInstance != null)
			{
				pageInstance.loadVehicleInformation(id);
			}else
			{
				System.out.println("View::ActionPerformed - Could not load vehicle to inventory page");
			}
			
			
		}
		else if(e.getActionCommand().matches("goTo_" + EmployeePage.name + "_.+"))
		{//loading an employee to the employee page    command format: "goTo_EmployeePage_ID"
			if(model.canViewEmployeeListPage())
			{
				String[] command = e.getActionCommand().split("_", 3);
				int id = Integer.valueOf(command[2]);
				loadPage(command[1]);

				EmployeePage pageInstance = (EmployeePage) getInstanceOfClass(EmployeePage.name);
				if(pageInstance != null)
				{
					pageInstance.loadEmployeeInformation(id);
				}else
				{
					System.out.println("View::ActionPerformed - Could not load employee to inventory page");
				}
			}else
			{
				JOptionPane.showConfirmDialog(this, "I can't let you do that, " + model.getCurrentUser().firstName + ".", "Insufficient Privileges", JOptionPane.PLAIN_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("goTo_" + InventoryListPage.name))
		{
			String[] command = e.getActionCommand().split("_", 2);
			loadPage(InventoryListPage.name);
			
			InventoryListPage ilp = (InventoryListPage) getInstanceOfClass(InventoryListPage.name);
			ilp.populateInventoryList();
		}
		else if(e.getActionCommand().equals("goTo_" + EmployeeListPage.name))
		{
			String[] command = e.getActionCommand().split("_", 2);
			loadPage(EmployeeListPage.name);
			
			EmployeeListPage elp = (EmployeeListPage) getInstanceOfClass(EmployeeListPage.name);
			elp.populateEmployeeList();
		}
		else if(e.getActionCommand().matches("goTo_" + SaleRecordPage.name + "_.+_.+"))
		{
			String[] command = e.getActionCommand().split("_", 4);
			int id = Integer.valueOf(command[3]);
			loadPage(command[1]);
			
			SaleRecordPage pageInstance = (SaleRecordPage) getInstanceOfClass(SaleRecordPage.name);
			if(pageInstance != null)
			{
				if(command[2].equals("new"))
					pageInstance.createNewSaleRecord(id);
				else if(command[2].equals("existing"))
					pageInstance.loadSaleRecordInformation(id);
			}else
			{
				System.out.println("View::ActionPerformed - Could not load instance of SaleRecordPage");
			}	
		}else if(e.getActionCommand().equals("goTo_" + SaleRecordListPage.name)) //----------
		{
			String[] command = e.getActionCommand().split("_", 2);
			loadPage(SaleRecordListPage.name);
			
			SaleRecordListPage esrlp = (SaleRecordListPage) getInstanceOfClass(SaleRecordListPage.name);
			esrlp.populateSaleRecordList();
		}
		else if(e.getActionCommand().equals("goTo_" + DashboardPage.name))
		{
			loadPage(DashboardPage.name);
		}
		else if(e.getActionCommand().matches("goTo_.+"))
		{
			System.out.println("View::actionPerformed() - could not match goTo_ comand \"" + e.getActionCommand() + "\"");
		}
		else if(e.getActionCommand().equals(logOut_button.getName()))
		{
			model.logOut();
			loadPage(LoginPage.name);
		}
	}
}











