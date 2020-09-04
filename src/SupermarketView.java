public class SupermarketView {
    public void viewSupermarketInsertAction(String name) {
        System.out.println("Supermarket " + name + " added successfully!");
    }

    public void viewSupermarketDeleteAction() {
        System.out.println("Supermarket deleted successfully!");
    }

    public void viewSupermarketUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }
}
