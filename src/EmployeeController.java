import java.util.Scanner;

public class EmployeeController {
    private Employee model;
    private EmployeeView view;

    public EmployeeController(Employee model, EmployeeView view) {
        this.model = model;
        this.view = view;
    }

    public void setEmployeeFirst_name(String first_name) {
        model.setFirst_name(first_name);
    }

    public void setEmployeeLast_name(String last_name) {
        model.setLast_name(last_name);
    }

    public void setEmployeeAge(int age) {
        model.setAge(age);
    }

    public int getEmployeeAge() {
        return model.getAge();
    }

    public void setEmployeeGender(String gender) {
        model.setGender(gender);
    }

    public void setEmployeeBudget(double budget) {
        model.setBudget(budget);
    }

    public void setEmployeeGross_salary(double gross_salary) {
        model.setGross_salary(gross_salary);
    }

    public double getEmployeeGross_salary() {
        return model.getGross_salary();
    }

    public void setEmployeeNet_salary(double net_salary) {
        model.setNet_salary(net_salary);
    }

    public void setEmployeeParttime(boolean parttime) {
        model.setParttime(parttime);
    }

    public boolean getEmployeeParttime() {
        return model.getParttime();
    }

    public void setEmployeeDepartment_id(int department_id) {
        model.setDepartment_id(department_id);
    }

    public void setEmployeePosition_id(int position_id) {
        model.setPosition_id(position_id);
    }

    public void setEmployeeMonthly_hours(double monthly_hours) {
        model.setMonthly_hours(monthly_hours);
    }

    public double getEmployeeMonthly_hours() {
        return model.getMonthly_hours();
    }

    public void setEmployeeSupermarket_id(int supermarket_id) {
        model.setSupermarket_id(supermarket_id);
    }

    public void createEmployeeDBInstance() {
        model.createDBInstance(model);
    }

    public void calculateEmployeeNetSalary() {
        model.calculateNetSalary(getEmployeeGross_salary());
    }

    public void calculateEmployeeGrossSalary(double hours, double age) {
        model.calculateGrossSalary(hours, age);
    }

    public void showEmployeeActions(Employee employee) {
        view.showActions(employee);
    }

    public int getEmployeeActionInput() {
        return model.getAction();
    }

    public void chooseEmployeeAction(Employee employee) {
        // The chooseEmployeeAction method will show a list of available functions
        // The shown methods are based on the CRUD the user has choosen to perform as well as the employee privilege level
        Scanner employeeInsertScanner = new Scanner(System.in);
        int employeeInsertInput = employeeInsertScanner.nextInt();
        int manager_id = model.getManager_id();

        switch (employeeInsertInput) {
            case 1:
                model.updateMyEmployeeValue(employee);
                break;
            case 2:
                model.deleteEmployee(false, employee);
                break;
            case 3:
                Product.createInstances("buy", manager_id, employee, 2);
                break;
            case 4:
                if (employee.privileges != 0) {
                    Product.createInstances("update", manager_id, employee, 1);
                }
                break;
            case 5:
                if (employee.privileges != 0) {
                    Product.createInstances("update", manager_id, employee, 3);
                }
                break;
            case 6:
                if (employee.privileges == 2) {
                    model.deleteEmployee(true, employee);
                }
                break;
        }
    }

    public Employee getEmployee(int userInstance, EmployeeController controller) {
        return model.getEmployee(userInstance);
    }

    public void deleteEmployee(boolean managerDelete, Employee employee) {
        model.deleteEmployee(managerDelete, employee);
    }

    public void updateEmployee() {
        model.updateEmployeeFindValue();
    }

    // Methods for viewing information

    public void showEmployeeInsertAction() {
        view.viewEmployeeInsertAction(model.getFirst_name());
    }

    public void showEmployeeDeleteAction() {
        view.viewEmployeeDeleteAction(model.getEmployee_id());
    }

    public void showEmployeeUpdateAction() {
        view.viewEmployeeUpdateAction(model.getColumn_name());
    }

    public void setEmployeePrivileges() {
        model.setPrivileges(model.getPosition_id());
    }
}
