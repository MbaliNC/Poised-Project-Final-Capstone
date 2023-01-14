import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

// Creating a class to contain all the methods required for user input

public class ProjectMethods {

    // Creating a static scanner object
    static Scanner input = new Scanner(System.in);

    /***
     *
     * @param instruction takes in integer input from the user
     * @return the integers the user entered
     */
    // Creating a method called getNum() to get numbers from the user using a try and catch block.
    public float getNum(String instruction) {

        while (true) {

            // Return the scanner and cast the string to an integer data type
            try {
                System.out.println(instruction);
                return Float.parseFloat(input.nextLine());
            }

            // If a string is entered from the user, an error message will be displayed in the console
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    /***
     * @param instruction takes in a String from user input
     * @return the string the user entered
     */
    // Creating a method called getNum() to get integers from the user using a try and catch block
    public String getStr(String instruction) {

        while (true) {
            // Return the scanner and cast the string to an integer data type
            try {
                System.out.println(instruction);
                return input.nextLine();
            }

            // If a string is not entered, an error message will be displayed in the console
            catch (Exception e) {
                System.out.println("Please enter a string of letters.");
            }
        }
    }

    /***
     * @return the date entered by the user
     */
    // Creating a method called dateCreator() to add the date in a set format for each project
    public String dateCreator() {
        String date;

        // Creating an object of the SimpleDateFormat method and passing the format that the user should enter
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Using a while loop and a try-catch block
        while (true) {
            try {

                // Collecting the date from the user
                Date deadline = dateFormat.parse(getStr(" "));
                date = dateFormat.format(deadline);
                break;
            }

            // Asking the user to enter the right date format
            // Handling potential exception error
            catch (ParseException pe) {
                System.out.println("Please enter the date in the format stated above.");
            }
        }

        // Returning the project deadline
        return date;
    }

    /***
     * @return the connection to the database
     */
    // Creating a method to connect to the MySQL database
    public static Connection connect() {

        Connection connection = null;

        // Using a try-catch block to connect to the database
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Poised_db?useSSL=false",
                    "otheruser",
                    "swordfish"
            );
        } catch (SQLException eSQL) {
            System.out.println("Connection Error!");
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }

        return connection;
    }

    /***
     * @return a statement from the database
     */
    // Creating a statement method to create a statement which connects to the database
    public static Statement statement() {
        Statement statement = null;

        try {
            statement = connect().createStatement();
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }

        return statement;
    }

    /***
     * @param projectResults taking in a ResultSet of the table
     * @throws SQLException any SQL error that might occur
     */
    // Creating a method to print out the details of the Projects' table to the user's console.
    public static void printProjects(ResultSet projectResults) throws SQLException {
        while (projectResults.next()) {
            System.out.println("Project Number: " + projectResults.getInt("projectNum") + " | "
                    + "Project Name: " + projectResults.getString("projectName") + " | "
                    + "Building Address: " + projectResults.getString("buildingAddress") + " | "
                    + "Building Type: " + projectResults.getString("buildingType") + " | "
                    + "Project Deadline: " + projectResults.getDate("deadline") + " | "
                    + "Project Complete: " + projectResults.getString("projectComplete") + " | "
                    + "Completion Date: " + projectResults.getDate("finalizedDate") + " | "
                    + "Customer Fee: R" + projectResults.getString("customerFee") + " | "
                    + "Amount Paid To Date: R" + projectResults.getString("totalAmountToDate") + " | "
                    + "ERF Number: " + projectResults.getInt("ERFNumber") + " | "
                    + "Architect ID: " + projectResults.getInt("architectID") + " | "
                    + "Customer ID: " + projectResults.getInt("customerID") + " | "
                    + "Project Manager ID: " + projectResults.getInt("projManagerID") + " | "
                    + "Structural Engineer ID: " + projectResults.getInt("structEngineerID") + " | "
            );
        }
    }

    /***
     * @param architectResults taking in a ResultSet of the table
     * @throws SQLException any SQL error that might occur
     */
    // Creating a method to print out the details of the Architect table to the user's console.
    public static void printArchitect(ResultSet architectResults) throws SQLException {
        while (architectResults.next()) {
            System.out.println("ID: " + architectResults.getInt("id") + " | "
                    + "Name: " + architectResults.getString("name") + " | "
                    + "Surname: " + architectResults.getString("surname") + " | "
                    + "Telephone Number: " + architectResults.getString("telephoneNumber") + " | "
                    + "Email Address: " + architectResults.getString("emailAddress") + " | "
                    + "Physical Address: " + architectResults.getString("physicalAddress") + " | "
            );
        }
    }

    /***
     * @param customerResults taking in a ResultSet of the table
     * @throws SQLException any SQL error that might occur
     */
    // Creating a method to print out the details of the Customer table to the user's console.
    public static void printCustomer(ResultSet customerResults) throws SQLException {
        while (customerResults.next()) {
            System.out.println("ID: " + customerResults.getInt("id") + " | "
                    + "Name: " + customerResults.getString("name") + " | "
                    + "Surname: " + customerResults.getString("surname") + " | "
                    + "Telephone Number: " + customerResults.getString("telephoneNumber") + " | "
                    + "Email Address: " + customerResults.getString("emailAddress") + " | "
                    + "Physical Address: " + customerResults.getString("physicalAddress") + " | "
            );
        }
    }

    /***
     * @param projectManagerResults taking in a ResultSet of the table
     * @throws SQLException any SQL error that might occur
     */
    // Creating a method to print out the details of the Project Manager table to the user's console.
    public static void printProjMan(ResultSet projectManagerResults) throws SQLException {
        while (projectManagerResults.next()) {
            System.out.println("ID: " + projectManagerResults.getInt("id") + " | "
                    + "Name: " + projectManagerResults.getString("name") + " | "
                    + "Surname: " + projectManagerResults.getString("surname") + " | "
                    + "Telephone Number: " + projectManagerResults.getString("telephoneNumber") + " | "
                    + "Email Address: " + projectManagerResults.getString("emailAddress") + " | "
                    + "Physical Address: " + projectManagerResults.getString("physicalAddress") + " | "
            );
        }
    }

    /***
     * @param structuralEngineerResults taking in a ResultSet of the table
     * @throws SQLException any SQL error that might occur
     */
    // Creating a method to print out the details of the Structural Engineer table to the user's console.
    public static void printStEng(ResultSet structuralEngineerResults) throws SQLException {
        while (structuralEngineerResults.next()) {
            System.out.println("ID: " + structuralEngineerResults.getInt("id") + " | "
                    + "Name: " + structuralEngineerResults.getString("name") + " | "
                    + "Surname: " + structuralEngineerResults.getString("surname") + " | "
                    + "Telephone Number: " + structuralEngineerResults.getString("telephoneNumber") + " | "
                    + "Email Address: " + structuralEngineerResults.getString("emailAddress") + " | "
                    + "Physical Address: " + structuralEngineerResults.getString("physicalAddress") + " | "
            );
        }
    }
}
