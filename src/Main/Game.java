package Main;

import GUI.GameController;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
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
    String musicFile;
    Media sound;
    MediaPlayer mediaPlayer;
    public static void main(String[] args) {
        launch(args);
    }

    public void initGame() {
        gc = new GameController();
        theScene = new Scene(gc);
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
        musicFile = "src/GUI/assets/Tetris.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.play();
        mediaPlayer.setStartTime(Duration.ZERO);
        mediaPlayer.setStopTime(Duration.seconds(77));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void stopMusic() {
        mediaPlayer.stop();
        musicFile = "src/GUI/assets/Game Over.mp3";
        sound = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    @Override
    public void start(Stage primaryStage) {
        initGame();
        mainThread();
        primaryStage.setScene(theScene);
        primaryStage.setResizable(false);
        primaryStage.setFullScreenExitHint("");

        Rectangle2D screenBound = Screen.getPrimary().getVisualBounds();

        primaryStage.setX((screenBound.getMinX()+screenBound.getMaxX())/2-(screenBound.getWidth()*80/100)/2);
        primaryStage.setY((screenBound.getMinY()+screenBound.getMaxY())/2-(screenBound.getHeight()*80/100)/2-50);
        primaryStage.setWidth(screenBound.getWidth()*80/100);
        primaryStage.setHeight(screenBound.getHeight()*80/100);
        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(1100);

        primaryStage.show();

        board.deadProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(board.isDead()) stopMusic();
            }
        });
    }
    //Getters



   //Setters

}
