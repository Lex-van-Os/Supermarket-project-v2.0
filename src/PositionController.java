public class PositionController {

    private Position model;
    private PositionView view;

    public PositionController(Position model, PositionView view) {
        this.model = model;
        this.view = view;
    }

    public void setPosition_name(String position_name) {
        model.setPosition_name(position_name);
    }

    public void createPositionDBInstance() {
        model.createDBInstance(model);
    }

    public void deletePosition() {
        model.deletePosition();
    }

    public void updatePosition() {
        model.updatePositionFindValue();
    }

    public void showPositionInsertAction() {
        view.viewPositionInsertAction(model.getPosition_name());
    }

    public void showPositionDeleteAction() {
        view.viewPositionDeleteAction(model.getPosition_id());
    }

    public void showPositionUpdateAction() {
        view.viewPositionUpdateAction(model.getColumn_name());
    }
}
