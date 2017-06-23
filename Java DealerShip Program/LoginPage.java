import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


//TODO finish implementing enter key triggers login button functionality

public class LoginPage extends JPanel implements ActionListener, KeyListener {
	
	
	private static final long serialVersionUID = 1L;
	public static final String name = "LoginPage";
	
	Model model;
	ActionListener pageLoadDelegate;
	
	JLabel username_label;
	JLabel password_label;
	JTextField username_text;
	JPasswordField password_text;
	JButton login_button;
	
	
	LoginPage(ActionListener pageLoadDelegate)
	{
		this.setName(name);
		this.setLayout(new GridBagLayout());
		
		this.model = Model.sharedInstance;
		this.pageLoadDelegate = pageLoadDelegate;
		
		buildLoginPage();	
	}
	
	void buildLoginPage()
	{
		this.addKeyListener(this);
		
		Dimension labelSize = new Dimension(100, 25);
		Dimension textAreaSize = new Dimension(100, 25);
		Dimension buttonSize = new Dimension(100, 25);
		
		
		
		
		username_label = new JLabel("Username:");
		password_label = new JLabel("Password:");
		username_text = new JTextField();
		password_text = new JPasswordField();
		login_button = new JButton("Login");
		login_button.setActionCommand("attemptLogin");
		login_button.addActionListener(this);
		username_label.setPreferredSize(labelSize);
		password_label.setPreferredSize(labelSize);
		username_text.setPreferredSize(textAreaSize);
		password_text.setPreferredSize(textAreaSize);
		login_button.setPreferredSize(buttonSize);
		
		
		//enable tab between fields
		username_text.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		password_text.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		
		//TODO layout stuff
		GridBagConstraints c = new GridBagConstraints();
		
		
		this.add(username_label);
		this.add(username_text);
		this.add(password_label);
		this.add(password_text);
		this.add(login_button);
	}
	
	void attemptLogin()
	{
		if(model.logIn(username_text.getText(), String.valueOf(password_text.getPassword())))
		{
			username_text.setBackground(Color.white);
			password_text.setBackground(Color.white);
			username_text.setText("");
			password_text.setText("");
			ActionEvent ae = new ActionEvent(this, 1, "goTo_" + DashboardPage.name);
			pageLoadDelegate.actionPerformed(ae);
		}else
		{
			username_text.setBackground(Color.red);
			password_text.setBackground(Color.red);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "attemptLogin")
		{
			attemptLogin();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ENTER)
		{
			login_button.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
}









