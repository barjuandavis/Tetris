import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import java.util.Random;

public class Board {
    private boolean retrieved;
    private boolean holding;
    private boolean hardDropping;
    private boolean dead;
    private Minos state[][];
    private double speed = 0;
    private double acceleration = 0.1;
    private int lineClear = 0;
    private int level = 1;
    private Line grids[][];
    private Line walls[];
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private final int BOTTOM_WALL = 0, LEFT_WALL = 1, RIGHT_WALL = 2;
    private final int lineClearObjective = 4;
    private final double baseSpeed = 1;

    //constructor
    public Board() {
        root = new Pane();
        holding = false;
        retrieved = false;
        hardDropping = false;
        dead = false;
        grids = new Line[Game.boardWidth_r-1][Game.boardHeight_r-1];
        walls = new Line[3];
        state = new Minos[Game.boardWidth_r+1][Game.boardHeight_r+1];
        double width = Game.boardWidth/ Game.boardWidth_r;
        double height = Game.boardHeight/ Game.boardHeight_r;
        //generating gridlines
        for (int i=1; i<=Game.boardWidth_r-1;i++){
            Line l = new Line(i*width,0,i*width,Game.boardHeight);
            l.setStroke(Color.valueOf("#BDAA2D"));
            root.getChildren().add(l);
            l.setOpacity(0.2);
        }
        for (int i=1; i<=Game.boardHeight_r-1;i++){
            Line l = new Line(0,i*height,(double)Game.boardWidth,i*height);
            l.setStroke(Color.valueOf("#BDAA2D"));
            root.getChildren().add(l);
            l.setOpacity(0.2);
        }
        //generating walls
        //WARNING: WALLS ARE NOT INTENDED TO BE DRAWN. They exist only for collision purposes.
        walls[LEFT_WALL] = new Line(-Minos.getMinosWidth(),0,-Minos.getMinosWidth(),Game.boardHeight);
        walls[RIGHT_WALL] = new Line(Game.boardWidth+Minos.getMinosWidth(),0,Game.boardWidth+Minos.getMinosWidth(),Game.boardHeight);
        walls[BOTTOM_WALL] = new Line(0,Game.boardHeight,Game.boardWidth,Game.boardHeight);
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
    private void checkCollision() {
        //TODO : check collisions only LEFT, RIGHT, AND DOWN. KEEP IN MIND THAT NOT EVERY COLLISIONS CAN STOP THE SHAPE.
        //check collision to walls
        //check collision to other minos
        for (Minos minos : activeShape.getMinosArray()) {
            if (minos.getBoundsInParent().intersects(walls[LEFT_WALL].getBoundsInParent())) {activeShape.moveRight(); }
            if (minos.getBoundsInParent().intersects(walls[RIGHT_WALL].getBoundsInParent())) {activeShape.moveLeft();}
            if (minos.getBoundsInParent().intersects(walls[BOTTOM_WALL].getBoundsInParent())) {
                speed = 0;
            }
        }
        checkMinosCollisions();

    }
    private void checkMinosCollisions() {

        for (Minos[] rows : state) {
            for (Minos which : rows) {
                if (which == null) continue;
                for (Minos minos : activeShape.getMinosArray()) {
                    int x = minos.getRelativeX(), y = minos.getRelativeY();
                    int xw = which.getRelativeX(), yw = which.getRelativeY();
                    if ((double)Math.sqrt((y-yw)*(y-yw)+(x-xw)*(x-xw)) <= 1.0 && yw > y) {
                        speed = 0;
                    }
                }
            }
        }
    }
    private void checkLines() {
        int validLineStart = -1;
        int validLineEnd = -1;
        for (Minos[] row : state) {
            boolean invalid = false;
            for (Minos m: row) {
                 if (m == null) {invalid = true; break;}
            }
        }
    }

     // PUBLIC UTILITY FUNCTIONS (Mostly user inputs)
    public void activate() {
        /*
        A function to retrieve a shape from nextShape
        and to give initial speed to activeShape.
         */
            activeShape = nextShape;
            shapeToBoard(activeShape);
            updateSpeed();
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
    public void startBoard() {
        randomizeShape();
        activate();
        AnimationTimer animate = new AnimationTimer() {
            int speed_f;
            @Override
            public void handle(long now) {
                /**
                 * 1 detik  = 60 frame (1 frame = 16.67 ms)
                 * v_awal   = 1 kotak/detik
                 * a        = 0.1 kotak/naik level
                 * Naik level = clear 5 lines, 6 lines, 7 lines, etc.
                 * speed 0 = mati, Shape yang sedang aktif berubah jadi inactive.
                 */

                if (speed > 0) {
                    //here's the gravity :)
                    speed_f = (int) (60/speed);
                    if (now % speed_f == 0) {activeShape.moveDown();}
                    checkCollision();
                } else {
                    //simpen di array dulu semua minosnya.
                    for (Minos m : activeShape.getMinosArray()) {
                        if (m.getRelativeY() == 0) {
                            //mati aja udah
                            this.stop();
                            dead = true;
                            System.out.println("Game Over :(");
                        } else state[m.getRelativeX()][m.getRelativeY()] = m;
                    }
                      if (!dead) {
                        randomizeShape();
                        activate();
                      }
                }
            }
        };
        animate.start();
    }

    public void left() {activeShape.moveLeft();}
    public void right() {activeShape.moveRight();}
    public void down() {activeShape.moveDown();}
    public void rotateLeft() {activeShape.rotateLeft();}
    public void rotateRight() {activeShape.rotateRight();}
    public void hardDrop() {
        hardDropping = true;
        speed = 60;
    }

    //GETTERS
    public Pane getPane() {
        return root;
    }
    public int getLineClearObjective() {return lineClearObjective+level;}
    public int getLevel() {return level;}
    public void updateSpeed() {
        hardDropping = false;
        speed = baseSpeed + level*acceleration;
    }

    public boolean isHardDropping() {return hardDropping;}


}


