import java.util.ArrayList;
import java.util.HashMap;

public class testProduct {
    int product_id;
    String product_name;
    double price;
    int in_stock;
    String amount;
    int category_id;
    int department_id;
    String column_name;
    int number;

    public static class Builder {
        private int product_id;
        private String product_name;
        private double price;
        private int in_stock;
        private String amount;
        private int category_id;
        private int department_id;
        private String column_name;
        private int number;

        public Builder(int product_id) {
            this.product_id = product_id;
        }

        public Builder productName(String product_name) {
            this.product_name = product_name;
            return this;
        }

        public Builder productPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder productInStock(int in_stock) {
            this.in_stock = in_stock;
            return this;
        }

        public Builder productAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Builder productNumber(int number) {
            this.number = number;
            return this;
        }

        public testProduct build() {
            testProduct testProduct = new testProduct();
            testProduct.product_id = this.product_id;
            testProduct.product_name = this.product_name;
            testProduct.price = this.price;
            testProduct.in_stock = this.in_stock;
            testProduct.amount = this.amount;
            testProduct.number = this.number;

            return testProduct;
        }
    }

    public static void main(String[] args) {
//        setArray();
        Manager manager = new Manager();
        int totalSalary = 1249;
        int reduction = 249;
        int addition = 12;

        totalSalary = totalSalary - reduction + addition;
        System.out.println(totalSalary);
        Employee employee = new Employee();

        testFunction(manager);
    }

    public static void testFunction(Object object) {
        if (object instanceof Manager) {
            System.out.println("1");
            System.out.println(((Manager) object).manager_id);
        } else if (object instanceof Employee) {
            System.out.println("2");
            System.out.println(((Employee) object).employee_id);
        }
    }
}
