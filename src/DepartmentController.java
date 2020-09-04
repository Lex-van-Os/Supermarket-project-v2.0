public class DepartmentController {

    private Department model;
    private DepartmentView view;

    public DepartmentController(Department model, DepartmentView view) {
        this.model = model;
        this.view = view;
    }

    public void setDepartment_name(String department_name) {
        model.setDepartment_name(department_name);
    }

    public String getDepartment_name() {
        return model.getDepartment_name();
    }

    public void createDepartmentDBInstance() {
        model.createDBInstance(model);
    }

    public void deleteDepartment() {
        model.deleteDepartment();
    }

    public void updateDepartment() {
        model.updateDepartmentFindValue();
    }

    public void showDepartmentInsertAction() {
        view.viewDepartmentInsertAction(model.getDepartment_name());
    }

    public void showDepartmentDeleteAction() {
        view.viewDepartmentDeleteAction(model.getDepartment_id());
    }

    public void showDepartmentUpdateAction() {
        view.viewDepartmentUpdateAction(model.getColumn_name());
    }
}
