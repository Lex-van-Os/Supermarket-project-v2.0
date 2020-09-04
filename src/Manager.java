import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Manager {
    public int manager_id;
    String first_name;
    String last_name;
    int age;
    String gender;
    double budget;
    double gross_salary;
    int supermarket_id;
    String column_name;

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setGross_salary(double gross_salary) {
        this.gross_salary = gross_salary;
    }

    public void setSupermarket_id(int supermarket_id) {
        this.supermarket_id = supermarket_id;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public void getManager_id() {
        DBConnect connectionTester = new DBConnect();
        connectionTester.testConnection();


    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public static void getManager(int userInstance) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Statement statement = connectionTester.connection.createStatement();

            String sql = "select * from manager where idmanager = " + userInstance;
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                String first_name = result.getString("first_name");
                String last_name = result.getString("last_name");
                int age = result.getInt("age");
                String gender = result.getString("gender");
                double budget = result.getDouble("budget");
                double gross_salary = result.getDouble("gross_salary");
                int supermarket_id = result.getInt("supermarket_idsupermarket");
                System.out.println("Supermarket Manager id in getManager");
                System.out.println(supermarket_id);

//                Manager manager = new Manager(first_name, last_name, age, gender, gross_salary, budget, supermarket_id);
//                showManagerActions(manager, userInstance);
            }

        } catch (SQLException err){
            System.out.println(err.getMessage());
        }
    }

    public static void createDBInstance(Manager manager) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to create a manager");

            String sql = " insert into manager (first_name, last_name, age, gender, budget, gross_salary, supermarket_idsupermarket)" + " values (?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, manager.first_name);
            preparedStmt.setString(2, manager.last_name);
            preparedStmt.setInt(3, manager.age);
            preparedStmt.setString(4, manager.gender);
            preparedStmt.setDouble(5, manager.budget);
            preparedStmt.setDouble(6, manager.gross_salary);
            preparedStmt.setDouble(7, manager.supermarket_id);

            preparedStmt.execute();
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void payoutEmployees() {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("Please enter the column you wish to update");
            String input_column = inputGetter.nextLine();

            System.out.println("Please enter the value that you'd like to give to the column");
            String input_information = inputGetter.nextLine();

            String sql = "update manager set " + input_column + " = ? where idmanager = ?";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, input_information);
            preparedStmt.setInt(2, userInstance);
            preparedStmt.execute();

            setColumn_name(input_column);
            System.out.println(input_column + " updated succesfully!");
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateManager(int userInstance) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("Please enter the column you wish to update");
            String input_column = inputGetter.nextLine();

            System.out.println("Please enter the value that you'd like to give to the column");
            String input_information = inputGetter.nextLine();

            String sql = "update manager set " + input_column + " = ? where idmanager = ?";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, input_information);
            preparedStmt.setInt(2, userInstance);
            preparedStmt.execute();

            setColumn_name(input_column);
            System.out.println(input_column + " updated succesfully!");
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteManager(Manager manager, int userInstance) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("You are about to resign, this will remove you from the database. Are you sure you wish to proceed?");
            String confirmation = inputGetter.nextLine();

            if (confirmation.charAt(0) == 'y') {

                String sql = "delete from manager where idmanager = ? and first_name = ? and last_name = ?";

                PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
                preparedStmt.setInt(1, userInstance);
                preparedStmt.setString(2, manager.first_name);
                preparedStmt.setString(3, manager.last_name);
                preparedStmt.execute();

                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }
}
