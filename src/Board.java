import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Random;

public class Board {
    private boolean retrieved;
    private boolean state[][];
    private int speed = 1;
    private int lineClearObjective = 5;
    private Line grids[][];
    private Line walls[];
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private final int BOTTOM_WALL = 0, LEFT_WALL = 1, RIGHT_WALL = 2;

    public Board() {
        root = new Pane();
        retrieved = false;
        grids = new Line[Game.boardWidth_r-1][Game.boardHeight_r-1];
        walls = new Line[3];
        double width = Game.boardWidth/ Game.boardWidth_r;
        double height = Game.boardHeight/ Game.boardHeight_r;
        //generating gridlines
        for (int i=1; i<=Game.boardWidth_r-1;i++){
            Line l = new Line(i*width,0,i*width,Game.boardHeight);
            l.setFill(Color.GRAY);root.getChildren().add(l);
            l.setOpacity(0.2);
        }
        for (int i=1; i<=Game.boardHeight_r-1;i++){
            Line l = new Line(0,i*height,(double)Game.boardWidth,i*height);
            l.setFill(Color.GRAY);
            root.getChildren().add(l);
            l.setOpacity(0.2);
        }
        //generating walls
        //WARNING: WALLS ARE NOT INTENDED TO BE DRAWN. They exist only for collision purposes.
        walls[LEFT_WALL] = new Line(0,0,0,Game.boardHeight);
        walls[RIGHT_WALL] = new Line(Game.boardWidth,0,Game.boardWidth,Game.boardHeight);
        walls[BOTTOM_WALL] = new Line(0,Game.boardHeight,Game.boardWidth,Game.boardHeight);
    }

    //PRIVATE UTILITY FUNCTIONS
    public void randomizeShape () {
        /*
        a function to randomize a shape and put it to nextShape.
        */
        Random r = new Random();
        int x = r.nextInt(7) + 1;
        nextShape = intToShape(x);

    }
    public void shapeToBoard(Shape a, int x, int y) {
            /*
            a utility function to put a shape to the board in any position.
            returns false if another minos already occupied the designated position.
            */
        a.move(x,y);
        root.getChildren().addAll(a.getMinosArray());
    }
    public void shapeToBoard(Shape a) {shapeToBoard(a,0,0);}
    public Shape intToShape (int x){
            if (x == 1) return new IShape();
            else if (x == 2) return new JShape();
            else if (x == 3) return new LShape();
            else if (x == 4) return new OShape();
            else if (x == 5) return new SShape();
            else if (x == 6) return new TShape();
            else return new ZShape();
        }
    public void isCollided() {
        //TODO : check collisions only LEFT, RIGHT, AND DOWN. KEEP IN MIND THAT NOT EVERY COLLISIONS CAN STOP THE SHAPE.
        //check collision to walls
        //check collision to other minos
        for (Minos minos : activeShape.getMinosArray()) {
            if (minos.getBoundsInParent().intersects(walls[LEFT_WALL].getBoundsInParent())) {activeShape.moveRight();}
            if (minos.getBoundsInParent().intersects(walls[RIGHT_WALL].getBoundsInParent())) {activeShape.moveLeft();}
            if (minos.getBoundsInParent().intersects(walls[BOTTOM_WALL].getBoundsInParent())) {speed = 0;}
        }
    }


    public void startBoard() {
        // TODO: PERINGATAN KERAS, JALANKAN METHOD INI DI GAME THREAD!!
        randomizeShape();
        activate();
        AnimationTimer animate = new AnimationTimer() {
            @Override
            public void handle(long now) {
                /**
                 * 1 detik  = 60 frame
                 * v_awal   = 1 kotak/detik
                 * a        = 0.1 kotak/naik level
                 * Naik level = clear 5 lines, 6 lines, 7 lines, etc.
                 * speed 0 = mati, Shape yang sedang aktif berubah jadi inactive.
                 */



            }
        };
        animate.start();
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


