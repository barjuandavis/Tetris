import javafx.scene.layout.Pane;

public class Board {
    Pane root;
    Shape activeShape,holdedShape;

    public Board() {
        root = new Pane();


    }



    public Pane getPane() {return root;}



}
