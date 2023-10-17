import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class Students {
    private JFrame studentFrame = new JFrame("Student Management");
    private JPanel contentPanel = new JPanel();
    private JPanel registrationForm;
    private JLabel welcomeLabel;
    protected String name;

    private Object[][] myList;
    private Object[][] myGrad;

    Students() throws IOException {
        StudentManagements LoginName = new StudentManagements();
        name = LoginName.geter();
        myList = new Object[0][];
        myGrad = new Object[0][];
        this.showWelcomeMessage(name);
    }

    public void showRegistrationForm(String name) {
        contentPanel.removeAll();

        // Create a back button and add it to the top left corner
        JButton backButton = new JButton("Back");
        contentPanel.add(backButton, BorderLayout.NORTH);
        studentFrame.revalidate();
        studentFrame.repaint();

        // Create the registration form panel
        JPanel registrationFormPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        // Add components to the registration form
        registrationFormPanel.add(new JLabel("Course field:"));
        // Create a combo box for the course field
        String[] courseOptions = {"Fundamental of Software Engineering", "DLD", "Object Oriented Programming", "History", "Psychology"};
        JComboBox<String> courseComboBox = new JComboBox<>(courseOptions);
        registrationFormPanel.add(courseComboBox);

        registrationFormPanel.add(new JLabel("Course code:"));
        JTextField courseCodeTextField = new JTextField(20);
        registrationFormPanel.add(courseCodeTextField);

        registrationFormPanel.add(new JLabel("Credit hours:"));
        JTextField creditHoursTextField = new JTextField(20);
        registrationFormPanel.add(creditHoursTextField);

        JButton saveButton = new JButton("Save");
        registrationFormPanel.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String course = courseComboBox.getSelectedItem().toString();
                String creditHour = creditHoursTextField.getText();
                String courseCode = courseCodeTextField.getText();
                try {
                    if (course.trim().isEmpty() || creditHour.trim().isEmpty() || courseCode.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(studentFrame, "Fill all spaces", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (isNotRegistered(name, course)) {
                        try {
                            savedData(name, course, creditHour, courseCode);
                            JOptionPane.showMessageDialog(studentFrame, "Saved successfully!!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException f) {
                            JOptionPane.showMessageDialog(studentFrame, "Invalid input of credit hour", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(studentFrame, "This student is already registered for the selected course.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Add an action listener for the back button
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showWelcomeMessage(name);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        // Add the registration form panel to the content panel
        contentPanel.add(registrationFormPanel, BorderLayout.CENTER);

        studentFrame.revalidate();
        studentFrame.repaint();
    }

    public void showGradeReport(String name) throws IOException {
        contentPanel.removeAll();
        String[] columnNames = {"Course", "Credit Hours", "Result", "Grade"};
        String[] courseOptions = {"Fundamental of Software Engineering", "DLD", "Object Oriented Programming", "History", "Psychology"};

        for (int i = 0; i < courseOptions.length; i++) {
            readGrad(courseOptions[i], name);
        }

        // Create a back button and add it to the top left corner
        String totalGrad = CalculateGrade();
        totalGrad = String.format("<html><h3>Grade Report: %s</h3></html>", totalGrad);

        JLabel totalGradReport = new JLabel(totalGrad);
        totalGradReport.setHorizontalAlignment(SwingConstants.CENTER);
        totalGradReport.setVerticalAlignment(SwingConstants.BOTTOM);
        totalGradReport.setFont(totalGradReport.getFont().deriveFont(Font.BOLD));

        contentPanel.add(totalGradReport);

        JButton backButton = new JButton("Back");
        contentPanel.add(backButton, BorderLayout.NORTH);
        studentFrame.revalidate();
        studentFrame.repaint();

        JTable gradeTable = new JTable(myGrad, columnNames);
        JScrollPane scrollPane = new JScrollPane(gradeTable);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showWelcomeMessage(name);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        studentFrame.revalidate();
        studentFrame.repaint();
    }

    public void showWelcomeMessage(String name) throws IOException {
        studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        studentFrame.setSize(600, 400);
        contentPanel.removeAll();

        JButton registerButton = new JButton("Register");
        JButton Logout = new JButton("Logout");
        JButton gradeReportButton = new JButton("Grade Report");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(Logout);
        buttonPanel.add(registerButton);
        buttonPanel.add(gradeReportButton);

        registrationForm = new JPanel();
        registrationForm.setLayout(new BoxLayout(registrationForm, BoxLayout.Y_AXIS));

        registrationForm.add(new JLabel("Dashboard"));
        JLabel nameLabel = new JLabel("Name:  " + name);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        registrationForm.add(nameLabel);

        JLabel semesterCoursesLabel = new JLabel("This Semester Courses:");
        semesterCoursesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        registrationForm.add(semesterCoursesLabel);

        String[] columnNames = {"Course", "Course Code", "Credit Hours"};
String[] courseOptions = {"Fundamental of Software Engineering", "DLD", "Object Oriented Programming", "History", "Psychology"};
        
        for (int i = 0; i < courseOptions.length; i++) {
            registerCourse(courseOptions[i], name);
        }

        JTable courseTable = new JTable(myList, columnNames);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        registrationForm.add(scrollPane);

        int totalCreditHours = 0;
        for (int i = 0; i < myList.length; i++) {
            totalCreditHours += Integer.parseInt((String) myList[i][2]);
        }

        JLabel totalCreditHoursLabel = new JLabel("Total Credit Hours: " + totalCreditHours);
        totalCreditHoursLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        registrationForm.add(totalCreditHoursLabel);

        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationForm(name);
            }
        });

        Logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                studentFrame.dispose();
                try {
                    MainClass.main(null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        gradeReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    showGradeReport(name);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(registrationForm, BorderLayout.CENTER);

        studentFrame.add(contentPanel);
        studentFrame.revalidate();
        studentFrame.repaint();
    }

    public String studentAfterLogin(boolean states, String name) throws IOException {
        this.showWelcomeMessage(name);
        studentFrame.setLocationRelativeTo(null);
        studentFrame.setVisible(states);
        return name;
    }

    void savedData(String name, String course, String creditHour, String courseCode) throws IOException {
        File studentFile = new File(course + ".txt");
        if (!studentFile.exists()) {
            studentFile.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(studentFile, true);
        fileWriter.write(name + "\t" + course + "\t" + courseCode + "\t" + creditHour + "\n");
        fileWriter.close();
        File gradFile = new File(course + "_Grad.txt");
        if (!gradFile.exists()) {
            gradFile.createNewFile();
        }
        FileWriter gradWrite = new FileWriter(gradFile, true);
        gradWrite.write(name + "\t" + creditHour + "\t" + "-" + "\n");
        gradWrite.close();
    }

    boolean isNotRegistered(String name, String course) throws IOException {
        File studentFile = new File(course + ".txt");

        if (!studentFile.exists()) {
            studentFile.createNewFile();
        }

        BufferedReader readFile = new BufferedReader(new FileReader(studentFile));
        String nameStudents;
        String[] listName = new String[4];
        while ((nameStudents = readFile.readLine()) != null) {
            listName = nameStudents.split("\t");
            if (listName[0].equals(name)) {
                return false;
            }
        }
        return true;
    }

    void registerCourse(String file, String name) throws IOException {
        File checkFile = new File(file + ".txt");
        if (checkFile.exists()) {
            String data;
            String[] dataRead = new String[4];
            BufferedReader readFile = new BufferedReader(new FileReader(checkFile));
            while ((data = readFile.readLine()) != null) {
                dataRead = data.split("\t");
                if (dataRead[0].equals(name)) {
                    List<String> subList = Arrays.asList(dataRead).subList(1, 4);
                    Object[] rowData = subList.toArray();

                    boolean exists = false;
                    for (Object[] existingRow : myList) {
                        if (Arrays.equals(existingRow, rowData)) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        Object[][] newMyList = new Object[myList.length + 1][];
                        System.arraycopy(myList, 0, newMyList, 0, myList.length);
                        newMyList[myList.length] = rowData;
                        myList = newMyList;
                    }
                }
            }
        }
    }

    protected void readGrad(String file, String name) throws IOException {
        File gradFile = new File(file + "_Grad.txt");
        if (gradFile.exists()) {
            BufferedReader readGrad = new BufferedReader(new FileReader(gradFile));
            String data;

            while ((data = readGrad.readLine()) != null) {
                String[] dataRead = data.split("\t");

                if (dataRead[0].equals(name)) {
                    Object[] rowData = new Object[dataRead.length + 1];
                    rowData[0] = file;

                    System.arraycopy(dataRead, 1, rowData, 1, dataRead.length - 1);

                    boolean exists = false;
                    for (Object[] existingRow : myGrad) {
                        if (Arrays.equals(existingRow, rowData)) {
                            exists = true;
                            break;
                        }
                    }

                    if (!exists) {
                        Object[][] newMyGrad = new Object[myGrad.length + 1][];
                        System.arraycopy(myGrad, 0, newMyGrad, 0, myGrad.length);
                        newMyGrad[myGrad.length] = rowData;
                        myGrad = newMyGrad;
                    }
                }
            }
            for (int i = 0; i < myGrad.length; i++) {
                String gradeStr = (String) myGrad[i][2];
                if (isNumeric(gradeStr)) {
                    int grade = Integer.parseInt(gradeStr);
                    if (grade >= 90) {
                        myGrad[i][3] = "A+";
                    } else if (grade >= 85) {
                        myGrad[i][3] = "A";
                    } else if (grade >= 80) {
                        myGrad[i][3] = "A-";
                    } else if (grade >= 75) {
                        myGrad[i][3] = "B+";
                    } else if (grade >= 70) {
                        myGrad[i][3] = "B";
                    } else if (grade >= 65) {
                        myGrad[i][3] = "B-";
                    } else if (grade >= 60) {
                        myGrad[i][3] = "C+";
                    } else if (grade >= 50) {
                        myGrad[i][3] = "C";
                    } else if (grade >= 45) {
                        myGrad[i][3] = "C-";
                    } else if (grade >= 40) {
                        myGrad[i][3] = "D";
                    } else {
                        myGrad[i][3] = "F";
                    }
                } else {
                    myGrad[i][3] = "-";
                }
            }
        }
    }

    private boolean isNumeric(String gradeStr) {
        try {
            Integer.parseInt(gradeStr);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    protected String CalculateGrade() {
        Map<String, Double> gradeDictionary = new HashMap<>();

        // Populate the dictionary with grade values
        gradeDictionary.put("A+", 4.0);
        gradeDictionary.put("A", 4.0);
        gradeDictionary.put("A-", 3.75);
        gradeDictionary.put("B+", 3.5);
        gradeDictionary.put("B", 3.0);
        gradeDictionary.put("B-", 2.75);
        gradeDictionary.put("C+", 2.5);
        gradeDictionary.put("C", 2.0);
        gradeDictionary.put("C-", 1.75);
        gradeDictionary.put("D", 1.0);
        gradeDictionary.put("F", 0.0);
        gradeDictionary.put("-", 0.0);
        double gradeTotal = 0.00;
        int creditHour = 0;
        for (int i = 0; i < myGrad.length; i++) {
            gradeTotal += (gradeDictionary.get(myGrad[i][3]) * Integer.parseInt((String) myGrad[i][1]));
            creditHour += Integer.parseInt((String) myGrad[i][1]);
        }
        gradeTotal /= creditHour;
        DecimalFormat df = new DecimalFormat("#.##");
        String roundedGradeTotal = df.format(gradeTotal);
        return roundedGradeTotal;
    }

    
}
