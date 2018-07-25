package Main;

import Shape.*;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    private boolean retrieved;
    private boolean holding;
    private boolean hardDropping;

    private SimpleBooleanProperty dead;
    private Minos state[][];
    private double speed;
    private SimpleIntegerProperty lineClear;
    private SimpleIntegerProperty level;
    private SimpleIntegerProperty score;
    private Line walls[];
    private Pane root;
    private Shape activeShape, holdedShape, nextShape;
    private final int BOTTOM_WALL = 0, LEFT_WALL = 1, RIGHT_WALL = 2;
    private int lastPos[][];
    private ArrayList<Shape> randomizer;



    //constructor
    public Board() {
        root = new Pane();
        holding = false;
        hardDropping = false;
        dead = new SimpleBooleanProperty(false);
        lineClear = new SimpleIntegerProperty(0);
        level = new SimpleIntegerProperty(1);
        score = new SimpleIntegerProperty(0);
        walls = new Line[3];
        state = new Minos[Game.boardWidth_r][Game.boardHeight_r];
        speed = 1;
        double width = Game.boardWidth/ Game.boardWidth_r;
        double height = Game.boardHeight/ Game.boardHeight_r;

        //generating gridlines
        for (int i=1; i<=Game.boardWidth_r-1;i++){
            Line l = new Line(i*width,0,i*width,Game.boardHeight);
            l.setStroke(Color.valueOf("#BDAA2D"));
            l.setStroke(Color.WHITESMOKE);
            root.getChildren().add(l);
            l.setOpacity(0.5);
        }
        for (int i=1; i<=Game.boardHeight_r-1;i++){
            Line l = new Line(0,i*height,(double)Game.boardWidth,i*height);
            l.setStroke(Color.valueOf("#BDAA2D"));
            l.setStroke(Color.WHITESMOKE);
            root.getChildren().add(l);
            l.setOpacity(0.5);
        }
        root.setStyle("-fx-background-color: #4E4B2A;");
        //generating walls
        //WARNING: WALLS ARE NOT INTENDED TO BE DRAWN. They exist only for collision purposes.
        walls[LEFT_WALL] = new Line(-Minos.getMinosWidth(),0,-Minos.getMinosWidth(),Game.boardHeight);
        walls[RIGHT_WALL] = new Line(Game.boardWidth+ Minos.getMinosWidth(),0,Game.boardWidth+ Minos.getMinosWidth(),Game.boardHeight);
        walls[BOTTOM_WALL] = new Line(0,Game.boardHeight,Game.boardWidth,Game.boardHeight);
    }

    //ANIMATOR THREAD
    public void startBoard() {
        randomizeShape();
        activate();
        AnimationTimer animate = new AnimationTimer() {
            int speed_f;
            int frameCount = 0;
            @Override
            public void handle(long now) {
                frameCount++;
                //1 frame ~ 16.67 ms = 169
                if (speed > 0) {
                    speed_f = 60000/(int)(speed*1000); //in FRAMES
                    if (frameCount % (speed_f) == 0) {down();frameCount = 0;}
                    checkCollision();
                } else {
                    //simpen di array dulu semua minosnya.
                    for (Minos m : activeShape.getMinosArray()) {
                        if (m.getRelativeY() == 0) {
                            this.stop();
                            setDead(true);
                        }
                        else state[m.getRelativeX()][m.getRelativeY()] = m;
                    }
                    if (!isDead()) {
                        ArrayList<Integer> a = checkForValidLines();
                        cleanLines(a);
                        activeShape = null;
                        increaseLevel();
                        randomizeShape();
                        activate();
                    }
                }
            }
        };
        animate.start();
    }

    //PRIVATE UTILITY FUNCTIONS
    private void randomizeShape () {
        /*
        a function to randomize a shape and put it to nextShape.
        */
        if (randomizer == null || randomizer.size() == 0) {
            randomizer = new ArrayList<Shape>();
            for (int i = 1; i<=7; i++) {
                randomizer.add(intToShape(i));
            }
        }
            Random r = new Random();
            int x = r.nextInt(randomizer.size());
            nextShape = randomizer.get(x);
            randomizer.remove(x);

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
                    } else if (d <= 0.0) {
                        for (int j=0; j<arr.length; j++) {
                            activeShape.getMinosArray()[j].set(lastPos[j][0],lastPos[j][1]);
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
    private void cleanLines(ArrayList<Integer> arrx) {
        Integer[] arr = arrx.toArray(new Integer[0]);
        for (Integer i : arr) {
            for (int j = 0; j<Game.boardWidth_r; j++) {
                if (state[j][i] != null) {
                    root.getChildren().remove(state[j][i]);
                    state[j][i] = null;
                }
            }
            increaseLineClear();
        }
        addScore(arr.length);
        for (int i = 0; i<arr.length; i++) {
            int y_target = arr[i];
            for (int y = y_target-1; y>=0; y--) {
                //shift down ALL ELEMENTS from i-1 to 0;
                for (int x = 0; x<Game.boardWidth_r; x++) {
                       if (state[x][y] != null) {
                           state[x][y].move(0,1);
                           state[x][y+1] = state[x][y];
                           state[x][y] = null;
                       }

                }
            }
            for (int j = arr.length-1; j>i; j--) {
                arr[j]++; //increment line clears before shifted down
            }
        }
    }
    private void updateLastPos(int x, int y, int i) {lastPos[i][0] = x; lastPos[i][1] = y;}
    private void updateLastPos() {
        Minos[] arr = activeShape.getMinosArray();
        for (int i = 0; i<arr.length; i++) {
            updateLastPos(arr[i].getRelativeX(),arr[i].getRelativeY(),i);
          //  System.out.print("("+arr[i].getRelativeX()+","+arr[i].getRelativeY()+") ");
        }
        //System.out.println();
    }
    private void increaseLineClear() {lineClear.setValue(lineClear.getValue()+1);}
    private void increaseLevel() {
        if (getLineClear() >= getLineClearObjective()) {
            setLevel(getLevel()+1);
            setLineClear(0);
        }
    }
    private void addScore(int combo) {
        int add;
        switch (combo) {
            case 1 : add = 100; break;
            case 2 : add = 300 ;break;
            case 3 : add = 600; break;
            case 4 : add = 1000; break;
            default : add = 0; break;
        }
        score.setValue(getScore()+add);
    }
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
    private void printPlayerStats() {
        System.out.println("--------------------------------------");
        System.out.println("CLEAR " + getLineClearObjective() + " LINES");
        System.out.println("Score : " + getScore());
        System.out.println("LineClear : " + getLineClear());
        System.out.println("Level : " + getLevel());
        System.out.println("--------------------------------------");
        System.out.println();
}

     // PUBLIC UTILITY FUNCTIONS (Mostly user inputs)
    public void activate() {
        /*
        A function to retrieve a shape from nextShape
        and to give initial speed to activeShape.
         */
            lastPos = new int[4][2];
            activeShape = nextShape;
            shapeToBoard(activeShape);
            updateSpeed();
    }
    public void hold() {
       if (!holding)
        if (holdedShape == null) {
            holdedShape = activeShape;
            holding = true;
        } else {
            retrieve();
        }
    }
    public void retrieve() {
        if (!retrieved) {
            activeShape = holdedShape;
            retrieved = true;
        }
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
        return lineClearObjective+level.getValue();
    }
    public SimpleIntegerProperty levelProperty() {return level;}
    public int getLevel() {return level.getValue();}
    public SimpleIntegerProperty scoreProperty() {return score;}
    public int getScore() {return score.getValue();}
    public SimpleIntegerProperty lineClearProperty() {return lineClear;}
    public int getLineClear() {return lineClear.getValue();}
    public void updateSpeed() {
        double acc = 0.1;
        hardDropping = false;
        int baseSpeed = 1;
        speed = baseSpeed + (getLevel()-1)*acc;
    }

    public boolean isHardDropping() {return hardDropping;}
    public boolean isDead() {return dead.get();}

    public SimpleBooleanProperty deadProperty() {return dead;}




    //SETTERS
    public void setLineClear(int lineClear) {this.lineClear.set(lineClear);}
    public void setLevel(int level) {this.level.set(level);}
    public void setDead(boolean dead) {this.dead.set(dead);}





}


