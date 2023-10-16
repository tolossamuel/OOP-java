import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class help {
    private JFrame helpFrame = new JFrame("Help");

    help() {
        helpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helpFrame.setSize(600, 400);

        // Create a back button and add it to the top left corner
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                helpFrame.dispose();
                try {
                    MainClass.main(null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);

        JPanel helpLabels = new JPanel();
        helpLabels.setLayout(new BoxLayout(helpLabels, BoxLayout.Y_AXIS)); // Vertical layout

        // Add your help content to the helpLabels panel (your Q&A JLabels)
		addQA(helpLabels, "Q0: How reviews the system?", "To reviews our system we have already temporary password and username for all (students, teachers, admin) <html><h3>for students username 'admin' and password also 'admin'</h3> <h3>for teachers the username is  'samy' password also 'samy'</h3> It is case sensitive this password and username not give full access of system it is just template access and to reviews how system work you access the template file only</html>");
        addQA(helpLabels, "Q1: How do I register for the system?", "Registration is done by the system administrator. They will provide you with a username and password. Please contact the administrator to get registered.");
        addQA(helpLabels, "Q2: What if I forget my password?", "If you forget your password, you can contact the system administrator. They can reset your password for you.");
        addQA(helpLabels, "Q3: I'm a student. How do I log in?", "Once registered, you can log in with the username and password given to you by the administrator. After logging in, you can access various features, including course registration and grade viewing.");
        addQA(helpLabels, "Q4: How do I register for courses?", "After logging in, you can register for courses that are scheduled for the semester. Use the registration feature in your student portal to select your desired courses.");
        addQA(helpLabels, "Q5: How can I view my grades?", "You can view your grades by logging in to your student portal. There, you will find an option to view your grade reports, which will display your test results and grades.");
        addQA(helpLabels, "Q6: I'm a teacher. How do I submit grades for my students?", "Teachers can submit grades for their assigned students through the system. Log in as a teacher, select the course and the student, and then submit the student's test results.");
        addQA(helpLabels, "Q7: Can I change my registered courses after registration?", "Once you've registered for a course, you may not be able to change it. Please check with the administrator or system guidelines for specific policies.");
        addQA(helpLabels, "Q8: How are grades calculated?", "Grades are calculated based on a predefined system that takes into account the test results. The system assigns grades based on specific score ranges, which you can view in your grade report.");

        JScrollPane scrollPane = new JScrollPane(helpLabels); // Make the content scrollable
        helpFrame.add(buttonPanel, BorderLayout.NORTH);
        helpFrame.add(scrollPane, BorderLayout.CENTER); // Add the scroll pane
	
        helpFrame.setVisible(true);
    }

    private void addQA(Container container, String question, String answer) {
        String wrappedAnswer = wrapText(answer, 80); // Wrap text into lines of max 80 characters
        container.add(new JLabel("<html><h2>" + question + "</h2>" + wrappedAnswer + "</html>"));
		
    }
	

    private String wrapText(String text, int maxLineLength) {
        StringBuilder wrappedText = new StringBuilder();
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (line.length() + word.length() <= maxLineLength) {
                if (line.length() > 0) {
                    line.append(" ");
                }
                line.append(word);
            } else {
                wrappedText.append(line.toString()).append("<br>");
                line = new StringBuilder(word);
            }
        }

        if (line.length() > 0) {
            wrappedText.append(line.toString());
        }

        return wrappedText.toString();
    }

    protected void helpLogin(boolean states) throws IOException {
        helpFrame.setLocationRelativeTo(null);
        helpFrame.setVisible(states);
    }

}
