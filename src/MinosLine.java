/**
 @project Tetris

 @author Barjuan Davis Penthalion    00000023971     BP3971@student.uph.edu
 @author Nadya Felim Bachtiar        00000019602     NB9602@student.uph.edu

 Informatics 2016
 Universitas Pelita Harapan
 */
import Main.Game;
import Shape.Minos;
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
