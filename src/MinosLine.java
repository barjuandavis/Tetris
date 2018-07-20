import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

public class MinosLine {
    private ArrayList<Minos> line;
    private Rectangle hitboxes[];
    public MinosLine() {
        line.ensureCapacity(Game.boardWidth_r);
        hitboxes = new Rectangle[Game.boardWidth_r];
        for (Rectangle i : hitboxes) {i = new Rectangle(Minos.getMinosWidth(),Minos.getMinosHeight());}
    }

    public void entryCheck(Minos m) {
        for (Rectangle i : hitboxes) {
            if (i.getBoundsInParent().intersects(m.getBoundsInParent())) {


            }
        }
    }
}
