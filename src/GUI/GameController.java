/**
    @project Tetris

    @author Barjuan Davis Penthalion    00000023971     BP3971@student.uph.edu
    @author Nadya Felim Bachtiar        00000019602     NB9602@student.uph.edu

    Informatics 2016
    Universitas Pelita Harapan
 */
package GUI;

import Main.Board;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends BorderPane implements Initializable {
    @FXML
    private Text score;

    @FXML
    private Text level;

    @FXML
    private Text line;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox centerBox;

    @FXML
    private Rectangle topBox;

    @FXML
    private ImageView logo;

    private Board board;
    private Alert over = new Alert(Alert.AlertType.INFORMATION);

    public GameController () {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {
        setImages();
        board = new Board();
        centerBox.getChildren().add(board.getPane());
        centerBox.setMaxWidth(300);
        centerBox.setMaxHeight(650);

        score.setText(""+board.getScore());
        level.setText(""+board.getLevel());
        line.setText(""+board.getLineClear());

        score.textProperty().bind(board.scoreProperty().asString());
        level.textProperty().bind(board.levelProperty().asString());
        line.textProperty().bind(board.totalLineProperty().asString());

        board.startBoard();

        board.deadProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (board.isDead()) {
                    callAlert();
                    over.show();
                    over.setOnCloseRequest(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent event) {
                            System.exit(0);
                        }
                    });
                }
            }
        });
    }

    public Board getBoard() {
        return board;
    }

    private void callAlert () {
        File overFile = new File("src/GUI/assets/game_over.png");
        Image overImg = new Image(overFile.toURI().toString());
        over.setTitle("Game Over");
        over.setHeaderText("");
        over.setGraphic(new ImageView(overImg));
        over.setContentText("Your final result:\nScore :\t"+board.getScore()+"\nLevel :\t"+board.getLevel()+"\nLine :\t"+board.getLineClear());
        DialogPane dialog = over.getDialogPane();
        dialog.setStyle("-fx-text-alignment: center-right; -fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color: #FFFFFF; -fx-font-family: Garamond");
    }

    private void setImages() {
        File bgFile = new File("./src/GUI/assets/bg.jpg");
        Image bg = new Image(bgFile.getAbsoluteFile().toURI().toString());
        BackgroundSize bgSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
        borderPane.setBackground(new Background
                (new BackgroundImage(bg,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        bgSize
                )));

        File logoFile = new File("src/GUI/assets/logo.png");
        Image logoImg = new Image(logoFile.toURI().toString(), borderPane.getWidth()/8, borderPane.getHeight()/6, true, true);
        logo.setImage(logoImg);
    }
}
