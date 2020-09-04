import java.sql.*;
import java.util.Scanner;

public class Position {
    int position_id;
    String position_name;
    String column_name;

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public static void createDBInstance(Position position) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to create position");

            String sql = " insert into position (position_name)" + " values (?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, position.position_name);

            preparedStmt.execute();

            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deletePosition() {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            InstanceFinder positionInstanceFinder = new InstanceFinder();
            positionInstanceFinder.findInstance("position", "position_name");

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("Please enter the id of the position you would like to delete");
            String input_id = inputGetter.nextLine();
            System.out.println("Are you sure you wish to delete the position with id: " + input_id + "?");
            String confirmation = inputGetter.nextLine();

            if (confirmation.charAt(0) == 'y') {

                String sql = "delete from position where idposition = ?";

                PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
                preparedStmt.setString(1, input_id);
                preparedStmt.execute();

                setPosition_id(Integer.parseInt(input_id));
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updatePositionFindValue() {

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("please enter the column you wish to update");
            String input_column = inputGetter.nextLine();

            ColumnGetter positionGetter = new ColumnGetter();
            positionGetter.getColumn("position", "position_name");

            System.out.println("please enter the name of the position you wish to update");
            String input_name = inputGetter.nextLine();
            input_name = input_name.replaceAll("(\\w+)","'$1'");

            String sql = "select " + input_column + " from position where " + input_column + " = " + input_name;

            Statement stmt = connectionTester.connection.createStatement();
            ResultSet result = stmt.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = result.getMetaData();
            String outputName = "";
            String outputType = "";

            while (result.next()){
                outputName = resultSetMetaData.getColumnName(1);
                outputType = resultSetMetaData.getColumnTypeName(1);
            }

            updatePositionValue(outputName, outputType, input_name);

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updatePositionValue(String outputName, String outputType, String inputName) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            System.out.println("please enter the value you wish to give the column");
            Scanner inputGetter = new Scanner(System.in);
            inputName = inputName.replace("'", "");


            String sql = " update department set " + outputName + " = ? where " + outputName + " = ?";
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);

            if (outputType == "VARCHAR") {
                System.out.println("VARCHAR");
                String input_value = inputGetter.nextLine();
                prpStmt.setString(1, input_value);
                prpStmt.setString(2, inputName);
                System.out.println("You're about to update " + inputName + " to " + input_value + ". Are you sure you wish to proceed?");
            }
            else if (outputType == "INT") {
                System.out.println("INT");
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

    public static void createInstances(String action){
        Position positionModel = new Position();
        PositionView positionView = new PositionView();
        PositionController positionController = new PositionController(positionModel, positionView);
        if(action == "insert") {
            createPosition(positionController);
            positionController.createPositionDBInstance();
            positionController.showPositionInsertAction();
        }
        else if(action == "delete") {
            positionController.deletePosition();
            positionController.showPositionDeleteAction();
        }
        else if(action == "update") {
            positionController.updatePosition();
            positionController.showPositionUpdateAction();
        }
    }

    static void createPosition(PositionController controller) {
        Scanner positionInputGetter = new Scanner(System.in);
        System.out.println("Please enter the name for your position: ");
        System.out.println("Position name: ");
        controller.setPosition_name(positionInputGetter.nextLine());
        controller.createPositionDBInstance();
    }
}
