import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;

public class Game extends Application {
    public static final int boardHeight_r = 20; //Game Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Game Width counted per unit/Tetramino
    public static final int boardHeight = 800;
    public static final int boardWidth = 400;
    private Scene theScene;
    private Board board;
    private GameController gc;
    public static void main(String[] args) {
        launch(args);
    }
    //user input configurated here



    public void initGame() {
        //board = new Board();
        gc = new GameController();
        //gc.setBoard(board);
        theScene = new Scene(gc);
        //theScene = new Scene(board.getPane(), boardWidth, boardHeight, Paint.valueOf("#0F0F0F"));
        //User input here
        //TODO FEATURES:
        board = gc.getBoard();
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, (k) -> {
            if (!board.isHardDropping()) { //user inputs are DISABLED during hardDrop.
                if (k.getCode() == KeyCode.LEFT) {board.left();}
                if (k.getCode() == KeyCode.RIGHT) {board.right();}
                if (k.getCode() == KeyCode.DOWN) {board.down();}
                if (k.getCode() == KeyCode.CONTROL) {board.rotateLeft(); }
                if (k.getCode() == KeyCode.UP) {board.rotateRight();}
            }
            if (k.getCode() == KeyCode.SPACE) {board.hardDrop();}
            if (k.getCode() == KeyCode.ESCAPE) {System.exit(0);}
        });

    }

    public void mainThread() {
        //board.startBoard();
        startMusic();
    }

    public void startMusic() {
        String musicFile = "src/Tetris.mp3";

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
    }

    @Override
    public void start(Stage primaryStage) {
        initGame();
        mainThread();
        primaryStage.setScene(theScene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
     //   primaryStage.setResizable(false);
        primaryStage.show();
    }
    //Getters



   //Setters

}
