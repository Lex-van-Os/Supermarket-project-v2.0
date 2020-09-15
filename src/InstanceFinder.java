import com.mysql.cj.protocol.Resultset;

        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.Scanner;

// The instanceFinder is used to check if an instance of a database instance exists.
// This, for example, is used to check if a supermarket exists when running the program.
// Once checked, the program will prompt the user with options based on if an instance exists.

// If an instance already exists, the program will run the chooseInstance method.
// This method lets the user choose an instance based around the function the user chooses to run the program as
// This information is used to prompt the user with methods based around the chosen function and instance

// If an instance does not exist, the program will ask if you would like to create a new one

public class InstanceFinder {
    ArrayList<Integer> instances = new ArrayList<Integer>();

    public void findInstance(String instance, String instanceName) {
        // Method for finding the instance
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            String instanceSql = instance.replace("\"", "");

            Statement statement = connectionTester.connection.createStatement();

            String sql = "select * from " + instanceSql;
            ResultSet result = statement.executeQuery(sql);

            String instanceString = instance.substring(0, 1).toUpperCase() + instance.substring(1);

            if (result.next()) {
                System.out.println(instanceString + " found!");
                String instanceInput = "id" + instanceSql;
                instanceInput = instanceInput.replace("\"", "");
                instanceName = instanceName.replace("\"", "");
                if (instance != "supermarket") {
                    Scanner chooseInstance = new Scanner(System.in);
                    boolean breakWhile = false;
                    while (!breakWhile) {
                        System.out.println("Would you like to choose your instance for " + instanceString + "?");
                        String instanceConfirmation = chooseInstance.nextLine();
                        if (instanceConfirmation.charAt(0) == 'y') {
                            breakWhile = true;
                            chooseInstance(sql, instance, instanceInput, instanceName);
                        } else if (instanceConfirmation.charAt(0) == 'n') {
                            break;
                        } else {
                            System.out.println("No valid input");
                        }
                    }
                }
            } else {
                createInstance(instance);
            }


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void chooseInstance(String sql, String instance, String instanceInput, String instanceName) {
        //Method for choosing the instance
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Statement statement = connectionTester.connection.createStatement();
            ResultSet chooserResult = statement.executeQuery(sql);

            while(chooserResult.next()) {
                int instanceId = chooserResult.getInt(instanceInput);
                String instanceNameResult = chooserResult.getString(instanceName);
                System.out.println(instanceId + ": " + instanceNameResult);
                instances.add(instanceId);
            }

            Scanner instanceScanner = new Scanner(System.in);
            System.out.println("Please select your instance by providing the id for your instance.");
            int userInstance = instanceScanner.nextInt();
            for (int i = 0; i < instances.size(); i++) {
                if (userInstance == instances.get(i)) {
                    Menu.selectCreateInstance(instance, userInstance);
                }
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void createInstance(String instance) {
        //Method for creating an instance
        System.out.println("No " + instance + " detected, would you like to create one?");
        Scanner instanceGetter = new Scanner(System.in);
        boolean breakWhile = false;
        while (!breakWhile) {
            String instanceInput = instanceGetter.nextLine();
            if (instanceInput.charAt(0) == 'y') {
                breakWhile = true;
                Menu.selectCreateInstance(instance);
            } else if (instanceInput.charAt(0) == 'n') {
                break;
            } else {
                System.out.println("No valid input");
            }
        }
    }
}