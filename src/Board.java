import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.Random;

public class Board {
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private boolean retrieved;
    private int dropspeed = 1;
    private Line grids[][];




    public Board() {
        root = new Pane();
        retrieved = false;
        grids = new Line[Game.boardWidth_r-1][Game.boardHeight_r-1];
        double width = Game.boardWidth/ Game.boardWidth_r;
        double height = Game.boardHeight/ Game.boardHeight_r;
        //generating gridlines
        for (int i=1; i<=Game.boardWidth_r-1;i++){
            Line l = new Line(i*width,0,i*width,Game.boardHeight);l.setFill(Color.GRAY);root.getChildren().add(l);l.setOpacity(0.2);
        }
        for (int i=1; i<=Game.boardHeight_r-1;i++){
            Line l = new Line(0,i*height,Game.boardWidth,i*height);l.setFill(Color.GRAY);root.getChildren().add(l);l.setOpacity(0.2);
        }
        startBoard();
    }

    //PRIVATE UTILITY FUNCTIONS
    private void randomizeShape () {
        /*
        a function to randomize a shape and put it to nextShape.
        */
        Random r = new Random();
        int x = r.nextInt(7) + 1;
        nextShape = intToShape(x);

    }
    private void shapeToBoard(Shape a, int x, int y) {
            /*
            a utility function to put a shape to the board in any position.
            returns false if another minos already occupied the designated position.
            */
        a.move(x,y);
        root.getChildren().addAll(a.getMinosArray());
    }
    private void shapeToBoard(Shape a) {shapeToBoard(a,0,0);}
    private Shape intToShape (int x){
            if (x == 1) return new IShape();
            else if (x == 2) return new JShape();
            else if (x == 3) return new LShape();
            else if (x == 4) return new OShape();
            else if (x == 5) return new SShape();
            else if (x == 6) return new TShape();
            else return new ZShape();
        }
    private void isActiveShapeCollided() {
        //TODO : check collisions only LEFT, RIGHT, AND DOWN. KEEP IN MIND THAT NOT EVERY COLLISIONS CAN STOP THE SHAPE.
        //check collision to walls
        //check collision to other minos
        for (Minos minos : activeShape.getMinosArray()) {

        }

    }
    private void startBoard() {
        randomizeShape();
        activate();
    }


     // PUBLIC UTILITY FUNCTIONS (Mostly user inputs)
    public void activate() {
        /*
        A function to retrieve a shape from nextShape when activeShape is null.
        TODO: When a shape is COLLIDED AND STOPPED, GIVE A CERTAIN DELAY, NULLIFY activeShape, and reactivate it.
         */
        if (activeShape == null) {
            activeShape = nextShape;
            shapeToBoard(activeShape);
        }
    }
    public void hold() {
        if (!retrieved || holdedShape == null) {
            holdedShape = activeShape;
            retrieved = false;
        }
    }
    public void retrieve() {
        if (!retrieved) {
            activeShape = holdedShape;
            retrieved = true;
        }
    }

    public void left() {activeShape.moveLeft();}
    public void right() {activeShape.moveRight();}
    public void down() {activeShape.moveDown();}
    public void rotateLeft() {activeShape.rotateLeft();}
    public void rotateRight() {activeShape.rotateRight();}





    //GETTERS
    public Pane getPane() {
        return root;
    }

}


