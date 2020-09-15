public class SupermarketController {
    private Supermarket model;
    private SupermarketView view;

    public SupermarketController(Supermarket model, SupermarketView view) {
        this.model = model;
        this.view = view;
    }

    public void setSupermarketName(String supermarketName) {
        model.setName(supermarketName);
    }

    public void setSupermarketBudget(double supermarketBudget) {
        model.setBudget(supermarketBudget);
    }

    public void setSupermarketIncome_tax(double income_tax) {
        model.setIncome_tax(income_tax);
    }

    public void createSupermarketDBInstance() {
        model.createDBInstance(model);
    }

    public void deleteSupermarket(Manager managerModel) {
        model.deleteSupermarket(managerModel);
    }

    public void updateSupermarket() {
        model.updateSupermarketFindValue();
    }

    public void showSupermarketInsertAction() {
        view.viewSupermarketInsertAction(model.getName());
    }

    public void showSupermarketrDeleteAction() {
        view.viewSupermarketDeleteAction();
    }

    public void showSupermarketUpdateAction() {
        view.viewSupermarketUpdateAction(model.getColumn_name());
    }
}