import java.sql.*;

// This class contains a method to create a new project

class ProjectReader {

	// Creating an object of the ProjectMethods class
	ProjectMethods method = new ProjectMethods();

	// Creating a connection to the database
	Connection connection = method.connect();

	// Creating a method to create a new project from user input
	public void createProject() {

		// Declaring the line separator variable I created
		String lineSeparator = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

		// Gathering the project information using the getNum() and getStr() methods for input
		System.out.println("\nPlease enter the name of the project: ");
		String projectName = method.getStr(" ");

		System.out.println("\nPlease enter the address of this building: ");
		String buildingAddress = method.getStr(" ");

		System.out.println("\nPlease enter the type of this building: ");
		String buildingType = method.getStr(" ");

		System.out.println("\nPlease enter the project's due date in yyyy-mm-dd format: ");
		String projectDeadline = method.dateCreator();

		System.out.println("\nPlease enter the cost of this project: ");
		float customerFee = method.getNum(" ");

		System.out.println("\nPlease enter the amount paid by the customer to date: ");
		float amountPaidToDate = method.getNum(" ");

		System.out.println("\nPlease enter the project's ERF number: ");
		float erf = method.getNum(" ");

		// By default, a new project is incomplete so this block of code is for if a user wishes to add a
		// previously completed project
		System.out.println("\nPlease type 'Yes' if this project has been completed already: ");
		String projectCompletion = method.getStr(" ");

		String completionDate;
		if (projectCompletion.equals("Yes")) {
			System.out.println("\nPlease enter the date of completion in yyyy-mm-dd format: ");
			completionDate = method.dateCreator();
		} else {
			projectCompletion = "No";
			completionDate = null;
		}

		System.out.println(lineSeparator);

		// Gathering the architect's information using the getStr() methods for input.
		System.out.println("\nPlease enter the Architect's details");

		System.out.println("\nArchitect's First Name: ");
		String architectName = method.getStr(" ");

		System.out.println("\nArchitect's Surname: ");
		String architectSurname = method.getStr(" ");

		System.out.println("\nArchitect's Telephone Number: ");
		String architectPhoneNumber = method.getStr(" ");

		System.out.println("\nArchitect's Email Address: ");
		String architectEmail = method.getStr(" ");

		System.out.println("\nArchitect's Physical Address: ");
		String architectAddress = method.getStr(" ");

		System.out.println(lineSeparator);

		// Gathering the customer's information using the getStr() methods for input.
		System.out.println("\nPlease enter the Customer's details");

		System.out.println("\nCustomer's First Name: ");
		String customerName = method.getStr(" ");

		System.out.println("\nCustomer's Surname: ");
		String customerSurname = method.getStr(" ");

		System.out.println("\nCustomer's Telephone Number: ");
		String customerPhoneNumber = method.getStr(" ");

		System.out.println("\nCustomer's Email Address: ");
		String customerEmail = method.getStr(" ");

		System.out.println("\nCustomer's Physical Address: ");
		String customerAddress = method.getStr(" ");

		// Adding code for if the user fails to enter a project name.
		if (projectName.equals("")) {
			projectName = buildingType + " " + customerSurname;
		}

		System.out.println(lineSeparator);

		//Gathering the Project Manager's information using the getStr() methods for input.
		System.out.println("\nPlease enter the Project Manager's details");

		System.out.println("\nProject Manager's First Name: ");
		String pManagerName = method.getStr(" ");

		System.out.println("\nProject Manager's Surname: ");
		String pManagerSurname = method.getStr(" ");

		System.out.println("\nProject Manager's Telephone Number: ");
		String pManagerPhoneNumber = method.getStr(" ");

		System.out.println("\nProject Manager's Email Address: ");
		String pManagerEmail = method.getStr(" ");

		System.out.println("\nProject Manager's Physical Address: ");
		String pManagerAddress = method.getStr(" ");

		System.out.println(lineSeparator);

		// Gathering the Structural Engineer's information using the getStr() methods for input.
		System.out.println("\nPlease enter the Structural Engineer's details");

		System.out.println("\nStructural Engineer's First Name: ");
		String structuralEngineerName = method.getStr(" ");

		System.out.println("\nStructural Engineer Surname: ");
		String structuralEngineerSurname = method.getStr(" ");

		System.out.println("\nStructural Engineer's Telephone Number: ");
		String structuralEngineerPhoneNumber = method.getStr(" ");

		System.out.println("\nStructural Engineer's Email Address: ");
		String structuralEngineerEmail = method.getStr(" ");

		System.out.println("\nStructural Engineer's Physical Address: ");
		String structuralEngineerAddress = method.getStr(" ");

		// Using a try-catch block to handle the SQL queries
		try {
			// Using a statement to connect to the database in order to use ResultSet
			Statement statement = connection.createStatement();

			// Declaring variables for the project number and person IDs
			int projectNumber = 0;
			int archID = 1000;
			int custID = 2000;
			int projManID = 3000;
			int stEngID = 4000;

			// Getting the number of rows in each table based on how many project/IDs there are already in the table
			// and increasing the count of the project or the ID

			ResultSet prResults = statement.executeQuery("SELECT count(projectNum) AS pr_id FROM ProjectsInfo_Table");
			prResults.next();
			int projCount = prResults.getInt("pr_id");
			projCount++;
			projectNumber += projCount;

			ResultSet archResults = statement.executeQuery("SELECT count(id) AS architectID FROM ArchitectInfo_Table");
			archResults.next();
			int archCount = archResults.getInt("architectID");
			archCount++;
			archID += archCount;

			ResultSet custResults = statement.executeQuery("SELECT count(id) AS customerID FROM CustomerInfo_Table");
			custResults.next();
			int custCount = custResults.getInt("customerID");
			custCount++;
			custID += custCount;

			ResultSet projManResults = statement.executeQuery("SELECT count(id) AS projManagerID FROM ProjManagerInfo_Table");
			projManResults.next();
			int projManCount = projManResults.getInt("projManagerID");
			projManCount++;
			projManID += projManCount;

			ResultSet stEngResults = statement.executeQuery("SELECT count(id) AS structEngineerID FROM StructEngineerInfo_Table");
			stEngResults.next();
			int stEngCount = stEngResults.getInt("structEngineerID");
			stEngCount++;
			stEngID += stEngCount;

			// Creating queries to insert rows into each of the different tables

			String archQuery = "INSERT INTO ArchitectInfo_Table (id, projectNum, name, surname, telephoneNumber, " + "emailAddress, physicalAddress) VALUES (?,?,?,?,?,?,?)";

			String custQuery = "INSERT INTO CustomerInfo_Table (id, projectNum, name, surname, telephoneNumber, " + "emailAddress, physicalAddress) VALUES (?,?,?,?,?,?,?)";

			String projManQuery = "INSERT INTO ProjManagerInfo_Table (id, projectNum, name, surname, telephoneNumber, " + "emailAddress, physicalAddress) VALUES (?,?,?,?,?,?,?)";

			String stEngQuery = "INSERT INTO StructEngineer_Table (id, projectNum, name, surname, telephoneNumber, " + "emailAddress, physicalAddress) VALUES (?,?,?,?,?,?,?)";

			String prQuery = "INSERT INTO ProjectsInfo_Table (projectNum, projectName, buildingAddress, " + "buildingType, deadline, projectComplete, finalizedDate, customerFee, " + "totalAmountToDate, ERFNumber, architectID, customerID, projManagerID, " + "structEngineerID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			// Using PreparedStatements to execute each query and then defining the different parameters for each query

			PreparedStatement archPreparedStatement = connection.prepareStatement(archQuery);
			archPreparedStatement.setInt(1, archID);
			archPreparedStatement.setInt(2, projectNumber);
			archPreparedStatement.setString(3, architectName);
			archPreparedStatement.setString(4, architectSurname);
			archPreparedStatement.setString(5, architectPhoneNumber);
			archPreparedStatement.setString(6, architectEmail);
			archPreparedStatement.setString(7, architectAddress);

			PreparedStatement custPreparedStatement = connection.prepareStatement(custQuery);
			custPreparedStatement.setInt(1, custID);
			custPreparedStatement.setInt(2, projectNumber);
			custPreparedStatement.setString(3, customerName);
			custPreparedStatement.setString(4, customerSurname);
			custPreparedStatement.setString(5, customerPhoneNumber);
			custPreparedStatement.setString(6, customerEmail);
			custPreparedStatement.setString(7, customerAddress);

			PreparedStatement projManPreparedStatement = connection.prepareStatement(projManQuery);
			projManPreparedStatement.setInt(1, projManID);
			projManPreparedStatement.setInt(2, projectNumber);
			projManPreparedStatement.setString(3, pManagerName);
			projManPreparedStatement.setString(4, pManagerSurname);
			projManPreparedStatement.setString(5, pManagerPhoneNumber);
			projManPreparedStatement.setString(6, pManagerEmail);
			projManPreparedStatement.setString(7, pManagerAddress);

			PreparedStatement stEngPreparedStatement = connection.prepareStatement(stEngQuery);
			stEngPreparedStatement.setInt(1, stEngID);
			stEngPreparedStatement.setInt(2, projectNumber);
			stEngPreparedStatement.setString(3, structuralEngineerName);
			stEngPreparedStatement.setString(4, structuralEngineerSurname);
			stEngPreparedStatement.setString(5, structuralEngineerPhoneNumber);
			stEngPreparedStatement.setString(6, structuralEngineerEmail);
			stEngPreparedStatement.setString(7, structuralEngineerAddress);

			PreparedStatement projPreparedStatement = connection.prepareStatement(prQuery);
			projPreparedStatement.setInt(1, projectNumber);
			projPreparedStatement.setString(2, projectName);
			projPreparedStatement.setString(3, buildingAddress);
			projPreparedStatement.setString(4, buildingType);
			projPreparedStatement.setString(5, projectDeadline);
			projPreparedStatement.setString(6, projectCompletion);
			projPreparedStatement.setString(7, completionDate);
			projPreparedStatement.setFloat(8, customerFee);
			projPreparedStatement.setFloat(9, amountPaidToDate);
			projPreparedStatement.setFloat(10, erf);
			projPreparedStatement.setInt(11, archID);
			projPreparedStatement.setInt(12, custID);
			projPreparedStatement.setInt(13, projManID);
			projPreparedStatement.setInt(14, stEngID);

			// Executing all the prepared statements
			boolean insertArchitectsSuccess = archPreparedStatement.execute();
			boolean insertCustomerSuccess = custPreparedStatement.execute();
			boolean insertStEngSuccess = stEngPreparedStatement.execute();
			boolean insertProjManSuccess = projManPreparedStatement.execute();
			boolean insertProjectsSuccess = projPreparedStatement.execute();

			// Using ResultSets again to extract the rows from the different tables based on the project number for
			// the new entry and printing out the project and personal details to the console
			System.out.println(lineSeparator);

			System.out.println("Project details: " + "\n");
			ResultSet projectsResult = statement.executeQuery("SELECT * FROM ProjectsInfo_table" + " WHERE projectNum = " + projectNumber + "");
			method.printProjects(projectsResult);
			System.out.println("\n" + lineSeparator);

			System.out.println("Customer Details: " + "\n");
			ResultSet customerResult = statement.executeQuery("SELECT * FROM CustomerInfo_Table" + " WHERE projectNum = " + projectNumber + "");
			method.printCustomer(customerResult);
			System.out.println("\n" + lineSeparator);

			System.out.println("Architect Details: " + "\n");
			ResultSet architectResult = statement.executeQuery("SELECT * FROM ArchitectInfo_Table" + " WHERE project_number = " + projectNumber + "");
			method.printArchitect(architectResult);
			System.out.println("\n" + lineSeparator);

			System.out.println("Structural Engineer Details: " + "\n");
			ResultSet structuralEngineerResult = statement.executeQuery("SELECT * FROM StructEngineerInfo_Table" + " WHERE projectNum = " + projectNumber + "");
			method.printStEng(structuralEngineerResult);
			System.out.println("\n" + lineSeparator);

			System.out.println("Project Manager Details: " + "\n");
			ResultSet projectManagerResult = statement.executeQuery("SELECT * FROM ProjManagerInfo_table" + " WHERE projectNum = " + projectNumber + "");
			method.printProjMan(projectManagerResult);
			System.out.println("\n" + lineSeparator);

		} catch (SQLException eSQL) {
			// Printing the stack trace for any error that might occur
			eSQL.printStackTrace();
		}
	}
}
