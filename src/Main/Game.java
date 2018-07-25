package Main;

import GUI.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;

public class Game extends Application {
    public static final int boardHeight_r = 20; //Main.Game Height counted per unit/Tetramino
    public static final int boardWidth_r = 10; // Main.Game Width counted per unit/Tetramino
    public static final int boardHeight = 600;
    public static final int boardWidth = 300;
    private Scene theScene;
    private Board board;
    private GameController gc;
    public static void main(String[] args) {
        launch(args);
    }

    public void initGame() {

        gc = new GameController();
        theScene = new Scene(gc, 800, 600);
        board = gc.getBoard();
        theScene.addEventHandler(KeyEvent.KEY_PRESSED, (k) -> {
            if (!board.isDead()) {
                if (!board.isHardDropping()) { //user inputs are DISABLED during hardDrop.
                    if (k.getCode() == KeyCode.LEFT) {board.left();}
                    if (k.getCode() == KeyCode.RIGHT) {board.right();}
                    if (k.getCode() == KeyCode.DOWN) {board.down();}
                    if (k.getCode() == KeyCode.CONTROL) {board.rotateLeft();}
                    if (k.getCode() == KeyCode.UP) {board.rotateRight();}
                }
                if (k.getCode() == KeyCode.SPACE) {
                    board.hardDrop();
                }
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
        String musicFile = "src/GUI/assets/Tetris.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        initGame();
        mainThread();
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.show();
    }
    //Getters



   //Setters

}
