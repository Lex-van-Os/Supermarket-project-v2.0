import java.util.Scanner;

// The menu is the main class as well as the class that the user should run when he wants to run the program
// The program will firstly ask for the role the user wants to use, two of these are work in progress
// After doing this, the program will run the instanceFinder to check if an instance already exists
// If not, it will prompt the user to create one. If one does exist, the user will be given the list of possible methods

public class Menu {

    public static void main(String[] args) {
        // Function that asks for the function
        InstanceFinder marketInstanceFinder = new InstanceFinder();
        marketInstanceFinder.findInstance("supermarket", "name");

        System.out.println("Welcome to the supermarket program, please choose as who you want to run the program:");
        Scanner functionGetter = new Scanner(System.in);
        System.out.println("1. God (Gain access to all functions) [WORK IN PROGRESS]");
        System.out.println("2. Manager");
        System.out.println("3. Employee");
        System.out.println("4. Customer [WORK IN PROGRESS]");
        int functionInput = functionGetter.nextInt();

        if (functionInput == 1) {
            InstanceFinder godInstanceFinder = new InstanceFinder();
            System.out.println("This function is work in progress");
            System.out.println("1. Create a supermarket");
            System.out.println("2. Create a manager");
            System.out.println("Would you like to create a supermarket or create a manager?");
            int startingInput = functionGetter.nextInt();
            if (startingInput == 1) {
//                Supermarket.createSupermarket();
            }
            if (startingInput == 2) {
//                Manager.createManager();
            }

        // Calling instanceFinder based on given role
        } else if (functionInput == 2) {
            InstanceFinder managerInstanceFinder = new InstanceFinder();
            managerInstanceFinder.findInstance("manager", "first_name");
        } else if (functionInput == 3) {
            InstanceFinder employeeInstanceFinder = new InstanceFinder();
            employeeInstanceFinder.findInstance("employee", "first_name");
        } else if (functionInput == 4) {
            InstanceFinder customerInstanceFinder = new InstanceFinder();
            System.out.println("This function is work in progress");
        }
    }

    static void selectCreateInstance(String instance, int userInstance) {
        // Method for asking for the possible methods and actions
        if (instance == "god") {
            System.out.println("This function is work in progress");
        }
        if (instance == "manager") {
            Manager managerModel = new Manager();
            ManagerView managerView = new ManagerView();
            ManagerController managerController = new ManagerController(managerModel, managerView);
            if (userInstance > 0) {
                Manager manager = managerController.getManager(userInstance, managerController);
                managerController.showManagerActions();
                managerController.chooseManagerActionType(userInstance, manager);
            } else {
                createManager(managerController);
            }
        }
        if (instance == "employee") {
            Employee employeeModel = new Employee();
            EmployeeView employeeView = new EmployeeView();
            EmployeeController employeeController = new EmployeeController(employeeModel, employeeView);
            if (userInstance > 0) {
                Employee employee = employeeController.getEmployee(userInstance, employeeController);
                employeeController.showEmployeeActions(employee);
                employeeController.chooseEmployeeAction(employee);
            } else {
                createEmployee(employeeController);
            }
        }
        if (instance == "customer") {
            System.out.println("This function is work in progress");
        }
        if (instance == "supermarket") {
            Supermarket supermarketModel = new Supermarket();
            SupermarketView supermarketView = new SupermarketView();
            SupermarketController supermarketController = new SupermarketController(supermarketModel, supermarketView);
            createSupermarket(supermarketController);
            supermarketController.createSupermarketDBInstance();
            supermarketController.showSupermarketInsertAction();
        }
    }

    static void selectCreateInstance(String instance) {
        int defaultUserInstance = 0;
        selectCreateInstance(instance, defaultUserInstance);
    }

    // Methods for creating an employee / manager / supermarket.
    // These might get split up later or moved to their corresponding classes

    static void createEmployee(EmployeeController controller) {
        System.out.println("Please enter the supermarket, department, position, first name, last name, age, gender, budget and parttime for your employee: ");
        Scanner employeeInputGetter = new Scanner(System.in);
        System.out.println("Supermarket: ");
        ColumnGetter supermarketGetter = new ColumnGetter();
        supermarketGetter.getColumn("supermarket", "name");
        controller.setEmployeeSupermarket_id(employeeInputGetter.nextInt());
        System.out.println("Department: ");
        ColumnGetter departmentGetter = new ColumnGetter();
        departmentGetter.getColumn("department", "department_name");
        controller.setEmployeeDepartment_id(employeeInputGetter.nextInt());
        System.out.println("Position: ");
        ColumnGetter positionGetter = new ColumnGetter();
        positionGetter.getColumn("position", "position_name");
        controller.setEmployeePosition_id(employeeInputGetter.nextInt());
        employeeInputGetter.nextLine();
        System.out.println("Firstname: ");
        controller.setEmployeeFirst_name(employeeInputGetter.nextLine());
        System.out.println("Lastname: ");
        controller.setEmployeeLast_name(employeeInputGetter.nextLine());
        System.out.println("Age: ");
        controller.setEmployeeAge(employeeInputGetter.nextInt());
        employeeInputGetter.nextLine();
        System.out.println("Gender: ");
        controller.setEmployeeGender(employeeInputGetter.nextLine());
        System.out.println("Budget: ");
        controller.setEmployeeBudget(employeeInputGetter.nextDouble());
        employeeInputGetter.nextLine();
        System.out.println("parttime: ");
        controller.setEmployeeParttime(employeeInputGetter.nextBoolean());
        System.out.println("Hours a month: ");
        controller.setEmployeeMonthly_hours(employeeInputGetter.nextDouble());
        System.out.println("Gross salary: ");
        if (controller.getEmployeeParttime()) {
            System.out.println("Calculating gross salary");
            controller.calculateEmployeeGrossSalary(controller.getEmployeeMonthly_hours(), controller.getEmployeeAge());
        } else if (!controller.getEmployeeParttime()) {
            System.out.println("How much would you like to pay your employee a month? (Note: The input is gross salary, net salary will be reduced by income tax)");
            controller.setEmployeeGross_salary(employeeInputGetter.nextDouble());
        }
        System.out.println("Privileges: ");
        System.out.println("Calculating privileges");
        controller.setEmployeePrivileges();
        System.out.println("Net salary: ");
        controller.calculateEmployeeNetSalary();
    }

    static void createManager(ManagerController controller) {
        System.out.println("Please enter the id, first name, last name, age, gender, gross salary, budget and supermarket for your manager: ");
        Scanner managerInputGetter = new Scanner(System.in);
        System.out.println("First name: ");
        controller.setManagerFirst_name(managerInputGetter.nextLine());
        System.out.println("Last name: ");
        controller.setManagerLast_name(managerInputGetter.nextLine());
        System.out.println("Age: ");
        controller.setManagerAge(managerInputGetter.nextInt());
        System.out.println("Gender: ");
        controller.setManagerGender(managerInputGetter.nextLine());
        System.out.println("Gross salary: ");
        controller.setManagerGross_salary(managerInputGetter.nextDouble());
        System.out.println("Budget: ");
        controller.setManagerBudget(managerInputGetter.nextDouble());
        System.out.println("Supermarket: ");
        ColumnGetter supermarketGetter = new ColumnGetter();
        supermarketGetter.getColumn("supermarket", "name");
        controller.setManagerSupermarket_id(managerInputGetter.nextInt());
    }

    static void createSupermarket(SupermarketController controller) {
        System.out.println("Please enter the name, budget and income tax for your supermarket: ");
        Scanner supermarketInputGetter = new Scanner(System.in);
        System.out.println("Name: ");
        controller.setSupermarketName(supermarketInputGetter.nextLine());
        System.out.println("Budget: ");
        controller.setSupermarketBudget(supermarketInputGetter.nextDouble());
        System.out.println("Income tax: ");
        controller.setSupermarketIncome_tax(supermarketInputGetter.nextDouble());
    }
}
