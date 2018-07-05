import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Minos extends Rectangle {
    private final int size = 1;
    public Minos(int x, int y) {
        super(Game.boardWidth/ Game.boardWidth_r, Game.boardHeight/ Game.boardHeight_r, Paint.valueOf("red"));
        move(x,y);
    }
    public void move(int x, int y) {setRelativeX(x);setRelativeY(y);}
    public void setRelativeX(int x) {setX((double)x*getWidth());}
    public void setRelativeY(int y) {setX((double)y*getHeight());}
    public int getRelativeX() {return (int)(getX()/(getWidth()));}
    public int getRelativeY() {return (int)(getY()/(getHeight()));}
}
