public class DepartmentView {
    public void viewDepartmentInsertAction(String department_name) {
        System.out.println("Department " + department_name + " added successfully!");
    }

    public void viewDepartmentDeleteAction(int department_id) {
        System.out.println("Department with id: " + department_id + " deleted successfully!");    }

    public void viewDepartmentUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }
}
