import java.sql.*;
import java.util.Scanner;

public class Employee {
    int employee_id;
    String first_name;
    String last_name;
    int age;
    String gender;
    double budget;
    double gross_salary;
    double net_salary;
    boolean parttime;
    int department_id;
    int position_id;
    double monthly_hours;
    int supermarket_id;
    String column_name;
    int privileges;

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
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

    public double getGross_salary() {
        return gross_salary;
    }

    public void setNet_salary(double net_salary) {
        this.net_salary = net_salary;
    }

    public void setParttime(boolean parttime) {
        this.parttime = parttime;
    }

    public boolean getParttime() {
        return parttime;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setMonthly_hours(double monthly_hours) {
        this.monthly_hours = monthly_hours;
    }

    public double getMonthly_hours() {
        return monthly_hours;
    }

    public void setSupermarket_id(int supermarket_id) {
        this.supermarket_id = supermarket_id;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void calculateGrossSalary(double hours, double age) {
        double salary_per_hour = age / 4;
        double gross_salary = salary_per_hour * hours;
        this.gross_salary = gross_salary;
    }

    public int getManager_id() {
        int manager_id = 0;
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            Statement statement = connectionTester.connection.createStatement();
            String sql = "select supermarket.manager_idmanager from supermarket join employee on supermarket.idsupermarket = employee.supermarket_idsupermarket";

            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                manager_id = result.getInt("manager_idmanager");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return manager_id;
    }

    static int chooseDepartment() {
        Scanner input = new Scanner(System.in);
        String input_department = input.nextLine();
        input_department = input_department.replace(input_department, "\"" + input_department + "\"");
        System.out.println(input_department);
        int department_id;

        try {
            String host = "jdbc:mysql://localhost:3306/supermarket?serverTimezone=UTC";
            String uName = "root";
            String uPass = "ytj?yctcsrpvw";
            Connection con = DriverManager.getConnection(host, uName, uPass);
            System.out.println("Preparing to fetch department");

            Statement statement = con.createStatement();

            String sql = ("select iddepartment from department where department_name = " + input_department);
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                department_id = result.getInt("iddepartment");
            } else {
                department_id = 0;
            }

        } catch (SQLException err) {
            department_id = 0;
            System.out.println(err.getMessage());
        }

        return department_id;
    }

    static int choosePosition() {
        System.out.println("Please choose the position you'd like to give your new employee");
        Scanner input = new Scanner(System.in);
        String input_position = input.nextLine();
        input_position = input_position.replace(input_position, "\"" + input_position + "\"");
        System.out.println(input_position);
        int position_id;

        try {
            String host = "jdbc:mysql://localhost:3306/supermarket?serverTimezone=UTC";
            String uName = "root";
            String uPass = "ytj?yctcsrpvw";
            Connection con = DriverManager.getConnection(host, uName, uPass);

            Statement statement = con.createStatement();

            String sql = ("select idposition from position where position_name = " + input_position);
            System.out.println(input_position);
            System.out.println(sql);
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                position_id = result.getInt("idposition");
            } else {
                position_id = 0;
            }

        } catch (SQLException err) {
            position_id = 0;
            System.out.println(err.getMessage());
        }

        System.out.println(position_id);
        return position_id;
    }

    public void calculateNetSalary(double gross_salary) {

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to fetch income_tax");

            String sql = "select income_tax from supermarket where idsupermarket = " + supermarket_id;
            Statement stmt = connectionTester.connection.createStatement();

            ResultSet result = stmt.executeQuery(sql);

            if (result.next()) {
                double income_tax = result.getDouble("income_tax");
                net_salary = gross_salary - income_tax;
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void createDBInstance(Employee employee) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to create an employee");

            String sql = " insert into employee (first_name, last_name, age, gender, budget, gross_salary, net_salary, parrtime, department_iddepartment, uren_per_maand, supermarket_idsupermarket, position_idposition, privileges)" + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, employee.first_name);
            preparedStmt.setString(2, employee.last_name);
            preparedStmt.setInt(3, employee.age);
            preparedStmt.setString(4, employee.gender);
            preparedStmt.setDouble(5, employee.budget);
            preparedStmt.setDouble(6, employee.gross_salary);
            preparedStmt.setDouble(7, employee.net_salary);
            preparedStmt.setBoolean(8, employee.parttime);
            preparedStmt.setInt(9, employee.department_id);
            preparedStmt.setDouble(10, employee.monthly_hours);
            preparedStmt.setInt(11, employee.supermarket_id);
            preparedStmt.setInt(12, employee.position_id);
            preparedStmt.setInt(13, employee.privileges);

            preparedStmt.execute();
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateMyEmployeeValue(Employee employee) {
        try {
            System.out.println("The updatemyvalue function");
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("please enter the column you wish to update");
            String input_column = inputGetter.nextLine();
            input_column = input_column.replaceAll("^\"|\"$", "");

            String sql = "select " + input_column + " from employee where idemployee = " + employee.employee_id;

            Statement stmt = connectionTester.connection.createStatement();

            System.out.println(sql);
            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = result.getMetaData();

            String outputName = resultSetMetaData.getColumnName(1);
            String outputType = resultSetMetaData.getColumnTypeName(1);
            updateEmployeeValue(outputName, employee.employee_id, outputType);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateEmployeeFindValue() {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            ColumnGetter positionGetter = new ColumnGetter();
            positionGetter.getColumn("employee", "first_name");

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("please enter the id of the employee you wish to update");
            int input_id = inputGetter.nextInt();
            inputGetter.nextLine();
            System.out.println("please enter the column you wish to update");
            String input_column = inputGetter.nextLine();
            input_column = input_column.replaceAll("^\"|\"$", "");

            String sql = " select " + input_column + " from employee where idemployee = " + input_id;

            Statement stmt = connectionTester.connection.createStatement();

            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = result.getMetaData();

            if (result.next()) {
                String outputName = resultSetMetaData.getColumnName(1);
                String outputType = resultSetMetaData.getColumnTypeName(1);
                updateEmployeeValue(outputName, input_id, outputType);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateEmployeeValue(String outputValue, int outputId, String outputType) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            System.out.println("please enter the value you wish to give the column");
            Scanner inputGetter = new Scanner(System.in);


            String sql = " update employee set " + outputValue + " = ? where idemployee = " + outputId;
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);

            switch (outputType) {
                case "VARCHAR": {
                    String input_value = inputGetter.nextLine();
                    prpStmt.setString(1, input_value);
                    System.out.println("You're about to update " + outputValue + " to " + input_value + ". Are you sure you wish to proceed?");
                }
                case "INT": {
                    String input_value = inputGetter.nextLine();
                    prpStmt.setString(1, input_value);
                    System.out.println("You're about to update " + outputValue + " to " + input_value + ". Are you sure you wish to proceed?");
                    break;
                }
                case "DOUBLE": {
                    double input_value = inputGetter.nextDouble();
                    prpStmt.setDouble(1, input_value);
                    System.out.println("You're about to update " + outputValue + " to " + input_value + ". Are you sure you wish to proceed?");
                }
            }

            Scanner confirmScanner = new Scanner(System.in);
            String inputConfirmation = confirmScanner.nextLine();

            if (inputConfirmation.charAt(0) == 'y') {
                prpStmt.execute();
                setColumn_name(outputValue);
                System.out.println("Column " + outputValue + " updated successfully!");
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public Employee getEmployee(int userInstance) {
        Employee employee = new Employee();
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Statement statement = connectionTester.connection.createStatement();

            String sql = "select * from employee where idemployee = " + userInstance;
            ResultSet result = statement.executeQuery(sql);

            if (result.next()) {
                employee.setEmployee_id(result.getInt("idemployee"));
                employee.setFirst_name(result.getString("first_name"));
                employee.setLast_name(result.getString("last_name"));
                employee.setAge(result.getInt("age"));
                employee.setGender(result.getString("gender"));
                employee.setBudget(result.getDouble("budget"));
                employee.setParttime(result.getBoolean("parrtime"));
                employee.setGross_salary(result.getDouble("gross_salary"));
                employee.setNet_salary(result.getDouble("net_salary"));
                employee.setDepartment_id(result.getInt("department_iddepartment"));
                employee.setMonthly_hours(result.getDouble("uren_per_maand"));
                employee.setPosition_id(result.getInt("position_idposition"));
                employee.setSupermarket_id(result.getInt("supermarket_idsupermarket"));
                employee.setPrivileges(employee.position_id);
                System.out.println("Supermarket Manager id in getManager");
                System.out.println(employee.employee_id);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return employee;
    }

    public void setPrivileges(int idposition) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to fetch position");

            String sql = "select privileges from position where idposition = ?";
            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setInt(1, idposition);
            preparedStmt.execute();

            ResultSet result = preparedStmt.executeQuery();

            if (result.next()) {
                int privileges = result.getInt("privileges");
                this.privileges = privileges;
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteEmployee(boolean managerDelete, Employee employee) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            int input_id = 0;

            Scanner inputGetter = new Scanner(System.in);

            if (managerDelete) {
                ColumnGetter employeeGetter = new ColumnGetter();
                employeeGetter.getColumn("employee", "first_name");
                System.out.println("please enter the id of the employee you wish to delete");
                input_id = inputGetter.nextInt();
                System.out.println("Are you sure you wish to delete the employee with id: " + input_id + "?");
            } else {
                System.out.println("Are you sure you wish to resign?");
            }
            String confirmation = inputGetter.next();

            if (confirmation.charAt(0) == 'y') {

                String sql = "delete from employee where idemployee = ?";

                PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
                if (managerDelete) {
                    preparedStmt.setInt(1, input_id);
                } else if (!managerDelete) {
                    preparedStmt.setInt(1, employee.employee_id);
                }
                preparedStmt.execute();

                if (managerDelete) {
                    setEmployee_id(input_id);
                }
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public int getAction() {
        Scanner actionSelector = new Scanner(System.in);
        int employeeActionInput = actionSelector.nextInt();

        return employeeActionInput;
    }

    public static void createInstances(String action) {
        Employee employeeModel = new Employee();
        EmployeeView employeeView = new EmployeeView();
        EmployeeController employeeController = new EmployeeController(employeeModel, employeeView);

        if (action == "insert") {
            Menu.createEmployee(employeeController);
            employeeController.createEmployeeDBInstance();
            employeeController.showEmployeeInsertAction();
        } else if (action == "delete") {
            Employee employee = new Employee();
            employeeController.deleteEmployee(true, employee);
            employeeController.showEmployeeDeleteAction();
        } else if (action == "update") {
            employeeController.updateEmployee();
            employeeController.showEmployeeUpdateAction();
        }
    }
}
