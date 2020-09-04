public class ProductView {

    public void showCategories() {
        System.out.println("1. Potato, rice, pasta");
        System.out.println("2. Meat, fish, vegetarian");
        System.out.println("3. Vegetables");
        System.out.println("4. Fruit");
        System.out.println("5. Cooking, soups, meals");
        System.out.println("6. Frozen foods");
        System.out.println("7. Bread, cereals, toppings");
        System.out.println("8. Dairy, eggs, butter");
        System.out.println("9. Baby, toddler");
        System.out.println("10. Householding, animals, service desk");
    }

    public void viewProductInsertAction(String product_name) {
        System.out.println("Product " + product_name + " added successfully!");
    }

    public void viewProductDeleteAction(int product_id) {
        System.out.println("Product with id: " + product_id + " deleted successfully!");    }

    public void viewProductUpdateAction(String column_name) {
        System.out.println("Column " + column_name + " updated successfully!");
    }

    public void viewOrderActions() {
        System.out.println("1. Add or return a product");
        System.out.println("2. Choose different category");
        System.out.println("3. Choose another order selection");
        System.out.println("4. Choose different category and order");
        System.out.println("5. Proceed to check-out");
        System.out.println("6. Break");
    }

    public void viewOrderMethods() {
        System.out.println("1. Alphabetical");
        System.out.println("2. Out of stock");
        System.out.println("3. In stock ascending");
    }
}
