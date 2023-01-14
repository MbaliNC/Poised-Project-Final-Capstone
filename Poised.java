import java.sql.Statement;

/**
 * Poised Project, Final Capstone
 *
 * @author Mbali Cele
 */

public class Poised {
    /***
     * Main method implements all other methods.
     * @param args arguments
     */

    public static void main(String[] args) {

        // Creating objects of the ProjectReader, EditProjects and ProjectMethods Classes
        ProjectReader reader = new ProjectReader();
        EditProjects editProjects = new EditProjects();
        ProjectMethods method = new ProjectMethods();

        // Adding the lineSeparator variable
        String lineSeparator = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";


        // Using a while loop to enter the menu
        while (true) {

            // Presenting the user with menu options for the Poised Projects
            System.out.println("""
                    Please select one of the following options:\s
                    1 - View All Projects
                    2 - Add a New Project
                    3 - Edit Projects
                    4 - View Incomplete Projects
                    5 - View Overdue Projects
                    6 - Exit the Program.""");

            // Gathering the user's choice using the getNum() method from the ProjectMethods Class
            float menuOption = method.getNum(" ");

            // If the user chooses 6, user will exit the program and an exit message is displayed to the user
            // This is outside the switch-case statement to ensure that the program quits
            if (menuOption == 6) {
                System.out.println("Thank You for Using the Poised Program. Goodbye!!!");
                System.out.println(lineSeparator);
                break;
            }

            // Using a switch-case statement for each user choice

            switch ((int) menuOption) {

                // If a user chooses 1, user will be able to view all the projects in the table
                case 1 -> {
                    System.out.println("You have chosen to view all projects.");

                    // Calling the viewAll() method from the EditProjects class
                    editProjects.viewAll();
                    System.out.println(lineSeparator);
                }

                // If the user chooses 2, user will be able to add a new project
                case 2 -> {
                    System.out.println("You have chosen to add a new project.");

                    // Calling the createProject() method from the ProjectReader Class
                    reader.createProject();
                    System.out.println("New Project added.");

                }

                // If the user chooses 3, user will be able to edit a project
                // Users are presented with a second menu called from the EditProjects Class
                case 3 -> {
                    editProjects.Menu();
                    System.out.println(lineSeparator);
                }

                // If the user chooses 4, user will be able to view all incomplete projects
                // Users are presented with the ViewIncomplete method from the EditProjects Class
                case 4 -> {
                    System.out.println("You have chosen to view all incomplete projects.");
                    editProjects.viewIncomplete();
                    System.out.println(lineSeparator);
                }

                // If the user chooses 5, user will be able to view all overdue projects
                // Users are presented with the Overdue method called from the EditProjects Class
                case 5 -> {
                    System.out.println("You have chosen to view all overdue projects.");
                    editProjects.viewOverdue();
                    System.out.println(lineSeparator);
                }

                // If the user does not enter a valid option, an appropriate error message is displayed
                default -> System.out.println("Please enter a valid option.");
            }
        }
    }
}