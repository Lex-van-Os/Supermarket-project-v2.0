import java.sql.*;
import java.util.Scanner;

public class Supermarket {
    String name;
    double budget;
    double income_tax;
    int manager_id;
    String column_name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setIncome_tax(double income_tax) {
        this.income_tax = income_tax;
    }

    public void setManager_id(int manager_id) {
        this.manager_id = manager_id;
    }

    public int getManager_id() {
        return manager_id;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void updateSupermarketFindValue(Manager manager) {

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("please enter the column you wish to update");
            String input_column = inputGetter.nextLine();

            ColumnGetter departmentGetter = new ColumnGetter();
            departmentGetter.getColumn("supermarket", "name");

            System.out.println("please enter the id of the supermarket you wish to update");
            int input_id = inputGetter.nextInt();

            String sql = "select " + input_column + " from supermarket where idsupermarket = " + input_id;

            Statement stmt = connectionTester.connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            String outputName = "";
            String outputType = "";

            while (result.next()){
                outputName = resultSetMetaData.getColumnName(1);
                outputType = resultSetMetaData.getColumnTypeName(1);
            }

            updateSupermarketValue(outputName, outputType, input_id);
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateSupermarketValue(String outputName, String outputType, int input_id) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            System.out.println("please enter the value you wish to give the column");
            Scanner inputGetter = new Scanner(System.in);

            String sql = " update supermarket set " + outputName + " = ? where idsupermarket = ?";
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);

            switch (outputType) {
                case "VARCHAR": {
                    String input_value = inputGetter.nextLine();
                    prpStmt.setString(1, input_value);
                    System.out.println("You're about to update " + outputName + " to " + input_value + ". Are you sure you wish to proceed?");
                    break;
                }
                case "INT": {
                    String input_value = inputGetter.nextLine();
                    prpStmt.setString(1, input_value);
                    System.out.println("You're about to update " + outputName + " to " + input_value + ". Are you sure you wish to proceed?");
                    break;
                }
                case "DOUBLE": {
                    double input_value = inputGetter.nextDouble();
                    prpStmt.setDouble(1, input_value);
                    System.out.println("You're about to update " + outputName + " to " + input_value + ". Are you sure you wish to proceed?");
                    break;
                }
            }

            Scanner confirmScanner = new Scanner(System.in);
            String inputConfirmation = confirmScanner.nextLine();

            if (inputConfirmation.charAt(0) == 'y') {
                prpStmt.setInt(2, input_id);
                prpStmt.execute();
                setColumn_name(outputName);
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteSupermarket(Manager manager) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            InstanceFinder positionInstanceFinder = new InstanceFinder();
            positionInstanceFinder.findInstance("supermarket", "name");

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("You are about to delete your own supermarket, this action will delete all records related to this supermarket, are you sure you wish to proceed?");
            String confirmation = inputGetter.nextLine();

            if (confirmation.charAt(0) == 'y') {
                System.out.println(manager.supermarket_id);
                System.out.println(manager.first_name);
                System.out.println(manager.last_name);

                ColumnGetter positionGetter = new ColumnGetter();
                positionGetter.getColumn("manager", "first_name");

                Scanner idGetter = new Scanner(System.in);
                System.out.println("please enter the id of the employee you wish to update");
                int input_id = idGetter.nextInt();
                idGetter.nextLine();

                int manager_id = manager.supermarket_id;

                String sqlSupermarketUpdate = "update supermarket set manager_idmanager = NULL where idsupermarket = ?";
                String sqlManager = "update manager set supermarket_idsupermarket = NULL where idmanager = ?";
                String sqlEmployee = "DELETE FROM employee WHERE supermarket_idsupermarket = ?";
                String sqlSupermarketDelete = "delete from supermarket where idsupermarket = ?";

                PreparedStatement preparedStmtSupermarketUpdate = connectionTester.connection.prepareStatement(sqlSupermarketUpdate);
                preparedStmtSupermarketUpdate.setInt(1, manager.supermarket_id);
                preparedStmtSupermarketUpdate.execute();
                System.out.println("Set supermarket managerid value to NULL");

                PreparedStatement preparedStmtManager = connectionTester.connection.prepareStatement(sqlManager);
                preparedStmtManager.setInt(1, input_id);
                preparedStmtManager.execute();
                System.out.println("Set manager supermarketid value to NULL");

                PreparedStatement preparedStmtEmployee = connectionTester.connection.prepareStatement(sqlEmployee);
                preparedStmtEmployee.setInt(1, manager_id);
                System.out.println(preparedStmtEmployee);
                preparedStmtEmployee.execute();
                System.out.println("All employees deleted succesfully!");

                PreparedStatement preparedStmtSupermarket = connectionTester.connection.prepareStatement(sqlSupermarketDelete);
                preparedStmtSupermarket.setInt(1, manager_id);
                System.out.println(preparedStmtSupermarket);
                preparedStmtSupermarket.execute();
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void createDBInstance(Supermarket jumbo) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            System.out.println("Preparing to create supermarket");

            String sql = " insert into supermarket (budget, income_tax, name)" + " values (?, ?, ?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setDouble(1, jumbo.budget);
            preparedStmt.setDouble(2, jumbo.income_tax);
            preparedStmt.setString(3, jumbo.name);

            preparedStmt.execute();
            connectionTester.connection.close();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void createInstances(String action, Manager managerModel) {
        Supermarket supermarketModel = new Supermarket();
        SupermarketView supermarketView = new SupermarketView();
        SupermarketController supermarketController = new SupermarketController(supermarketModel, supermarketView);
        if(action == "insert") {

        }

        else if(action == "delete") {
            supermarketController.deleteSupermarket(managerModel);
            supermarketController.showSupermarketrDeleteAction();
        }

        else if(action == "update") {
            supermarketController.updateSupermarket(managerModel);
            supermarketController.showSupermarketUpdateAction();
        }
    }
}
