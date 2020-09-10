import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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

    public Manager getManager(int userInstance) {
        Manager manager = new Manager();
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Statement statement = connectionTester.connection.createStatement();

            String sql = "select * from manager where idmanager = " + userInstance;
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                manager.setManager_id(result.getInt("idmanager"));
                manager.setFirst_name(result.getString("first_name"));
                manager.setLast_name(result.getString("last_name"));
                manager.setGender(result.getString("gender"));
                manager.setBudget(result.getDouble("budget"));
                manager.setGross_salary(result.getDouble("gross_salary"));
                manager.setSupermarket_id(result.getInt("supermarket_idsupermarket"));
                System.out.println("Supermarket Manager id in getManager");
                System.out.println(manager.manager_id);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return manager;
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

    public double getSupermarketInstance(Manager manager) {
        System.out.println("getSupermarketInstance");
        double supermarket_budget = 0;
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            String sql = "select budget from supermarket where idsupermarket = ?";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setInt(1, manager.supermarket_id);

            ResultSet supermarketResult = preparedStmt.executeQuery();

            while (supermarketResult.next()) {
                supermarket_budget = supermarketResult.getDouble("budget");
            }

            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        return supermarket_budget;
    }

    public void getEmployeeInstances(Manager manager, double supermarket_budget) {
        System.out.println("getEmployeeInstances");
        HashMap<Integer, Employee> employees = new HashMap<Integer, Employee>();
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            String sql = "select * from employee where supermarket_idsupermarket = ?";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            System.out.println(manager.supermarket_id);
            preparedStmt.setInt(1, manager.supermarket_id);

            ResultSet employeeResult = preparedStmt.executeQuery();

            while (employeeResult.next()) {
                Employee employee = new Employee();
                employee.setEmployee_id(employeeResult.getInt("idemployee"));
                employee.setBudget(employeeResult.getDouble("budget"));
                employee.setNet_salary(employeeResult.getDouble("net_salary"));
                employee.setGross_salary(employeeResult.getDouble("gross_salary"));
                System.out.println(employee.employee_id);
                employees.put(employee.employee_id, employee);
            }

            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        payoutEmployees(employees, manager, supermarket_budget);
    }

    public void payoutEmployees(HashMap<Integer, Employee> employees, Manager manager, double supermarket_budget) {
        System.out.println("payoutEmployees");
        try {
            double grossAmount = 0;
            double salarySpendings = 0;
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            String employeeSql = "update employee set budget = ? where idemployee = ?";
            String supermarketSql = "update supermarket set budget = ? where idsupermarket = ?";

            for (int employee_id : employees.keySet()) {
                double new_budget = employees.get(employee_id).budget + employees.get(employee_id).net_salary;
                PreparedStatement preparedStmtEmployee = connectionTester.connection.prepareStatement(employeeSql);
                preparedStmtEmployee.setDouble(1, new_budget);
                preparedStmtEmployee.setInt(2, employees.get(employee_id).employee_id);
                preparedStmtEmployee.execute();
                grossAmount = grossAmount + employees.get(employee_id).gross_salary - employees.get(employee_id).net_salary;
                salarySpendings = salarySpendings + employees.get(employee_id).net_salary;
                System.out.println(employees.get(employee_id).employee_id);
                System.out.println(new_budget);
                System.out.println(grossAmount);
            }

            System.out.println("Supermarket budget: ");
            System.out.println(supermarket_budget);
            System.out.println(supermarket_budget + grossAmount);
            PreparedStatement preparedStmtSupermarket = connectionTester.connection.prepareStatement(supermarketSql);
            preparedStmtSupermarket.setDouble(1, supermarket_budget - salarySpendings + grossAmount);
            preparedStmtSupermarket.setInt(2, manager.supermarket_id);
            preparedStmtSupermarket.execute();
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
