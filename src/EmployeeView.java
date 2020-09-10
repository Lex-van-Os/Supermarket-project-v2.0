public class EmployeeView {

    public void showActions(Employee employee) {
        System.out.println("1. Update my own information");
        System.out.println("2. Resign");
        System.out.println("3. Buy a product");
        if (employee.privileges != 0) {
            System.out.println("4. Order product(s)");
            System.out.println("5. Return product(s)");
        }
        if (employee.privileges == 2) {
            System.out.println("6. Fire an employee");
        }
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
