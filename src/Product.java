import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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

            InstanceFinder productInstanceFinder = new InstanceFinder();
            productInstanceFinder.findInstance("product", "product_name");

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

    public void updateProduct(ProductController controller, int manager_id) {
        HashMap<Integer, Integer> hashOrders = new HashMap<Integer, Integer>();
        Scanner input_id = new Scanner(System.in);
        Scanner input_action_scanner = new Scanner(System.in);
        boolean return_products = false;


        System.out.println("Please select whether you'd like to order or to return product(s): ");
        System.out.println("1. Order products");
        System.out.println("2. Return products");
        int input_action = input_action_scanner.nextInt();
        if (input_action == 2) {
            return_products = true;
        }

        System.out.println("Please enter the number of the category you'd like to change the information from:");
        controller.showProductCategories();
        int category_id = input_id.nextInt();

        System.out.println("Please select how you would like to print your products:");
        controller.showProductOrderMethods();
        int method = input_id.nextInt();
        ArrayList<String> filtered_results = getCategoryProducts(category_id, method);

        boolean breakLoop = false;

        while (!breakLoop) {
            for (int i = 0; i < filtered_results.size(); i++) {
                System.out.println(filtered_results.get(i));
            }

            System.out.println("\n");
            controller.showProductOrderActions();
            int action_input = input_id.nextInt();

            switch (action_input) {
                case 1:
                    System.out.println("Please enter the id of the product you'd like to add or return: ");
                    int product_input = input_id.nextInt();
                    System.out.println("Please enter the amount you'd like to order or return: ");
                    int product_input_amount = input_id.nextInt();
                    System.out.println("Product added to cart! ");
                    hashOrders.put(product_input, product_input_amount);
                    break;
                case 2:
                    category_id = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                case 3:
                    method = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                case 4:
                    category_id = input_id.nextInt();
                    method = input_id.nextInt();
                    filtered_results = getCategoryProducts(category_id, method);
                case 5:
                    if (hashOrders.isEmpty()) {
                        if (return_products = true) {
                            System.out.println("Please add a product to return before proceeding to return it");

                        } else {
                            System.out.println("Please add a product to your order before proceeding to check out");
                        }
                    } else {
                        addOrder(hashOrders, manager_id, return_products);
                    }
                case 6:
                    breakLoop = true;
                    break;
            }
        }
    }

    public ArrayList<String> getCategoryProducts(int category, int sortMethod) {
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

    public void addOrder(HashMap<Integer, Integer> orders, int manager_id, boolean return_products) {
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

                ResultSet result = prpStmt.executeQuery();
                while (result.next()) {
                    int id_product = result.getInt("idproduct");
                    String product_name = result.getString("product_name");
                    double price = result.getDouble("price");
                    int in_stock = result.getInt("in_stock");
                    String amount = result.getString("amount");
                    int ordered_amount = orders.get(product_id);
                    if (return_products) {
                        order_price = result.getDouble("price") * ordered_amount;
                    } else if (!return_products) {
                        order_price = result.getDouble("price") * ordered_amount - 0.10;
                    }

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

        reviewOrder(products, order_totalprice, manager_id, return_products);
    }

    public void reviewOrder(HashMap<Integer, Product> products, double order_totalprice, int manager_id, boolean return_products) {
        System.out.println("Standing order: ");
        for (int product_id : products.keySet()) {
            System.out.println("Product id - product name - ordered amount - order price");
            System.out.println(products.get(product_id).product_id + " - " + products.get(product_id).product_name + " - " + products.get(product_id).ordered_amount + " - " + products.get(product_id).order_price);
        }
        System.out.println(order_totalprice);

        if (!return_products) {
            System.out.println("Would you like to check-out your order?");
        } else if (return_products) {
            System.out.println("Would you like to return the selected products?");
        }
        System.out.println("1. Yes");
        System.out.println("2. No");

        Scanner checkoutGetter = new Scanner(System.in);
        int checkoutConfirm = checkoutGetter.nextInt();

        if (checkoutConfirm == 1) {
            System.out.println("Order confirmed! Processing order...");
            orderCheckOut(order_totalprice, products, manager_id, return_products);
        } else if (checkoutConfirm == 2) {
            System.out.println("Action cancelled");
        }
    }

    public void orderCheckOut(double product_totalprice, HashMap<Integer, Product> products, int manager_id, boolean return_products) {
        try {
            DBConnect connectionTester = new DBConnect();
            connectionTester.testConnection();
            double new_budget = 0;

            String sqlProduct = "update product set in_stock = ? where idproduct = ?";
            String sqlGetSupermarket = "select budget from supermarket where manager_idmanager = ?";
            String sqlSupermarket = "update supermarket set budget = ? where manager.idmanger = ?";

            for (int product_id : products.keySet()) {
                PreparedStatement prpStmtGetSupermarket = connectionTester.connection.prepareStatement(sqlGetSupermarket);
                prpStmtGetSupermarket.setInt(1, manager_id);

                ResultSet supermarketResult = prpStmtGetSupermarket.executeQuery();
                while(supermarketResult.next()) {
                    if (!return_products) {
                        new_budget = supermarketResult.getDouble("budget") - product_totalprice;
                    } else if (return_products) {
                        new_budget = supermarketResult.getDouble("budget") + product_totalprice;
                    }
                }

                PreparedStatement prpStmtProduct = connectionTester.connection.prepareStatement(sqlProduct);
                if (!return_products) {
                    prpStmtProduct.setInt(1, products.get(product_id).in_stock + products.get(product_id).ordered_amount);
                } else if (return_products) {
                    prpStmtProduct.setInt(1, products.get(product_id).in_stock - products.get(product_id).ordered_amount);
                }
                prpStmtProduct.setInt(2, products.get(product_id).product_id);
                prpStmtProduct.execute();
            }
            System.out.println("Products updated successfully!");

            PreparedStatement prpStmtSupermarket = connectionTester.connection.prepareStatement(sqlSupermarket);
            prpStmtSupermarket.setDouble(1, new_budget);
            prpStmtSupermarket.setInt(2,  manager_id);
            prpStmtSupermarket.execute();
            System.out.println("Supermarket updated successfully!");
            System.out.println("Order processed!");
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public static void createInstances(String action, int manager_id) {
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
        } else if (action == "update") {
            productController.updateProduct(productController, manager_id);
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
