import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Minos extends Rectangle {
    private final int size = 1;
    public Minos(int x, int y) {
        super(Game.boardWidth/ Game.boardWidth_r, Game.boardHeight/ Game.boardHeight_r, Paint.valueOf("red"));
        move(x,y);
    }
    public void move(int x, int y) {setRelativeX(x+getRelativeX());setRelativeY(y+getRelativeY());}
    public void setRelativeX(int x) {setX(x*getWidth());}
    public void setRelativeY(int y) {setY(y*getHeight());}
    public int getRelativeX() {return (int)(getX()/(getWidth()));}
    public int getRelativeY() {return (int)(getY()/(getHeight()));}
}
