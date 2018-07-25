package Shape;

import javafx.scene.paint.Paint;

public abstract class Shape {
    /*
        Shape.Shape consists of 4 Shape.Minos. (That's why it's called: Tetrominos.)
        There are seven different shape; L, Mirrored-L, Z, Mirrored-Z, Line, Square, and T-shape.
     */
    private Minos[] minosArray;
    private Minos rotator;
    public Shape() {
        minosArray = new Minos[4];
        for (int i=0; i<4; i++) {
            minosArray[i] = new Minos(2,0);
        }
        initShape();
    }

    public abstract void initShape();
    /*

    Shapes can be rotated, and every shapes' definition will be defined in their own classes.
    * There are THREE things that need to be defined:
    * a. Positions of every minos
    * b. The exact minos that is used for rotation point.
    * c. Color of every minos

    */
    //GETTERS
    public Minos[] getMinosArray() {return minosArray;}
    //SETTERS
    protected void setRotator(int rotator) {if (rotator != -1) this.rotator = minosArray[rotator];}
    public void setColor(Paint color) {for (int i=0; i<4; i++) {minosArray[i].setFill(color);}}
    //VARIOUS METHODS
    private void printRelPos() {

        for (Minos x : minosArray) {
            System.out.print("("+x.getRelativeX()+","+x.getRelativeY()+") ");
        }
        System.out.println();

    }
    public void move(int x,int y) {
        for (int i=0; i<4; i++) {minosArray[i].move(x,y);}
      //  printRelPos();
    }
    public void moveLeft() {move(-1,0);}
    public void moveRight() {move(1,0);}
    public void moveDown() {move(0,1);}
    public void rotateLeft(){
        if (rotator != null)
            for (Minos m : minosArray) {
                if (m != rotator) {
                    /** a. translate ke origin */
                    int xR = m.getRelativeX() - rotator.getRelativeX();
                    int yR = m.getRelativeY() - rotator.getRelativeY();
                    /**
                     * b. rotate DENGAN rotation matrix
                     * | 0  1 |   | xR |   | yR|
                     * |      | . |    | = |    |
                     * |-1  0 |   | yR |   | -xR |
                     */
                    int _x = yR, _y = -xR;
                    /**
                     * c. balikin lagi ke pivot
                     */
                    m.setRelativeX(_x + rotator.getRelativeX());
                    m.setRelativeY(_y + rotator.getRelativeY());;
                }
            }
        } //rotation movement
    public void rotateRight() {
        if (rotator != null)
            for (Minos m : minosArray) {
                if (m != rotator) {
                    /** a. translate ke origin */
                    int xR = m.getRelativeX() - rotator.getRelativeX();
                    int yR = m.getRelativeY() - rotator.getRelativeY();
                    /**
                     * b. rotate DENGAN rotation matrix
                     * | 0 -1 |   | xR |   | -yR|
                     * |      | . |    | = |    |
                     * |1   0 |   | yR |   | xR |
                     */
                    int _x = -yR, _y = xR;
                    /**
                     * c. balikin lagi ke pivot
                     */
                    m.setRelativeX(_x + rotator.getRelativeX());
                    m.setRelativeY(_y + rotator.getRelativeY());
                }
            }
       // System.out.print("RR : ");
      //  printRelPos();
    } // rotation movement

}

