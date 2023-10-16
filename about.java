import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class about {
    private JFrame aboutFrame = new JFrame("About");

    about() {
        aboutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aboutFrame.setSize(600, 400);  // Increased the width for horizontal scrolling

        // Create a back button and add it to the top left corner
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aboutFrame.dispose();
                try {
                    MainClass.main(null);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(backButton);

        // Create a table with columns: Name, ID numbers, Professions
        String[] columnNames = {"<html><h2>Name</h2></html>", "<html><h2>ID numbers</h2></html>", "<html><h2>Professions</h2></html"};
        Object[][] data = {
            {"<html><h3>Samuel Tolossa</h3></html>", "<html><h3>UGR/25454/14</h3></html>", "<html><h3>Software Developers</h3></html>"},
            {"<html><h3>Abel Alemayehu</h3></html>", "<html><h3>Ugr/25383/14</h3></html>", "<html><h3>Software Developers</h3></html>"},
            {"<html><h3>Tinsae Teferi</h3></html>", "<html><h3>UGR/25753/14</h3></html>", "<html><h3>Software Developers</h3></html>"},
            {"<html><h3>Bealprasim Demere</h3></html>", "<html><h3>UGR/25540/14</h3></html>", "<html><h3>Software Developers</h3></html>"},
            {"<html><h3>Biruk Sitota</h3></html>", "<html><h3>UGR/25647/14</h3></html>", "<html><h3>Software Developers</h3></html>"},
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Create a JTextArea for "About Projects" text
        String aboutProjectsText = "This is a brief overview of our projects. We are a group of software developers working on various projects. Our team consists of talented individuals who are dedicated to creating high-quality software solutions.";
        aboutFrame.add(new JLabel(aboutProjectsText));
		JTextArea aboutProjectsTextArea = new JTextArea(5, 10);
        aboutProjectsTextArea.setText(aboutProjectsText);
        aboutProjectsTextArea.setLineWrap(true);
        aboutProjectsTextArea.setWrapStyleWord(true);

        JScrollPane textScrollPane = new JScrollPane(aboutProjectsTextArea);
        textScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Create a JPanel for the "About Projects" text
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(textScrollPane);

        // Create a container panel for both the table and the "About Projects" text
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(tableScrollPane, BorderLayout.NORTH);
        containerPanel.add(textPanel, BorderLayout.SOUTH);

        aboutFrame.add(buttonPanel, BorderLayout.NORTH);
        aboutFrame.add(containerPanel, BorderLayout.CENTER);
        aboutFrame.setVisible(true);
    }

    protected void aboutInfo(boolean states) throws IOException {
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setVisible(states);
    }
}
