import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Game extends Application {
    public static final int boardHeight_r = 20; //Game Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Game Width counted per unit/Tetramino
    public static final int boardHeight = 800;
    public static final int boardWidth = 400;
    private Scene theScene;
    private Board board;
    public static void main(String[] args) {
        launch(args);
    }
    //user input configurated here



    public void initGame() {
        board = new Board();
        theScene = new Scene(board.getPane(), boardWidth, boardHeight, Paint.valueOf("rgb(255,255,255)"));
        //User input here
        //TODO FEATURES:
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, (k) -> {
            if (k.getCode() == KeyCode.LEFT) {board.left();}
            if (k.getCode() == KeyCode.RIGHT) {board.right();}
            if (k.getCode() == KeyCode.DOWN) {board.down();}
            if (k.getCode() == KeyCode.CONTROL) {board.rotateLeft();}
            if (k.getCode() == KeyCode.UP) {board.rotateRight();}
        });

    }

    public void mainThread() {


    }

    @Override
    public void start(Stage primaryStage) {
        initGame();
        mainThread();
        primaryStage.setScene(theScene);
        primaryStage.show();
    }
    //Getters



   //Setters

}
