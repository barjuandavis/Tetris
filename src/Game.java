import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Game extends Application {
    public static final int boardHeight_r = 20; //Game Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Game Width counted per unit/Tetramino
    public static int boardHeight = 800;
    public static int boardWidth = 400;
    private Scene theScene;

    public static void main(String[] args) {
        launch(args);
    }
        Board board;
    public void initGame() {
        /// TODO : bikin kelas KHUSUS UNTUK BOARD
        board = new Board();
        theScene = new Scene(board.getPane(), boardWidth, boardHeight, Paint.valueOf("rgb(255,255,255)"));
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
