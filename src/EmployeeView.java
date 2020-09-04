public class EmployeeView {

    public void showActions() {
        System.out.println("1. Update my own information");
    }

    public void viewEmployeeInsertAction(String first_name) {
        System.out.println("Employee " + first_name + " added successfully!");
    }

    public void viewEmployeeDeleteAction(int employee_id) {
        System.out.println("Employee with id: " + employee_id + " deleted successfully!");    }

    public void viewEmployeeUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }
}
