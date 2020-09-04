import java.util.Scanner;

public class ManagerController {
    private Manager model;
    private ManagerView view;

    public ManagerController(Manager model, ManagerView view) {
        this.model = model;
        this.view = view;
    }

    public void setManagerFirst_name(String first_name) {
        model.setFirst_name(first_name);
    }

    public void setManagerLast_name(String last_name) {
        model.setLast_name(last_name);
    }

    public void setManagerAge(int age) {
        model.setAge(age);
    }

    public void setManagerGender(String gender) {
        model.setGender(gender);
    }

    public void setManagerGross_salary(double gross_salary) {
        model.setGross_salary(gross_salary);
    }

    public void setManagerBudget(double budget) {
        model.setBudget(budget);
    }

    public void setManagerSupermarket_id(int supermarket_id) {
        model.setSupermarket_id(supermarket_id);
    }

    public void createManagerDBInstance() {
        model.createDBInstance(model);
    }

    public void showManagerActions() {
        view.showActions();
    }

    public void chooseManagerActionType(int userInstance) {
        Scanner managerActionTypeScanner = new Scanner(System.in);
        int managerActionTypeInput = managerActionTypeScanner.nextInt();

        switch (managerActionTypeInput) {
            case 1 -> {
                view.showInsertActions();
                chooseManagerInsertAction(userInstance);
            }
            case 2 -> {
                view.showDeleteActions();
                chooseManagerDeleteAction(userInstance);
            }
            case 3 -> {
                view.showUpdateActions();
                chooseManagerUpdateAction(userInstance);
            }
        }
    }

    public void chooseManagerInsertAction(int userInstance) {
        Scanner managerInsertScanner = new Scanner(System.in);
        int managerInsertInput = managerInsertScanner.nextInt();

        switch (managerInsertInput) {
            case 1:
                Department.createInstances("insert");
            case 2:
                Position.createInstances("insert");
            case 3:
                Employee.createInstances("insert");
            case 4:
                Product.createInstances("insert", userInstance);
            case 5:
                model.payoutEmployees();
        }
    }

    public void chooseManagerDeleteAction(int userInstance) {
        Scanner managerDeleteScanner = new Scanner(System.in);
        int managerDeleteInput = managerDeleteScanner.nextInt();
        switch (managerDeleteInput) {
            case 1:
                Department.createInstances("delete");
            case 2:
                Position.createInstances("delete");
            case 3:
                Employee.createInstances("delete");
            case 4:
                Supermarket.createInstances("delete", model);
            case 5:
                model.deleteManager(model, userInstance);
                showManagerDeleteAction();
            case 6:
                Product.createInstances("delete", userInstance);

        }
    }

    public void chooseManagerUpdateAction(int userInstance) {
        Scanner managerUpdateScanner = new Scanner(System.in);
        int managerUpdateInput = managerUpdateScanner.nextInt();
        switch (managerUpdateInput) {
            case 1:
                Department.createInstances("update");
            case 2:
                Position.createInstances("update");
            case 3:
                Employee.createInstances("update");
            case 4:
                Supermarket.createInstances("update", model);
            case 5:
                model.updateManager(userInstance);
                showManagerUpdateAction();
            case 6:
                Product.createInstances("update", userInstance);
        }
    }

    public void showManagerInsertAction() {
        view.viewManagerInsertAction(model.getFirst_name());
    }

    public void showManagerDeleteAction() {
        view.viewManagerDeleteAction(model.getFirst_name(), model.getLast_name());
    }

    public void showManagerUpdateAction() {
        view.viewManagerUpdateAction(model.getColumn_name());
    }
}
