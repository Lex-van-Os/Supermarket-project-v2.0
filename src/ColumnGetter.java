import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class ColumnGetter {
    ArrayList<Object> instances = new ArrayList<Object>();


    public void getColumn(String input_table, String input_column) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            input_table = input_table.replace("\"", "");
            input_column = input_column.replace("\"", "");
            String table_id = "id" + input_table;

            String sql = "select * from " + input_table;
            Statement statement = connectionTester.connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                int instanceId = result.getInt(table_id);
                String instanceNameResult = result.getString(input_column);
                System.out.println(instanceId + ": " + instanceNameResult);
//                instances.add( result.getObject(input_column));
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

//        for(int i = 0; i < instances.size(); i++) {
//            System.out.println(instances.get(i));
//        }
    }

    int chooseColumn(String input_id, String input_table, String input_column) {
        Scanner input = new Scanner(System.in);
        String instance_name = input.nextLine();
        int instance_id;

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            String sql = ("select " + input_id + " from " + input_table + " where " + input_column + " = ?");
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);
            prpStmt.setString(1, instance_name);
            System.out.println(prpStmt);
            ResultSet result = prpStmt.executeQuery();

            if (result.next()) {
                instance_id = result.getInt(input_id);
            } else {
                instance_id = 0;
            }

        } catch(SQLException err) {
            instance_id = 0;
            System.out.println(err.getMessage());
        }

        return instance_id;
    }
}
