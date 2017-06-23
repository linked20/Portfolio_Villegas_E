import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//TODO finish implementing enter key triggers login button functionality

public class DashboardPage extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final String name = "DashboardPage";
	
	Model model;
	ActionListener pageLoadDelegate;
	
	JLabel welcomeMsg1;
	JLabel welcomeMsg2;
	JLabel logo;
	
	
	DashboardPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		this.setLayout(new GridBagLayout());
		
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		
		buildDashboardPage();	
	}
	
	void buildDashboardPage()
	{	
		welcomeMsg1 = new JLabel("Welcome to IHAA");
		welcomeMsg1.setFont(new Font("Seif", Font.PLAIN, 20));
		welcomeMsg2 = new JLabel("Click a button on the toolbar");
		welcomeMsg2.setFont(new Font("Seif", Font.PLAIN, 20));
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.LINE_START;
		c.anchor = GridBagConstraints.CENTER; 

		c.gridx = 0;
		c.gridy = 0;
		this.add(welcomeMsg1,c);
		
		c.gridx = 0;
		c.gridy = 1;
		this.add(welcomeMsg2,c);
	
		
		logo = new JLabel();
		
		try {
			BufferedImage bi = ImageIO.read(getClass().getResource(  "logo.png"));
			logo.setIcon(new ImageIcon(bi.getScaledInstance((int) (200 * 2.88), 200, Image.SCALE_SMOOTH)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		c.anchor = GridBagConstraints.BASELINE;
		c.gridx = 0;
		c.gridy = 3;
		
		this.add(logo, c);
		
	}
}