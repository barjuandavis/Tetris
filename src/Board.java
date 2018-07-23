import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
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
    private int score = 0;
    private Line grids[][];
    private Line walls[];
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private final int BOTTOM_WALL = 0, LEFT_WALL = 1, RIGHT_WALL = 2;
    private int lastPos[][];

    //constructor
    public Board() {
        root = new Pane();
        holding = false;
        retrieved = false;
        hardDropping = false;
        dead = false;
        grids = new Line[Game.boardWidth_r-1][Game.boardHeight_r-1];
        walls = new Line[3];
        state = new Minos[Game.boardWidth_r][Game.boardHeight_r];
        double width = Game.boardWidth/ Game.boardWidth_r;
        double height = Game.boardHeight/ Game.boardHeight_r;
        //generating gridlines
        for (int i=1; i<=Game.boardWidth_r-1;i++){
            Line l = new Line(i*width,0,i*width,Game.boardHeight);
            l.setStroke(Color.valueOf("#BDAA2D"));
            l.setStroke(Color.BLACK);
            root.getChildren().add(l);
            //l.setOpacity(0.2);
        }
        for (int i=1; i<=Game.boardHeight_r-1;i++){
            Line l = new Line(0,i*height,(double)Game.boardWidth,i*height);
            l.setStroke(Color.valueOf("#BDAA2D"));
            l.setStroke(Color.BLACK);
            root.getChildren().add(l);
            //l.setOpacity(0.2);
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
                Minos[] arr = activeShape.getMinosArray();
                for (int i = 0; i<arr.length; i++) {
                    int x = arr[i].getRelativeX(), y = arr[i].getRelativeY();
                    int xw = which.getRelativeX(), yw = which.getRelativeY();
                    double d = Math.sqrt((y-yw)*(y-yw)+(x-xw)*(x-xw));
                    if (d == 1.0) {
                        if (yw > y) speed = 0;
                    } else if (d == 0.0) {
                        //return to prevPos
                        for (int j=0; j<arr.length; j++) {
                            activeShape.getMinosArray()[j].move(lastPos[j][0],lastPos[j][1]);
                        }

                    }
                }
            }
        }
    }
    private ArrayList<Integer> checkForValidLines() {
        ArrayList<Integer> a = new ArrayList<>();
        for (int y=Game.boardHeight_r-1; y>=0; y--) {
            boolean invalid = false;
            for (int x=0; x<=Game.boardWidth_r-1; x++) {if (state[x][y] == null) {invalid = true; break;}}
            if (!invalid) {
                a.add(y);
            }
        }
        return a;
    }
    private void cleanLines(ArrayList<Integer> arr) {
        for (Integer i : arr) {
            for (int j = 0; j<Game.boardWidth_r; j++) {
                if (state[j][i] != null) {
                    root.getChildren().remove(state[j][i]);
                    state[j][i] = null;
                }
                //pindahin dari state[j][i-1] ke state[j][0] satu line ke bawah
            }
            for (int y = i-1; y>=0; y--) {
                for (int x = 0; x<Game.boardWidth_r; x++) {
                    if (state[x][y] != null){
                        state[x][y].move(0,1);
                        state[x][y + 1] = state[x][y];
                        state[x][y] = null;

                    }
                }
            }
            increaseLineClear();
        }
    }
    private void updateLastPos(int x, int y, int i) {lastPos[i][0] = x; lastPos[i][1] = y;}
    private void updateLastPos() {
        Minos[] arr = activeShape.getMinosArray();
        for (int i = 0; i<arr.length; i++) {
            updateLastPos(arr[i].getRelativeX(),arr[i].getRelativeY(),i);
        }
    }
    private void increaseLineClear() {lineClear++;}
    private void increaseLevel() {level++;}
    private void setScore(int add) {score+=add;}
    private void printState() {
        for (int i=0; i<Game.boardHeight_r; i++) {
            for (int j=0; j<Game.boardWidth_r; j++) {
                if (state[j][i] != null) System.out.print("1");
                else System.out.print("0");
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();

    }

     // PUBLIC UTILITY FUNCTIONS (Mostly user inputs)
    public void activate() {
        /*
        A function to retrieve a shape from nextShape
        and to give initial speed to activeShape.
         */
            if (activeShape != null) updateLastPos();
            else lastPos = new int[4][2];
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

                long now_s = now/(int)Math.pow(10,9);
                if (speed > 0) {
                    //here's the gravity :)
                    speed_f = (int) (60/speed);
                    if (now % (speed_f) == 0) {activeShape.moveDown();}
                    checkCollision();
                } else {
                    //simpen di array dulu semua minosnya.
                    for (Minos m : activeShape.getMinosArray()) {
                        if (m.getRelativeY() == 0) {
                            this.stop();
                            dead = true;
                        } else state[m.getRelativeX()][m.getRelativeY()] = m;
                    }
                      if (!dead) {
                        printState();
                        ArrayList<Integer> a = checkForValidLines();
                        if (a.size() > 0) {cleanLines(a);for( int i : a ){System.out.print(i+" ");}System.out.println();}
                        activeShape = null;
                        randomizeShape();
                        activate();
                      } else System.out.println("Game Over :(");
                }
            }
        };
        animate.start();
    }

    public void left() { updateLastPos();activeShape.moveLeft();}
    public void right() {updateLastPos();activeShape.moveRight();}
    public void down() {updateLastPos();activeShape.moveDown();}
    public void rotateLeft() {updateLastPos();activeShape.rotateLeft();}
    public void rotateRight() {updateLastPos();activeShape.rotateRight();}
    public void hardDrop() {
        updateLastPos();
        hardDropping = true;
        speed = 60;
    }

    //GETTERS
    public Pane getPane() {
        return root;
    }
    public int getLineClearObjective() {
        int lineClearObjective = 4;
        return lineClearObjective+level;
    }
    public int getLevel() {return level;}
    public int getScore() {return score;}
    public int getLineClear() {return lineClear;}
    public void updateSpeed() {
        hardDropping = false;
        int baseSpeed = 1;
        speed = baseSpeed + (level-1)*acceleration;
    }

    public boolean isHardDropping() {return hardDropping;}
    public boolean isDead() {return dead;}



}


