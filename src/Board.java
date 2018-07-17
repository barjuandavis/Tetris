import javafx.scene.layout.Pane;
import java.util.Random;

public class Board {
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private boolean retrieved;

    public Board() {
        root = new Pane();
        retrieved = false;
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
        private Shape intToShape (int x){
            if (x == 1) return new IShape();
            else if (x == 2) return new JShape();
            else if (x == 3) return new LShape();
            else if (x == 4) return new OShape();
            else if (x == 5) return new SShape();
            else if (x == 6) return new TShape();
            else return new ZShape();
        }
        private void shapeToBoard(Shape a, int x, int y) {
            /*
            a utility function to put a shape to the board in any position.
            returns false if another minos already occupied the designated position.
            */
            a.move(x,y);

            root.getChildren().addAll(a.getMinosArray());
        }

     // PUBLIC UTILITY FUNCTIONS (Mostly user inputs)
    public void activate() {
        /*
        A function to retrieve a shape from nextShape when activeShape is null.
        TODO: When a shape is COLLIDED AND STOPPED, GIVE A CERTAIN DELAY, NULLIFY activeShape, and reactivate it.
         */
        if (activeShape == null) {
            activeShape = nextShape;
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
    public void left() {activeShape.move(-1,0);}
    public void right() {activeShape.move(1,0);}
    public void () {


    }



    //GETTERS
    public Pane getPane() {
        return root;
    }

}


