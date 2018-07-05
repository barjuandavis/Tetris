import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Board extends Application {
    public static final int boardHeight_r = 20; //Board Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Board Width counted per unit/Tetramino
    public static int boardHeight;
    public static int boardWidth;
    private Scene theScene;
    private GridPane mainBoard;

    public static void main(String[] args) {
        launch(args);
    }

    public void initBoard() {
        Pane root = new Pane();

        theScene = new Scene(root, 400, 800, Paint.valueOf("rgb(1,100,1)"));
        boardHeight = (int)theScene.getHeight();
        boardWidth = (int)theScene.getWidth();
        Minos t = new Minos(0,1);
        root.getChildren().add(t);


    }


    @Override
    public void start(Stage primaryStage) {
        initBoard();
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
    //Getters

   //Setters

}
