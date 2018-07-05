import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Minos extends Rectangle {
    private final int size = 1;
    public Minos(int x, int y) {
        super(Board.boardWidth/Board.boardWidth_r, Board.boardHeight/Board.boardHeight_r, Paint.valueOf("red"));
        move(x,y);
    }
    public void move(int x, int y) {
        setX((double)x*getWidth());
        setY((double)y*getHeight());
    }
}
