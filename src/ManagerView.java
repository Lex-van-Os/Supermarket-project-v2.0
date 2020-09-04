public class ManagerView {

    public void showActions() {
        System.out.println("1. Insert");
        System.out.println("2. Delete");
        System.out.println("3. Update");
    }

    public void showInsertActions() {
        System.out.println("1. Create a new department");
        System.out.println("2. Create a position");
        System.out.println("3. Hire an employee");
        System.out.println("4. Add product(s)");
        System.out.println("5. Payout employees");
    }

    public void showDeleteActions() {
        System.out.println("1. Delete a department");
        System.out.println("2. Delete a position");
        System.out.println("3. Fire an employee");
        System.out.println("4. Delete my supermarket");
        System.out.println("5. Resign");
        System.out.println("6. Remove product(s)");
    }

    public void showUpdateActions() {
        System.out.println("1. Update a department");
        System.out.println("2. Update a position");
        System.out.println("3. Update employee information");
        System.out.println("4. Update my supermarket information");
        System.out.println("5. Update my own information");
        System.out.println("6. Order or return product(s)");
    }

    public void viewManagerInsertAction(String first_name) {
        System.out.println("Manager " + first_name + " added successfully!");
    }

    public void viewManagerDeleteAction(String first_name, String last_name) {
        System.out.println(first_name + " " + last_name + " resigned succesfully!");
    }

    public void viewManagerUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }
}
