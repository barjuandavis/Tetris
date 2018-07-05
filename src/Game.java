import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Game extends Application {
    public static final int boardHeight_r = 20; //Game Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Game Width counted per unit/Tetramino
    public static int boardHeight;
    public static int boardWidth;
    private Scene theScene;

    public static void main(String[] args) {
        launch(args);
    }

    public void initGame() {
        Pane root = new Pane(); //THIS is the board
        /// TODO : bikin kelas KHUSUS UNTUK BOARD
        theScene = new Scene(root, 400, 800, Paint.valueOf("rgb(1,100,1)"));
        boardHeight = (int)theScene.getHeight();
        boardWidth = (int)theScene.getWidth();
    }

    @Override
    public void start(Stage primaryStage) {
        initGame();
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
    //Getters

   //Setters

}
