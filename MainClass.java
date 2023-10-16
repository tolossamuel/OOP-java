import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        StudentManagements login = new StudentManagements();
		
        Students students = new Students();
		
		

        // Display the login page
        login.LoginPages(students);
		
    }
}