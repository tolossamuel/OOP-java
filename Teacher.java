import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Teacher {
    private String name;
    private Object[][] studentList;
	private String course;
    JFrame teacherStudents;
	
    Container contentPanel;

    Teacher(String name, String course) throws IOException {
		this.course = course;
		this.name = name;
		contentPanel = new JPanel();
		
        studentList = new Object[0][3];
        readFile(course);
        
        fremForTeacher();
    }

    private void fremForTeacher() throws IOException {
        teacherStudents = new JFrame("Teacher Students");
        teacherStudents.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	teacherStudents.setSize(600, 400);

        contentPanel.removeAll();

        JButton save = new JButton("Save Change");
        JButton logout = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(logout);
        buttonPanel.add(save);
    

        JPanel studentListForm = new JPanel();
        studentListForm.setLayout(new BoxLayout(studentListForm, BoxLayout.Y_AXIS));

        studentListForm.add(new JLabel("Dashboard"));

        JLabel nameLabel = new JLabel("Name: Instructor " + name);
		JLabel nameLabel1 = new JLabel("Course " + course);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(nameLabel);
		nameLabel1.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(nameLabel1);

        JLabel semesterCoursesLabel = new JLabel("Your Students:");
        semesterCoursesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(semesterCoursesLabel);

        String[] columnNames = {"Name Students", "Credit Hour", "result"};
        

        JTable courseTable = new JTable(studentList, columnNames);
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        studentListForm.add(scrollPane);
		
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                teacherStudents.dispose();
                try {
                    MainClass.main(null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
		save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					iterateTables(courseTable,course);
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
            }
        });
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(buttonPanel, BorderLayout.NORTH);
        contentPanel.add(studentListForm, BorderLayout.CENTER);

        teacherStudents.add(contentPanel);
        teacherStudents.revalidate();
        teacherStudents.repaint();
    }

    private void readFile(String course) throws IOException {
        File file = new File(course + "_Grad.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedReader readFile = new BufferedReader(new FileReader(file));
        String data;
        
        while ((data = readFile.readLine()) != null) {
			String[] dataRead = data.split("\t");
			
			// Ensure dataRead contains all three elements
			if (dataRead.length == 3) {
				// Create a new array to hold the data
				Object[] rowData = new Object[3];
				
				// Populate rowData with the data from dataRead
				for (int i = 0; i < 3; i++) {
					rowData[i] = dataRead[i];
				}
	
				// Add rowData to the studentList
				int currentLength = studentList.length;
				Object[][] newStudentList = new Object[currentLength + 1][];
				System.arraycopy(studentList, 0, newStudentList, 0, currentLength);
				newStudentList[currentLength] = rowData;
				studentList = newStudentList;
			}
		}
        
		
    }
	private void iterateTables( JTable courseTable,String course ) throws IOException{
		int rowCount = courseTable.getRowCount();
		int colCount = courseTable.getColumnCount();

		File rewriteFile = new File(course+"_Grad.txt");
		if (rewriteFile.exists()){
			rewriteFile.delete();
			rewriteFile.createNewFile();
		}
		// FileWriter editStudentMark = new FileWriter(rewriteFile);
		// editStudentMark.write("");
		// editStudentMark.close();
		FileWriter gradEdited = new FileWriter(rewriteFile,true);
		String data = "";
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				Object value = courseTable.getValueAt(row, col);
				data += value + "\t";
			}
			data += "\n";
			gradEdited.write(data);
			data = "";
			 // Move to the next row
		}
		gradEdited.close();
		studentList = new Object[0][];
		readFile(course);
	}
	protected void teacherAfterLogin(boolean states) throws IOException{
		
		fremForTeacher();
        teacherStudents.setLocationRelativeTo(null);
        teacherStudents.setVisible(states);
	}
}
