public class PositionView {
    public void viewPositionInsertAction(String position_name) {
        System.out.println("Position " + position_name + " added successfully!");
    }

    public void viewPositionDeleteAction(int position_id) {
        System.out.println("Position with id: " + position_id + " deleted successfully!");    }

    public void viewPositionUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }
}
