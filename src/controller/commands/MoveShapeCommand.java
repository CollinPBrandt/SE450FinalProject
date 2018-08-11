package controller.commands;

import controller.interfaces.IUndoable;
import controller.lists.ShapeListManager;
import model.shapes.data.ShapeObject;
import view.mouse.MouseDragDimensions;
import model.interfaces.ICommand;

public class MoveShapeCommand implements ICommand, IUndoable {

    private MouseDragDimensions dimensions;
    private ShapeListManager shapeListManager;
    private boolean draggedRight;
    private boolean draggedDown;

    public MoveShapeCommand(MouseDragDimensions dimensions, boolean draggedRight, boolean draggedDown, ShapeListManager shapeListManager) {
        this.dimensions = dimensions;
        this.shapeListManager = shapeListManager;
        this.draggedRight = draggedRight;
        this.draggedDown = draggedDown;
    }

    @Override
    public void execute() {
        moveShape();
    }

    /**/
    private void moveShape(){
        for(ShapeObject shape : shapeListManager.getSelectedShapeListObject().getList()){
            //if the shape was draw by dragging top left to bottom right
            if(draggedRight && draggedDown){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() + dimensions.getWidth(),
                        shape.getDimensions().getStartY() + dimensions.getHeight());
            }
            //if the shape was drawn by dragging bottom left to top right
            else if(draggedRight){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() + dimensions.getWidth(),
                        shape.getDimensions().getStartY() - dimensions.getHeight());
            }
            //if the shape was drawn by dragging top right to bottom left
            else if(draggedDown){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() - dimensions.getWidth(),
                        shape.getDimensions().getStartY() + dimensions.getHeight());
            }
            //if the shape was drawn by dragging bottom right to top left
            else{
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() - dimensions.getWidth(),
                        shape.getDimensions().getStartY() - dimensions.getHeight());
            }
        }
        shapeListManager.getShapeListObject().notifyObservers();    //clear and redraw everything after move
        CommandHistory.add(this);
    }

    @Override
    public void undo() {
        for(ShapeObject shape : shapeListManager.getSelectedShapeListObject().getList()){
            if(draggedRight && draggedDown){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() - dimensions.getWidth(),
                        shape.getDimensions().getStartY() - dimensions.getHeight());
            }
            else if(draggedRight){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() - dimensions.getWidth(),
                        shape.getDimensions().getStartY() + dimensions.getHeight());
            }

            else if(draggedDown){
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() + dimensions.getWidth(),
                        shape.getDimensions().getStartY() - dimensions.getHeight());
            }

            else{
                shape.changeShapeStart(
                        shape.getDimensions().getStartX() + dimensions.getWidth(),
                        shape.getDimensions().getStartY() + dimensions.getHeight());
            }
        }
        shapeListManager.getShapeListObject().notifyObservers();    //clear and redraw everything after move
    }

    @Override
    public void redo() {
        moveShape();
    }
}
