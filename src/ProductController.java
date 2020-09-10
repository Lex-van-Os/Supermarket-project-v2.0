public class ProductController {

    private Product model;
    private ProductView view;

    public ProductController(Product model, ProductView view) {
        this.model = model;
        this.view = view;
    }

    public void setProduct_name(String product_name) {
        model.setProduct_name(product_name);
    }

    public void setPrice(double price) {
        model.setPrice(price);
    }

    public void setIn_stock(int in_stock) {
        model.setIn_stock(in_stock);
    }

    public void setAmount(String amount) {
        model.setAmount(amount);
    }

    public void setCategory(int category_id) {
        model.setCategory(category_id);
    }

    public void setDepartment_id(int department_id) {
        model.setDepartment_id(department_id);
    }

    public void createProductDBInstance() {
        model.createDBInstance(model);
    }

    public void deleteProduct() {
        model.deleteProduct();
    }

    public void chooseProductAction(ProductController controller, int manager_id, Object userObject, int orderMethod) {
        model.chooseAction(controller, manager_id, userObject, orderMethod);
    }

    public void showProductCategories() {
        view.showCategories();
    }

    public void showProductInsertAction() {
        view.viewProductInsertAction(model.getProduct_name());
    }

    public void showProductDeleteAction() {
        view.viewProductDeleteAction(model.getProduct_id());
    }

    public void showProductUpdateAction() {
        view.viewProductUpdateAction(model.getColumn_name());
    }

    public void showProductOrderActions() {
        view.viewOrderActions();
    }

    public void showProductOrderMethods() {
        view.viewOrderMethods();
    }
}
