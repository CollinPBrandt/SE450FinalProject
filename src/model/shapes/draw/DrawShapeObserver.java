package model.shapes.draw;

import model.interfaces.IDraw;
import model.interfaces.IShapeObserver;
import controller.lists.ShapeList;
import model.shapes.data.ShapeObject;
import view.gui.PaintCanvas;

import java.awt.*;

public class DrawShapeObserver implements IShapeObserver {

    private ShapeList shapeList;
    private Graphics g;
    private PaintCanvas canvas;

    public DrawShapeObserver(ShapeList shapeList, PaintCanvas canvas){
        this.shapeList = shapeList;
        this.g = canvas.getGraphics2D();
        this.canvas = canvas;
        this.shapeList.registerObserver(this);
    }

    private void clearCanvas(){
        Graphics whiteRecForClearing = canvas.getGraphics2D();  //draw white rectangle over canvas to clear
        whiteRecForClearing.setColor(Color.white);
        whiteRecForClearing.fillRect(0,0, 1200, 800);
    }

    @Override
    public void update() {
        clearCanvas();
        IDraw drawStrategy;
        for(ShapeObject shape : shapeList.getList()){
            switch (shape.getShapeType()) {
                case ELLIPSE:
                    drawStrategy = new EllipseDraw(shape, canvas, g);
                    break;
                case RECTANGLE:
                    drawStrategy = new RectangleDraw(shape, canvas, g);
                    break;
                case TRIANGLE:
                    drawStrategy = new TriangleDraw(shape, canvas, g);
                    break;
                default:
                    drawStrategy = new EllipseDraw(shape, canvas, g);   //default is to draw Ellipse
                    break;
            }
            drawStrategy.paint(g);
        }
    }
}
