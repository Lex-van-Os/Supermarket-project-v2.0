import com.mysql.cj.protocol.Resultset;

import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

// The product class, is the biggest class in the program.
// This class knows a builder pattern as well as the most complicated methods in this program

public class Product {
    int product_id;
    String product_name;
    double price;
    int in_stock;
    String amount;
    int category_id;
    int department_id;
    String column_name;
    int ordered_amount;
    double order_price;

    public static class Builder {
        private int product_id;
        private String product_name;
        private double price;
        private int in_stock;
        private String amount;
        private int ordered_amount;
        double order_price;

        public Builder(int product_id) {
            this.product_id = product_id;
        }

        public Product.Builder productName(String product_name) {
            this.product_name = product_name;
            return this;
        }

        public Product.Builder productPrice(double price) {
            this.price = price;
            return this;
        }

        public Product.Builder productInStock(int in_stock) {
            this.in_stock = in_stock;
            return this;
        }

        public Product.Builder productAmount(String amount) {
            this.amount = amount;
            return this;
        }

        public Product.Builder productOrderedAmount(int ordered_amount) {
            this.ordered_amount = ordered_amount;
            return this;
        }

        public Product.Builder productOrderPrice(double order_price) {
            this.order_price = order_price;
            return this;
        }

        // The Builder pattern
        // This is mainly used for the methods for adding or returning product orders and buying products
        // For these methods, products will have to be created with the necessary information (parameters)
        // Since a product doesn't require any parameters to construct it, a builder pattern has been added
        // This method is more efficient than calling setters and getters

        public Product build() {
            Product product = new Product();
            product.product_id = this.product_id;
            product.product_name = this.product_name;
            product.price = this.price;
            product.in_stock = this.in_stock;
            product.amount = this.amount;
            product.ordered_amount = this.ordered_amount;
            product.order_price = this.order_price;

            return product;
        }
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIn_stock(int in_stock) {
        this.in_stock = in_stock;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCategory(int category_id) {
        this.category_id = category_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getColumn_name() {
        return column_name;
    }

    public static void createDBInstance(Product product) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            System.out.println("Preparing to create product");

            String sql = "insert into product (product_name, price, in_stock, amount, category_idcategory, department_iddepartment)" + " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
            preparedStmt.setString(1, product.product_name);
            preparedStmt.setDouble(2, product.price);
            preparedStmt.setInt(3, product.in_stock);
            preparedStmt.setString(4, product.amount);
            preparedStmt.setInt(5, product.category_id);
            preparedStmt.setInt(6, product.department_id);

            preparedStmt.execute();
            connectionTester.connection.close();

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteProduct() {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            ColumnGetter productColumnGetter = new ColumnGetter();
            productColumnGetter.getColumn("product", "product_name");

            Scanner inputGetter = new Scanner(System.in);
            System.out.println("Please enter the id of the product you would like to delete");
            String input_id = inputGetter.nextLine();
            System.out.println("Are you sure you wish to delete the product with id: " + input_id + "?");
            String confirmation = inputGetter.nextLine();

            if (confirmation.charAt(0) == 'y') {

                String sql = "delete from product where idproduct = ?";

                PreparedStatement preparedStmt = connectionTester.connection.prepareStatement(sql);
                preparedStmt.setString(1, input_id);
                preparedStmt.execute();

                setProduct_id(Integer.parseInt(input_id));
                connectionTester.connection.close();
            } else {
                System.out.println("Action cancelled");
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void chooseAction(ProductController controller, int manager_id, Object userObject, int orderMethod) {
        // This method is called prior to the method for buying / returning / ordering a product.
        // All three of these methods include the printing of products based on their categories and sort method
        // Based on the any of these three options, the program will ask the player for input

        HashMap<Integer, Integer> hashOrders = new HashMap<Integer, Integer>();
        Scanner input_id = new Scanner(System.in);

        // Asking the user for the product category
        System.out.println("Please enter the number of the category you'd like to change the information from:");
        controller.showProductCategories();
        int category_id = input_id.nextInt();

        // Asking for the product order method
        System.out.println("Please select how you would like to print your products:");
        controller.showProductOrderMethods();
        int method = input_id.nextInt();
        ArrayList<String> filtered_results = getCategoryProducts(category_id, method);

        printProducts(filtered_results);

        System.out.println("\n");

        boolean breakSwitch = false;
        while (!breakSwitch) {

            System.out.println("Please select what you'd like to do:");
            controller.showProductOrderActions();
            int action_input = input_id.nextInt();
            switch (action_input) {
                case 1:
                    // Adding a product to order
                    printProducts(filtered_results);
                    System.out.println("Please enter the id of the product you'd like to add, return or buy: ");
                    int product_input = input_id.nextInt();
                    System.out.println("Please enter the amount you'd like to order, return or buy: ");
                    int product_input_amount = input_id.nextInt();
                    System.out.println("Product added to cart! ");
                    hashOrders.put(product_input, product_input_amount);
                    break;
                case 2:
                    // Changing the category
                    System.out.println("Please enter the category you'd like to view");
                    category_id = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                    printProducts(filtered_results);
                    break;
                case 3:
                    // Changing the order method
                    System.out.println("Please enter how you would like to print your products:");
                    method = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                    printProducts(filtered_results);
                    break;
                case 4:
                    // Changing both the category and order method
                    System.out.println("Please enter the category you'd like to view and how you'd like to print your products: ");
                    category_id = input_id.nextInt();
                    method = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                    printProducts(filtered_results);
                case 5:
                    // Proceeding to checkout after adding an order
                    if (hashOrders.isEmpty()) {
                        if (orderMethod == 1 || orderMethod == 2) {
                            System.out.println("Please add a product to your order before proceeding to check out");
                        } else if (orderMethod == 3) {
                            System.out.println("Please add a product to return before proceeding to return it");
                        }
                    } else {
                        addOrder(hashOrders, manager_id, orderMethod, userObject);
                    }
                case 6:
                    breakSwitch = true;
            }
        }
    }

    public void printProducts(ArrayList<String> filteredResults) {
        // Method for printing the list of available products
        for (int i = 0; i < filteredResults.size(); i++) {
            System.out.println(filteredResults.get(i));
        }
    }

    public ArrayList<String> getCategoryProducts(int category, int sortMethod) {
        // Method for constructing the list of available products, this list will be returned to the chooseAction method
        ArrayList<String> filtered_results = new ArrayList<>();

        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            String sql;

            String alphSql = " order by product_name asc";
            String stockSql = " and in_stock = 0";
            String stockAscSql = " order by in_stock asc";
            String baseSql = "select idproduct, product_name, in_stock from product where category_idcategory = ?";
            switch (sortMethod) {
                case 1:
                    sql = baseSql + alphSql;
                    break;
                case 2:
                    sql = baseSql + stockSql;
                    break;
                case 3:
                    sql = baseSql + stockAscSql;
                    break;
                default:
                    sql = "";
                    break;
            }
            PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);
            prpStmt.setInt(1, category);
            ResultSet result = prpStmt.executeQuery();

            while (result.next()) {
                int product_id = result.getInt("idproduct");
                String productname = result.getString("product_name");
                int instock = result.getInt("in_stock");

                String sqlresult = product_id + ". " + productname + " - in stock: " + instock;
                filtered_results.add(sqlresult);
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        return filtered_results;
    }

    public void addOrder(HashMap<Integer, Integer> orders, int manager_id, int orderMethod, Object userObject) {
        // After adding orders and proceeding to checkout, the orders will be processed
        // Orders are put into a hashmap and are itterated over so that the necessary information can be requested
        // Processed orders are then put into a new hashmap, then the reviewOrder method is called
        HashMap<Integer, Product> products = new HashMap<Integer, Product>();
        double order_totalprice = 0;
        double order_price = 0;
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();

            for (int product_id : orders.keySet()) {

                String sql = "select * from product where idproduct = ?";
                PreparedStatement prpStmt = connectionTester.connection.prepareStatement(sql);
                prpStmt.setInt(1, product_id);

                // Not only is information requested, information like the total price is also processed
                ResultSet result = prpStmt.executeQuery();
                while (result.next()) {
                    int id_product = result.getInt("idproduct");
                    String product_name = result.getString("product_name");
                    double price = result.getDouble("price");
                    int in_stock = result.getInt("in_stock");
                    String amount = result.getString("amount");
                    int ordered_amount = orders.get(product_id);
                    if (orderMethod == 2 || orderMethod == 3) {
                        order_price = result.getDouble("price") * ordered_amount;
                    } else if (orderMethod == 1) {
                        order_price = result.getDouble("price") * ordered_amount - 0.10;
                    }

                    // Calling the builder to store the information in a class
                    products.put(id_product, new Product.Builder(id_product)
                            .productName(product_name)
                            .productPrice(price)
                            .productInStock(in_stock)
                            .productAmount(amount)
                            .productOrderedAmount(ordered_amount)
                            .productOrderPrice(order_price)
                            .build());
                    order_totalprice = order_totalprice + order_price;
                }
            }

        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

        reviewOrder(products, order_totalprice, manager_id, orderMethod, userObject);
    }

    public void reviewOrder(HashMap<Integer, Product> products, double order_totalprice, int manager_id, int orderMethod, Object userObject) {
        // The reviewOrder is used to print the information of the order
        System.out.println("Standing order: ");
        for (int product_id : products.keySet()) {
            System.out.println("Product id - product name - ordered amount - order price");
            System.out.println(products.get(product_id).product_id + " - " + products.get(product_id).product_name + " - " + products.get(product_id).ordered_amount + " - " + products.get(product_id).order_price);
        }
        System.out.println(order_totalprice);

        if (orderMethod == 1 || orderMethod == 2) {
            System.out.println("Would you like to check-out your order?");
        } else if (orderMethod == 3) {
            System.out.println("Would you like to return the selected products?");
        }
        System.out.println("1. Yes");
        System.out.println("2. No");

        Scanner checkoutGetter = new Scanner(System.in);
        int checkoutConfirm = checkoutGetter.nextInt();

        if (checkoutConfirm == 1) {
            System.out.println("Order confirmed! Processing order...");
            orderCheckOut(order_totalprice, products, manager_id, orderMethod, userObject);
        } else if (checkoutConfirm == 2) {
            System.out.println("Action cancelled");
        }
    }

    public void orderCheckOut(double product_totalprice, HashMap<Integer, Product> products, int manager_id, int orderMethod, Object userObject) {
        // CRUD method for the orders and database tables
        // The stock of the product will be updated based on the return or order method choosen
        // The supermarket budget will also be updated based on the choosen method
        // The SQL for buying is empty, this is because the SQL differs between employee and manager
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            double new_budget = 0;

            // The SQL for buying is empty, this is because the SQL differs between employee and manager
            // Before calling sqlSupermarket, sqlGetSupermarket is called to verify the correct supermarket
            String sqlBuy = "";
            String sqlProduct = "update product set in_stock = ? where idproduct = ?";
            String sqlGetSupermarket = "select budget from supermarket where manager_idmanager = ?";
            String sqlSupermarket = "update supermarket set budget = ? where manager_idmanager = ?";

            for (int product_id : products.keySet()) {
                PreparedStatement prpStmtGetSupermarket = connectionTester.connection.prepareStatement(sqlGetSupermarket);
                prpStmtGetSupermarket.setInt(1, manager_id);

                // For each product, the supermarket budget is altered
                ResultSet supermarketResult = prpStmtGetSupermarket.executeQuery();
                while (supermarketResult.next()) {
                    if (orderMethod == 1) {
                        new_budget = supermarketResult.getDouble("budget") - product_totalprice;
                    } else if (orderMethod == 3 || orderMethod == 2) {
                        new_budget = supermarketResult.getDouble("budget") + product_totalprice;
                    }
                }

                // For each product, the stock of the product is altered
                PreparedStatement prpStmtProduct = connectionTester.connection.prepareStatement(sqlProduct);
                if (orderMethod == 1) {
                    prpStmtProduct.setInt(1, products.get(product_id).in_stock + products.get(product_id).ordered_amount);
                } else if (orderMethod == 3 || orderMethod == 2) {
                    prpStmtProduct.setInt(1, products.get(product_id).in_stock - products.get(product_id).ordered_amount);
                }
                prpStmtProduct.setInt(2, products.get(product_id).product_id);
                prpStmtProduct.execute();
            }
            System.out.println("Products updated successfully!");

            // The SQL for updating the supermarket is prepared
            // The new budget is the total price of all the products
            PreparedStatement prpStmtSupermarket = connectionTester.connection.prepareStatement(sqlSupermarket);
            prpStmtSupermarket.setDouble(1, new_budget);
            prpStmtSupermarket.setInt(2, manager_id);
            prpStmtSupermarket.execute();
            System.out.println("Supermarket updated successfully!");

            // When buying, the budget of the role is also updated.
            // To find the correct user to update, there are two possible SQL's, one for the id of employee and one for manager
            if (orderMethod == 2) {
                if (userObject instanceof Manager) {
                    sqlBuy = "update manager set budget = ? where idmanager = ?";
                    PreparedStatement prpStmtManager = connectionTester.connection.prepareStatement(sqlBuy);
                    prpStmtManager.setDouble(1, ((Manager) userObject).budget - product_totalprice);
                    prpStmtManager.setInt(2, ((Manager) userObject).manager_id);
                    prpStmtManager.execute();
                } else if (userObject instanceof Employee) {
                    sqlBuy = "update employee set budget = ? where idemployee = ?";
                    PreparedStatement prpStmtEmployee = connectionTester.connection.prepareStatement(sqlBuy);
                    prpStmtEmployee.setDouble(1, ((Employee) userObject).budget - product_totalprice);
                    prpStmtEmployee.setInt(2, ((Employee) userObject).employee_id);
                    prpStmtEmployee.execute();
                }

            }
            System.out.println("Order processed!");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void createInstances(String action, int manager_id, Object userObject, int orderMethod) {
        // Again, an "intersection" type method that will call the correct function
        // Note that the update and buy action will call the same method, only with different parameters
        Product productModel = retrieveProduct();
        ProductView productView = new ProductView();
        ProductController productController = new ProductController(productModel, productView);

        if (action == "insert") {
            createProduct(productController);
            productController.createProductDBInstance();
            productController.showProductInsertAction();
        } else if (action == "delete") {
            productController.deleteProduct();
            productController.showProductDeleteAction();
        } else if (action == "update" || action == "buy") {
            productController.chooseProductAction(productController, manager_id, userObject, orderMethod);
        }
    }

    static void createProduct(ProductController controller) {
        Scanner productInputGetter = new Scanner(System.in);
        System.out.println("Please enter the name for your product: ");
        System.out.println("Product name: ");
        controller.setProduct_name(productInputGetter.nextLine());
        System.out.println("Price: ");
        controller.setPrice(productInputGetter.nextDouble());
        System.out.println("In stock: ");
        controller.setIn_stock(productInputGetter.nextInt());
        productInputGetter.nextLine();
        System.out.println("Amount: ");
        controller.setAmount(productInputGetter.nextLine());
        System.out.println("Category (Please enter the number of the category): ");
        controller.showProductCategories();
        controller.setCategory(productInputGetter.nextInt());
        ColumnGetter departmentGetter = new ColumnGetter();
        departmentGetter.getColumn("department", "department_name");
        controller.setDepartment_id(productInputGetter.nextInt());
    }

    private static Product retrieveProduct() {
        Product productModel = new Product();
        return productModel;
    }
}
