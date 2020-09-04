import java.sql.*;
import java.util.Scanner;

public class Department {
    int department_id;
    String department_name;
    String column_name;

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public static void createDBInstance(Department department) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to create department");

            String sql = " insert into department (department_name)" + " values (?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, department.department_name);

            preparedStmt.execute();
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteDepartment() {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            InstanceFinder employeeInstanceFinder = new InstanceFinder();
            employeeInstanceFinder.findInstance("department", "department_name");

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("Please enter the id of the department you would like to delete");
            String input_id = inputGetter.nextLine();
            System.out.println("Are you sure you wish to the department with id: " + input_id + "?");
            String confirmation = inputGetter.nextLine();

            if (confirmation.charAt(0) == 'y') {

                String sql = "delete from department where iddepartment = ?";

                PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
                preparedStmt.setString(1, input_id);
                preparedStmt.execute();

                setDepartment_id(Integer.parseInt(input_id));
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateDepartmentFindValue() {

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("please enter the column you wish to update");
            String input_column = inputGetter.nextLine();

            ColumnGetter departmentGetter = new ColumnGetter();
            departmentGetter.getColumn("department", "department_name");

            System.out.println("please enter the name of the department you wish to update");
            String input_name = inputGetter.nextLine();
            input_name = input_name.replaceAll("(\\w+)","'$1'");

            String sql = "select " + input_column + " from department where " + input_column + " = " + input_name;

            Statement stmt = connectionTester.connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            String outputName = "";
            String outputType = "";

            while (result.next()){
                outputName = resultSetMetaData.getColumnName(1);
                outputType = resultSetMetaData.getColumnTypeName(1);
            }

            updateDepartmentValue(outputName, outputType, input_name);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateDepartmentValue(String outputName, String outputType, String inputName) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            System.out.println("please enter the value you wish to give the column");
            Scanner inputGetter = new Scanner(System.in);
            inputName = inputName.replace("'", "");


            String sql = " update department set " + outputName + " = ? where " + outputName + " = ?";
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);

            if (outputType == "VARCHAR") {
                String input_value = inputGetter.nextLine();
                prpStmt.setString(1, input_value);
                prpStmt.setString(2, inputName);
                System.out.println("You're about to update " + inputName + " to " + input_value + ". Are you sure you wish to proceed?");
            }
            else if (outputType == "INT") {
                int input_value = inputGetter.nextInt();
                prpStmt.setInt(1, input_value);
                prpStmt.setString(2, inputName);
                System.out.println("You're about to update " + inputName + " to " + input_value + ". Are you sure you wish to proceed?");
            }

            Scanner confirmScanner = new Scanner(System.in);
            String inputConfirmation = confirmScanner.nextLine();

            if (inputConfirmation.charAt(0) == 'y') {
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

    public static void createInstances(String action) {
        Department departmentModel = new Department();
        DepartmentView departmentView = new DepartmentView();
        DepartmentController departmentController = new DepartmentController(departmentModel, departmentView);
        if(action == "insert") {
            createDepartment(departmentController);
            departmentController.showDepartmentInsertAction();
        }

        else if(action == "delete") {
            departmentController.deleteDepartment();
            departmentController.showDepartmentDeleteAction();
        }

        else if(action == "update") {
            departmentController.updateDepartment();
            departmentController.showDepartmentUpdateAction();
        }
    }

    static void createDepartment(DepartmentController controller) {
        Scanner departmentInputGetter = new Scanner(System.in);
        System.out.println("Please enter the name for your department: ");
        System.out.println("Department name: ");
        controller.setDepartment_name(departmentInputGetter.nextLine());
        controller.createDepartmentDBInstance();
    }
}
