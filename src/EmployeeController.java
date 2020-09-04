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

    public void showEmployeeActions() {
        view.showActions();
    }

    public int getEmployeeActionInput() {
        return model.getAction();
    }

    public void chooseEmployeeAction() {
        Scanner employeeInsertScanner = new Scanner(System.in);
        int employeeInsertInput = employeeInsertScanner.nextInt();

        if (employeeInsertInput == 1) {
            model.updateMyEmployeeValue();
        }
    }

    public void getEmployee(int userInstance, EmployeeController controller) {
        model.getEmployee(userInstance, controller, model);
    }

    public void deleteEmployee() {
        model.deleteEmployee();
    }

    public void updateEmployee() {
        model.updateEmployeeFindValue();
    }

    public void showEmployeeInsertAction() {
        view.viewEmployeeInsertAction(model.getFirst_name());
    }

    public void showEmployeeDeleteAction() {
        view.viewEmployeeDeleteAction(model.getEmployee_id());
    }

    public void showEmployeeUpdateAction() {
        view.viewEmployeeUpdateAction(model.getColumn_name());
    }
}
