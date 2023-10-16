import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;

public class StudentManagements extends JFrame {
    public JFrame login = new JFrame("Login");
    String states = "0";
	protected String[] useArray = new String[5];
	private String nameUser = "";
	
    public JFrame getLoginFrame() {
        return login;
    }
	String geter(){
		return nameUser;
	}
    protected String read(String username, String password) throws IOException {
        
        File loginFile = new File("login.txt");
        if (!loginFile.exists()) {
            loginFile.createNewFile();
        }
        BufferedReader read = new BufferedReader(new FileReader(loginFile));
        String user;
        while ((user = read.readLine()) != null) {
            useArray = user.split("\t");
			
            try {
                if (useArray[0].trim().equalsIgnoreCase(username.trim()) &&
                    useArray[1].trim().equals(password.trim())) {
              
                    password = ""; // Clear the password
                    username = ""; // Clear the username
					nameUser = useArray[3];
					
                    return useArray[2];
                }
            } finally {

            }
        }
        read.close();
        return "0";
    }
	String getUserName(){
		return useArray[4];
	}

    protected void LoginPages(Students students) {
    // Create the main frame
    JFrame loginFrame = new JFrame("Login");
    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Define colors
    Color backgroundColor = new Color(240, 240, 240); // Light gray
    Color labelColor = new Color(40, 40, 40); // Dark gray
    Color buttonColor = new Color(70, 130, 180); // Steel blue

    loginFrame.getContentPane().setBackground(backgroundColor);

    // Create a panel to hold the components
    JPanel loginPanel = new JPanel();
    loginPanel.setLayout(null); // Use null layout for precise component placement

    // Create the Username field
    JTextField username = new JTextField();
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setForeground(labelColor);
    usernameLabel.setBounds(30, 30, 100, 30);
    username.setBounds(140, 30, 200, 30);

    // Create the Password field
    JPasswordField password = new JPasswordField();
    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setForeground(labelColor);
    passwordLabel.setBounds(30, 70, 100, 30);
    password.setBounds(140, 70, 200, 30);

    // Create the Login button
    JButton signIn = new JButton("Login");
	JButton helpButton = new JButton("help");
	JButton aboutDev = new JButton("About");
    signIn.setBackground(buttonColor);
    signIn.setForeground(Color.WHITE);
    signIn.setBounds(140, 110, 100, 30);
	helpButton.setBackground(buttonColor);
    helpButton.setForeground(Color.WHITE);
    helpButton.setBounds(300, 170, 80, 20);
	aboutDev.setBackground(buttonColor);
    aboutDev.setForeground(Color.WHITE);
    aboutDev.setBounds(10, 170, 80, 20);

    // Add action listener to the Login button
    signIn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            char[] passwordChars = password.getPassword();
            String passwordText = new String(passwordChars);
            String usernameText = username.getText().trim(); // Trim input

            try {
                String loginState = read(usernameText, passwordText);
// adminAfterLogin
                if (loginState.equals("students")) {
                    loginFrame.setVisible(false);
                    
                    Students rebuild = new Students();
                    loginFrame.dispose();
                    rebuild.studentAfterLogin(true, useArray[3]);
                }
				else if(loginState.equals("admin")){
					loginFrame.setVisible(false);
                    
                    Admin rebuild = new Admin();
                    loginFrame.dispose();
                    rebuild.adminAfterLogin(true);
				}
				else if (loginState.equals("0")) {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                } else {
                    loginFrame.setVisible(false);
                    Teacher teacherLogin = new Teacher(geter(), loginState);
                    teacherLogin.teacherAfterLogin(true);
                    loginFrame.dispose();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    });
	helpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
			help helpEdit = new help();
			loginFrame.setVisible(false);
			try {
				helpEdit.helpLogin(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			loginFrame.dispose();
		}
	});
	aboutDev.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
			about helpEdit = new about();
			loginFrame.setVisible(false);
			try {
				helpEdit.aboutInfo(true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			loginFrame.dispose();
		}
	});
    // Add components to the panel
    loginPanel.add(username);
    loginPanel.add(usernameLabel);
    loginPanel.add(password);
    loginPanel.add(passwordLabel);
    loginPanel.add(signIn);
	loginPanel.add(helpButton);
	loginPanel.add(aboutDev);
    // Set the size and position of the panel
    loginPanel.setBounds(0, 0, 400, 200);

    // Add the panel to the frame
    loginFrame.add(loginPanel);

    // Set frame properties
    loginFrame.setSize(400, 200);
    loginFrame.setLocationRelativeTo(null); // Center the frame on the screen
    loginFrame.setVisible(true);
}

	
	
	
}
