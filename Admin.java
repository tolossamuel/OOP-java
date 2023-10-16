import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private List<Object[]> teacherList;
    private List<Object[]> studentList;
    JFrame adminFrame;

    Admin() throws IOException {
        studentList = new ArrayList<>();
        teacherList = new ArrayList<>();
		readTeacher();
		readStudent();
        initializeFrame();
        homePage();
    }

    void initializeFrame() {
        adminFrame = new JFrame("Admin Control");
        adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminFrame.setSize(600, 400);
        adminFrame.setLocationRelativeTo(null); // Center the frame on the screen
		adminFrame.setVisible(true);
    }

    void homePage() {
        adminFrame.getContentPane().removeAll();

        JButton addStudents = new JButton("Add Students");
        JButton logout = new JButton("Logout");
        JButton staticButtons = new JButton("Statistics");
        JButton addTeachers = new JButton("Add Teachers");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(logout);
        buttonPanel.add(addStudents);
        buttonPanel.add(addTeachers);
        buttonPanel.add(staticButtons);

        addStudents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudents();
            }
        });
		addTeachers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTeachers(); // Call the addTeachers() function when "Add Teachers" button is clicked
			}
		});
		staticButtons.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					staticData();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				} // Call the addTeachers() function when "Add Teachers" button is clicked
			}
		});
		logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                try {
                    MainClass.main(null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel studentListForm = new JPanel();
        studentListForm.setLayout(new BoxLayout(studentListForm, BoxLayout.Y_AXIS));

        studentListForm.add(new JLabel("Dashboard"));

        JLabel nameLabel = new JLabel("<html><h3>Teachers Staff</h3></html>");
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(nameLabel);

        String[] columnNames = {"Name Teachers","Username", "Course"};
        Object[][] teacherData = teacherList.toArray(new Object[0][]);
        JTable courseTable = new JTable(teacherData, columnNames);
        JScrollPane teacherScrollPane = new JScrollPane(courseTable);
        teacherScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(teacherScrollPane);

        JLabel nameLabel2 = new JLabel("<html><h3>Students Staff</h3></html>");
        nameLabel2.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(nameLabel2);

        String[] studentColumnNames = {"Name Students","Username","Baches"};
        Object[][] studentData = studentList.toArray(new Object[0][]);
        JTable studentTable = new JTable(studentData, studentColumnNames);
        JScrollPane studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(studentScrollPane);

        adminFrame.add(buttonPanel, BorderLayout.NORTH);
        adminFrame.add(studentListForm, BorderLayout.CENTER);

        adminFrame.revalidate();
        adminFrame.repaint();
    }

    void addStudents() {
		adminFrame.getContentPane().removeAll();
	
		JPanel addStudentPanel = new JPanel();
		addStudentPanel.setLayout(null);
	
		JLabel welcomeLabel = new JLabel("<html><h2>Add Students</h2></html>");
		welcomeLabel.setBounds(150, 10, 200, 30);
	
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 50, 100, 30);
		JTextField nameField = new JTextField();
		nameField.setBounds(150, 50, 200, 30);
	
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 100, 100, 30);
		JTextField usernameField = new JTextField();
		usernameField.setBounds(150, 100, 200, 30);
	
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 150, 100, 30);
		JTextField passwordField = new JTextField();
		passwordField.setBounds(150, 150, 200, 30);
	
		JLabel yearLabel = new JLabel("Baches of Study:");
		yearLabel.setBounds(10, 200, 100, 30);
		JTextField yearField = new JTextField();
		yearField.setBounds(150, 200, 200, 30);
	
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(150, 250, 100, 30);
	
		JButton backButton = new JButton("Back");
		backButton.setBounds(10, 250, 100, 30);
	
		addStudentPanel.add(welcomeLabel);
		addStudentPanel.add(nameLabel);
		addStudentPanel.add(nameField);
		addStudentPanel.add(usernameLabel);
		addStudentPanel.add(usernameField);
		addStudentPanel.add(passwordLabel);
		addStudentPanel.add(passwordField);
		addStudentPanel.add(yearLabel);
		addStudentPanel.add(yearField);
		addStudentPanel.add(saveButton);
		addStudentPanel.add(backButton);
	
		adminFrame.add(addStudentPanel);
	
		adminFrame.revalidate();
		adminFrame.repaint();
	
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String username = usernameField.getText();
				String password = passwordField.getText();
				String baches = yearField.getText();
				int year;
				if (!username.isEmpty() && !password.isEmpty() && !name.isEmpty() && !baches.isEmpty()) {
					try {
						year = Integer.parseInt(baches);
						saveStudents(name, username,password, Integer.toString(year));
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Baches of Study must be an integer.");
						return;
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
					homePage();
				}
				else {
					JOptionPane.showMessageDialog(null, "fill all spaces");
					return;
				}
	
					
			}
		});
	
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Simply go back to the home page without saving
				homePage();
			}
		});
	}
	void addTeachers() {
		adminFrame.getContentPane().removeAll();
	
		JPanel addTeacherPanel = new JPanel();
		addTeacherPanel.setLayout(null);
	
		JLabel welcomeLabel = new JLabel("<html><h2>Add Teachers</h2></html>");
		welcomeLabel.setBounds(150, 10, 200, 30);
	
		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(10, 50, 100, 30);
		JTextField nameField = new JTextField();
		nameField.setBounds(150, 50, 200, 30);
	
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(10, 100, 100, 30);
		JTextField usernameField = new JTextField();
		usernameField.setBounds(150, 100, 200, 30);
	
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(10, 150, 100, 30);
		JTextField passwordField = new JTextField();
		passwordField.setBounds(150, 150, 200, 30);
	
		JLabel courseLabel = new JLabel("Course:");
		courseLabel.setBounds(10, 200, 100, 30);
		String[] courseOptions = {"Fundamental of Software Engineering", "DLD", "Object Oriented Programming", "History", "Psychology"};
		JComboBox<String> courseComboBox = new JComboBox<>(courseOptions);
		courseComboBox.setBounds(150, 200, 200, 30);
	
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(150, 250, 100, 30);
	
		addTeacherPanel.add(welcomeLabel);
		addTeacherPanel.add(nameLabel);
		addTeacherPanel.add(nameField);
		addTeacherPanel.add(usernameLabel);
		addTeacherPanel.add(usernameField);
		addTeacherPanel.add(passwordLabel);
		addTeacherPanel.add(passwordField);
		addTeacherPanel.add(courseLabel);
		addTeacherPanel.add(courseComboBox);
		addTeacherPanel.add(saveButton);

		JButton backButton = new JButton("Back");
		backButton.setBounds(10, 250, 100, 30);
		addTeacherPanel.add(backButton);
		adminFrame.add(addTeacherPanel);
	
		adminFrame.revalidate();
		adminFrame.repaint();
	
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String username = usernameField.getText();
				String password = passwordField.getText();
				String course = (String) courseComboBox.getSelectedItem();
	
				if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields.");
					return;
				}
	
				try {
					saveTeachers(name, username, password,course);
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
	
				homePage();
			}
		});
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Simply go back to the home page without saving
				homePage();
			}
		});
	}
	
	
	
	private int countLinesInFile(String fileName) {
		int lineCount = 0;
	
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				lineCount++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return lineCount;
	}
	private void readStudent() throws IOException{
		File file = new File("students.txt");
		if (!file.exists()) {
            return;
        }
		BufferedReader readFile = new BufferedReader(new FileReader(file));
		String data;

		while ((data = readFile.readLine()) != null) {
			String[] dataRead = data.split("\t");

			if (dataRead.length >= 3) {
				studentList.add(new Object[]{dataRead[0],dataRead[1],dataRead[2]});
				
			}
		}

	}
	private void readTeacher() throws IOException{
		File file = new File("teachers.txt");
		if (!file.exists()) {
            return;
        }
		BufferedReader readFile = new BufferedReader(new FileReader(file));
		String data;

		while ((data = readFile.readLine()) != null) {
			String[] dataRead = data.split("\t");

			if (dataRead.length >= 3) {
				teacherList.add(new Object[]{dataRead[0],dataRead[1],dataRead[2]});
				
			}
		}

	}
    
	private void saveStudents(String name,String username,String password, String baches) throws IOException{
		File studentFil = new File("students.txt");
		if (!studentFil.exists()){
			studentFil.createNewFile();
		}
		FileWriter saveS = new FileWriter(studentFil,true);
		if (baches.equals("1")){
			baches += "st Year";
		}
		else if (baches.equals("2")){
			baches += "nd Year";
		}
		else if (baches.equals("3")){
			baches += "rd Year";
		}
		else{
			baches += "th Year";
		}
		saveS.write(name + "\t" + username + "\t" + baches + "\n");
		saveS.close();
		File passwordSave = new File("login.txt");
		if (!passwordSave.exists()){
			passwordSave.createNewFile();
		}
		FileWriter passwordW = new FileWriter(passwordSave,true);
		passwordW.write(username + "\t" + password + "\t" + "students" + "\t" + name + "\n");
		passwordW.close();
	}
	private void saveTeachers(String name,String username,String password, String course) throws IOException{
		File teachersFil = new File("teachers.txt");
		if (!teachersFil.exists()){
			teachersFil.createNewFile();
		}
		FileWriter saveS = new FileWriter(teachersFil,true);
		
		saveS.write(name + "\t" + username + "\t" + course + "\n");
		saveS.close();
		File passwordSave = new File("login.txt");
		if (!passwordSave.exists()){
			passwordSave.createNewFile();
		}
		FileWriter passwordW = new FileWriter(passwordSave,true);
		passwordW.write(username + "\t" + password + "\t" + course + "\t" + name + "\n");
		passwordW.close();
	}
	public void adminAfterLogin(boolean states) throws IOException {
        this.homePage();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(states);
        
    }
	private String counters(String course,String grad) throws IOException{
		int counters = 0;
		File gradFile = new File(course +"_Grad.txt");
		if (!gradFile.exists()){
			return "0";
		}
		BufferedReader newReader = new BufferedReader(new FileReader(gradFile));
		String data;
		
		while((data = newReader.readLine())!= null){
			String[] dataRead = data.split("\t");
			try {
				if (Integer.parseInt(dataRead[2]) >= 90){
					dataRead[2] = "A+";

				}
				else if (Integer.parseInt(dataRead[2]) >= 85){
					dataRead[2] = "A";

				}
				else if (Integer.parseInt(dataRead[2]) >= 80){
					dataRead[2] = "A-";

				}
				else if (Integer.parseInt(dataRead[2]) >= 85){
					dataRead[2] = "A";

				}
				else if (Integer.parseInt(dataRead[2]) >= 80){
					dataRead[2] = "A-";

				}
				else if (Integer.parseInt(dataRead[2]) >= 85){
					dataRead[2] = "A";

				}
				else if (Integer.parseInt(dataRead[2]) >= 75){
					dataRead[2] = "B+";

				}
				else if (Integer.parseInt(dataRead[2]) >= 70){
					dataRead[2] = "B";

				}
				else if (Integer.parseInt(dataRead[2]) >= 65){
					dataRead[2] = "B-";

				}
				else if (Integer.parseInt(dataRead[2]) >= 60){
					dataRead[2] = "C+";

				}
				else if (Integer.parseInt(dataRead[2]) >= 50){
					dataRead[2] = "C";

				}
				else if (Integer.parseInt(dataRead[2]) >= 45){
					dataRead[2] = "C-";

				}
				else if (Integer.parseInt(dataRead[2]) >= 35){
					dataRead[2] = "D";

				}
				else if (Integer.parseInt(dataRead[2]) <35){
					dataRead[2] = "F";

				}
				
				
			}
			catch (NumberFormatException e) {
				dataRead[2] = "F"; // Set to "F" for invalid scores
	
				if ("F".equals(grad)) {
					counters++;
				}
			}
			if (dataRead[2].equals(grad)){
				counters += 1;
			}
			

		}
		return Integer.toString(counters);
	}
}

