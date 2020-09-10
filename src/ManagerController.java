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

    public void chooseManagerActionType(int userInstance, Manager manager) {
        Scanner managerActionTypeScanner = new Scanner(System.in);
        int managerActionTypeInput = managerActionTypeScanner.nextInt();

        switch (managerActionTypeInput) {
            case 1 -> {
                view.showInsertActions();
                chooseManagerInsertAction(userInstance, manager);
            }
            case 2 -> {
                view.showDeleteActions();
                chooseManagerDeleteAction(userInstance, manager);
            }
            case 3 -> {
                view.showUpdateActions();
                chooseManagerUpdateAction(userInstance, manager);
            }
        }
    }

    public void chooseManagerInsertAction(int userInstance, Manager manager) {
        Scanner managerInsertScanner = new Scanner(System.in);
        int managerInsertInput = managerInsertScanner.nextInt();

        switch (managerInsertInput) {
            case 1:
                Department.createInstances("insert");
                break;
            case 2:
                Position.createInstances("insert");
                break;
            case 3:
                Employee.createInstances("insert");
                break;
            case 4:
                Product.createInstances("insert", userInstance, manager, 0);
                break;
            case 5:
                double supermarket_budget = model.getSupermarketInstance(manager);
                model.getEmployeeInstances(manager, supermarket_budget);
        }
    }

    public void chooseManagerDeleteAction(int userInstance, Manager manager) {
        Scanner managerDeleteScanner = new Scanner(System.in);
        int managerDeleteInput = managerDeleteScanner.nextInt();
        switch (managerDeleteInput) {
            case 1:
                Department.createInstances("delete");
                break;
            case 2:
                Position.createInstances("delete");
                break;
            case 3:
                Employee.createInstances("delete");
                break;
            case 4:
                Supermarket.createInstances("delete", model);
                break;
            case 5:
                model.deleteManager(model, userInstance);
                showManagerDeleteAction();
                break;
            case 6:
                Product.createInstances("delete", userInstance, manager, 0);
                break;
        }
    }

    public void chooseManagerUpdateAction(int userInstance, Manager manager) {
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
                Product.createInstances("update", userInstance, manager, 1);
            case 7:
                Product.createInstances("update", userInstance, manager, 3);
            case 8:
                Product.createInstances("buy", userInstance, manager, 2);
        }
    }

    public Manager getManager(int userInstance, ManagerController controller) {
        return model.getManager(userInstance);
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
