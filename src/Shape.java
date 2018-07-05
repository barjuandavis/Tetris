.002import javafx.scene.paint.Paint;

public abstract class Shape {
    /*
        Shape consists of 4 Minos. (That's why it's called: Tetrominos.)
        There are seven different shape; L, Mirrored-L, Z, Mirrored-Z, Line, Square, and T-shape.
     */
    Minos[] minosArray;
    private int rotator;
    public Shape() {
        minosArray = new Minos[4];
        for (int i=0; i<4; i++) {
            minosArray[i] = new Minos(0,0);
        }
        initShape();
    }

    public abstract void initShape();
    /*shapes can be rotated, and every shapes' definition will be defined in their own classes.
    * There are THREE things that need to be defined:
    * a. Positions of every minos
    * b. The exact minos that is used for rotation point.
    * c. Color of every minos
    * */
    public int getRotator() {return rotator;}
    public void setRotator(int rotator) {this.rotator = rotator;}
    public void setColor(Paint color) {for (int i=0; i<4; i++) {minosArray[i].setFill(color);}
    }
}
