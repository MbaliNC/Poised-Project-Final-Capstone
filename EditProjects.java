import java.sql.*;

/***
 * This class allows a user to edit the projects contained in the project's table
 * User is presented with a menu and then is able to select from different options of being able to edit a project
 * deadline, change the amount paid by the customer, update the Structural Engineer's details, finalize a project and
 * generate an invoice for the poised project, and also be able to delete a project
 * This is done by using SQL queries
 */

public class EditProjects {

    // Declaring necessary variables
    String lineSeparator = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

    // Creating an object of the ProjectMethods Class
    ProjectMethods method = new ProjectMethods();

    // Creating an object of the connection method from the ProjectMethods Class.
    Connection connection = method.connect();
    Statement statement = method.statement();

    // This method is to present the user with a menu to edit projects,
    public void Menu() {

        System.out.println("You have chosen to edit the existing projects.");
        System.out.println("""
                Please select one of the following options:\s
                1 - Edit a Project's Deadline
                2 - Edit the Amount Paid to Date by the Customer
                3 - Update the Structural Engineer's details
                4 - Finalise a Project and Print an Invoice for the Customer
                5 - Delete an Existing Project""");

        // Gathering the user's choice using the getNum() method from the ProjectMethods Class
        float menuOption2 = method.getNum(" ");

        // Using switch-case statements for each menu option
        switch ((int) menuOption2) {

            // Allowing the user to edit a deadline
            case 1 -> EditDeadline();

            // Allowing a user to update the amount paid by the customer
            case 2 -> UpdateAmount();

            // Allowing the user to update the structuralEngineer's details
            case 3 -> updateStructuralEngineer();

            // Allowing a user to finalise a project and print an invoice for the project
            case 4 -> FinaliseProject();

            // Allowing a user to delete a project
            case 5 -> DeleteProject();

            // If the user does not enter a valid option, an appropriate message is displayed
            default -> System.out.println("Please enter a valid option.");
        }
    }

    // Creating an EditDeadline() method to change a project deadline.
    public void EditDeadline() {

        System.out.println("\nYou have chosen to edit a project's deadline.");

        // Asking the user to enter the project number of the project they want to edit
        System.out.println("Please enter project the number of the project you wish to edit: ");
        float prNum = method.getNum(" ");

        // Using the dateCreator() method to edit the project deadline and using the getStr() method for input
        System.out.println("\nPlease enter a new deadline in yyyy-mm-dd format: ");
        String newProjectDeadline = method.dateCreator();

        // Using a try-catch block to handle SQL queries
        try {
            // Creating a query to update the ProjectDeadline column based on the project number
            String setNewDeadlineQuery = "UPDATE ProjectsInfo_Table SET deadline=? WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement setNewDeadlinePrepStmnt = connection.prepareStatement(setNewDeadlineQuery);

            // Setting the parameters in the PreparedStatement
            setNewDeadlinePrepStmnt.setString(1, newProjectDeadline);
            setNewDeadlinePrepStmnt.setFloat(2, prNum);

            boolean success = setNewDeadlinePrepStmnt.execute();

            System.out.println("Project Deadline Updated.");
            System.out.println(lineSeparator);
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating an UpdateAmount() method to change the amount paid by the customer.
    public void UpdateAmount() {

        System.out.println("\nYou have chosen to edit the amount paid by the customer.");

        // Asking the user to enter the project number of the project they want to edit
        System.out.println("Please enter project number of the project you wish to edit: ");
        float prNum = method.getNum(" ");

        // Asking the user to enter the amount paid by the customer
        System.out.println("\nPlease enter the amount paid by the customer: R");
        float newAmountPaid = method.getNum(" ");

        // Using a try-catch block to handle SQL queries
        try {
            // Creating a query to update the AmountPaidToDate column based on the project number
            String setAmountPaidQuery = "UPDATE ProjectsInfo_Table SET totalAmountToDate=? WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement setAmountPaidPrepStmnt = connection.prepareStatement(setAmountPaidQuery);

            // Setting the parameters in the PreparedStatement
            setAmountPaidPrepStmnt.setFloat(1, newAmountPaid);
            setAmountPaidPrepStmnt.setFloat(2, prNum);

            // Executing the prepared statement
            boolean setAmountSuccess = setAmountPaidPrepStmnt.execute();

            System.out.println("Amount paid has been updated.");
            System.out.println(lineSeparator);

            // Creating a query to extract the AmountPaidToDate value based on the project number
            String findAmountPaidQuery = "SELECT totalAmountToDate FROM ProjectsInfo_Table WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement findAmountPaidPrepStmnt = connection.prepareStatement(findAmountPaidQuery);

            // Setting the parameters in the PreparedStatement
            findAmountPaidPrepStmnt.setFloat(1, prNum);

            // Executing the prepared statement
            boolean findAmountSuccess = findAmountPaidPrepStmnt.execute();

            ResultSet amountResult = findAmountPaidPrepStmnt.executeQuery();
            amountResult.next();

            // Creating an integer variable of the value in the column
            float paid = Float.parseFloat(String.valueOf(amountResult.getString("totalAmountToDate")));

            // Creating a query to extract the CustomerFee value based on the project number
            String findCustFeeQuery = "SELECT customerFee FROM ProjectsInfo_Table WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement findCustFeePrepStmnt = connection.prepareStatement(findCustFeeQuery);

            // Setting the parameters in the PreparedStatement
            findCustFeePrepStmnt.setFloat(1, prNum);

            // Executing the prepared statement
            boolean findFeeSuccess = findCustFeePrepStmnt.execute();

            ResultSet custFeeResult = findCustFeePrepStmnt.executeQuery();
            custFeeResult.next();

            // Creating an integer variable of the value in the column
            float fee = Float.parseFloat(String.valueOf(custFeeResult.getString("customerFee")));

            if (paid == fee) {
                // Printing a message for the user if the customer has paid in full
                System.out.println("\nCustomer has paid in full.");
            }
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating an UpdateContractor() method to update the structuralEngineer's details
    public void updateStructuralEngineer() {

        System.out.println("\nYou have chosen to update a Structural Engineer's details");

        // Asking the user to enter the ID of the Structural Engineer that they want to edit
        System.out.println("Please enter the ID of the Structural Engineer whose details you wish to update: ");
        float stEngID = method.getNum(" ");

        // Using the getStr() method to get the new information from the user
        System.out.println("Structural Engineer's First Name: ");
        String structuralEngineerName = method.getStr(" ");

        System.out.println("\nStructural Engineer's Surname: ");
        String structuralEngineerSurname = method.getStr(" ");

        System.out.println("\nStructural Engineer's Telephone Number: ");
        String structuralEngineerPhoneNumber = method.getStr(" ");

        System.out.println("\nStructural Engineer's Email Address: ");
        String structuralEngineerEmail = method.getStr(" ");

        System.out.println("\nStructural Engineer's Physical Address: ");
        String structuralEngineerAddress = method.getStr(" ");

        System.out.println("Structural Engineer Details have been updated.");

        // Using a try-catch block to handle SQL queries
        try {
            // Creating a query to update the AmountPaidToDate column based on the project number
            String updateStEngQuery = "UPDATE StructEngineerInfo_Table SET name=?, surname=?, telephoneNumber=?, " +
                    "emailAddress=?, physicalAddress=? WHERE id=?";

            // Using PreparedStatement to execute the query
            PreparedStatement updateStEngPrepStmnt = connection.prepareStatement(updateStEngQuery);

            // Setting the parameters in the PreparedStatement
            updateStEngPrepStmnt.setString(1, structuralEngineerName);
            updateStEngPrepStmnt.setString(2, structuralEngineerSurname);
            updateStEngPrepStmnt.setString(3, structuralEngineerPhoneNumber);
            updateStEngPrepStmnt.setString(4, structuralEngineerEmail);
            updateStEngPrepStmnt.setString(5, structuralEngineerAddress);
            updateStEngPrepStmnt.setFloat(6, stEngID);

            // Executing the prepared statement
            boolean updateStEngSuccess = updateStEngPrepStmnt.execute();
        } catch (SQLException eSQL) {
            eSQL.printStackTrace();
        }
    }

    // Creating a FinaliseProject() method to finalise a project and print an invoice for that project
    public void FinaliseProject() {

        System.out.println("\nYou have chosen to finalise a project and print an invoice for the customer.");

        // Asking the user to enter the project number of the project they want to finalise
        System.out.println("Please enter project number of the project you wish to edit: ");
        float prNum = method.getNum(" ");

        // Asking the user to enter the amount paid by the customer
        System.out.println("\nPlease enter the amount paid by the customer: ");
        float newAmountPaid = method.getNum(" ");

        // Adding the completion date using the dateCreator() method
        System.out.println("\nPlease enter the completion date in yyyy-mm-dd format: ");
        String newCompletionDate = method.dateCreator();

        // Using a try-catch block to handle SQL queries
        try {
            // Creating ResultSet objects of queries to find the information about the project and the customer
            // from their respective tables based on the project number entered by the user
            ResultSet projectResults = statement.executeQuery("SELECT * FROM ProjectsInfo_table " +
                    " WHERE projectNum = " + prNum + "");

            ResultSet custResults = statement.executeQuery("SELECT * FROM CustomerInfo_Table " +
                    " WHERE projectNum = " + prNum + "");

            // Creating a query to update the projects table
            String finalProjQuery = "UPDATE ProjectsInfo_Table SET projectComplete=?, finalizedDate=?," +
                    " totalAmountToDate=? WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement finalProjPrepStmnt = connection.prepareStatement(finalProjQuery);

            // Setting the parameters in the PreparedStatement
            finalProjPrepStmnt.setString(1, "Yes");
            finalProjPrepStmnt.setString(2, newCompletionDate);
            finalProjPrepStmnt.setFloat(3, newAmountPaid);
            finalProjPrepStmnt.setFloat(4, prNum);

            // Executing the prepared statement
            boolean updateCompletionSuccess = finalProjPrepStmnt.execute();

            // Creating a query to extract the AmountPaidToDate value based on the project number
            String findAmountPaidQuery = "SELECT totalAmountToDate FROM ProjectsInfo_Table WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement findAmountPaidPrepStmnt = connection.prepareStatement(findAmountPaidQuery);

            // Setting the parameters in the PreparedStatement
            findAmountPaidPrepStmnt.setFloat(1, prNum);

            // Executing the prepared statement
            boolean findAmountSuccess = findAmountPaidPrepStmnt.execute();

            ResultSet amountResult = findAmountPaidPrepStmnt.executeQuery();
            amountResult.next();

            // Creating an integer variable of the value in the column
            float paid = Float.parseFloat(String.valueOf(amountResult.getString("totalAmountToDate")));

            // Creating a query to extract the CustomerFee value based on the project number
            String findCustFeeQuery = "SELECT customerFee FROM Projects_Table WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement findCustFeePrepStmnt = connection.prepareStatement(findCustFeeQuery);

            // Setting the parameters in the PreparedStatement
            findCustFeePrepStmnt.setFloat(1, prNum);

            // Executing the prepared statement
            boolean findFeeSuccess = findCustFeePrepStmnt.execute();

            ResultSet custFeeResult = findCustFeePrepStmnt.executeQuery();
            custFeeResult.next();

            // creating an integer variable of the value in the column
            float fee = Float.parseFloat(String.valueOf(custFeeResult.getString("customer_fee")));

            // Calculating the outstanding amount to be paid by the customer
            float outstandingAmount = fee - paid;

            // Printing out a customer invoice
            System.out.println(lineSeparator);
            System.out.println("Project details: ");
            method.printCustomer(custResults);
            System.out.println(lineSeparator);
            method.printProjects(projectResults);
            System.out.println(lineSeparator + "\nAMOUNT OUTSTANDING: " + outstandingAmount + lineSeparator);
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating a DeleteProject() method for user to delete a project.
    public void DeleteProject() {
        System.out.println("You have chosen to delete a project.");

        // Asking the user to enter the project number of the project they want to delete.
        System.out.println("Please enter the project number of the project you wish to delete: ");
        float prNum = method.getNum(" ");

        // Using a try-catch block to handle SQL queries
        try {
            // Creating a query to delete the row from the projects table based on the project number
            String prQuery = "DELETE FROM ProjectsInfo_Table WHERE projectNum=?";

            // Using PreparedStatement to execute the query
            PreparedStatement projPreparedStatement = connection.prepareStatement(prQuery);

            // Setting the parameters in the PreparedStatement
            projPreparedStatement.setFloat(1, prNum);

            // Executing the prepared statement
            boolean projDeleteSuccess = projPreparedStatement.execute();

            System.out.println("Project Deleted.");
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating a viewAll() method to allow a user to see all the entries in the projects table
    public void viewAll() {

        try {
            // Using ResultSet to select and print out all the entries in the projects table
            ResultSet projectsResult = statement.executeQuery("SELECT * FROM ProjectsInfo_Table");
            method.printProjects(projectsResult);
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating a viewIncomplete() method to allow a user to see all the incomplete entries in the projects table
    public void viewIncomplete() {

        try {
            // Using ResultSet to select and print out all the entries in the projects table based on
            // if the project is incomplete or not
            ResultSet projectsResult = statement.executeQuery("SELECT * FROM ProjectsInfo_Table " +
                    "WHERE projectComplete = 'No'");
            method.printProjects(projectsResult);
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }

    // Creating a viewOverdue() method to allow a user to see all the overdue entries in the projects table
    public void viewOverdue() {

        try {
            // Using ResultSet to select and print out all the entries in the projects table based on
            // if the project is overdue or not
            ResultSet projectsResult = statement.executeQuery("SELECT * FROM ProjectsInfo_Table " +
                    "WHERE deadline < date(now()) AND projectComplete = 'No'");
            method.printProjects(projectsResult);
        } catch (SQLException eSQL) {
            // Printing the stack trace for any error that might occur
            eSQL.printStackTrace();
        }
    }
}
