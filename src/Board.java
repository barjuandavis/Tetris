import javafx.scene.layout.Pane;
import java.util.Random;

public class Board {
    private Pane root;
    private Shape activeShape,holdedShape,nextShape;

    public Board() {
        root = new Pane();
    }

    private boolean shapeToBoard(Shape x) {
        /*
        a utility function to put a shape to the board in any position.
        returns false if another minos already occupied the designated position.
        */
        root.getChildren().addAll(x.getMinosArray());
        return true;
    }

    private void randomizeShape() {
        /*
        a function to randomize a shape and put it to nextShape.
        */
        Random r = new Random();
        int x = r.nextInt(7) + 1;
    }

    private Shape intToShape(int x) {
        switch (x) {

        }
        return new IShape();
    }

    public void setActiveShape(Shape activeShape) {
        this.activeShape = activeShape;
    }




    public Pane getPane() {return root;}



}
