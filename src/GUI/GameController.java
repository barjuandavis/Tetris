package GUI;

import Main.Board;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends StackPane implements Initializable {
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
        //TODO implement Text changes
        board = new Board();
        score.setText(""+board.getScore());
        level.setText(""+board.getLevel());
        line.setText(""+board.getLineClear());
        //borderPane.setCenter(board.getPane());
        centerBox.getChildren().add(board.getPane());
        centerBox.setMaxWidth(board.getPane().getWidth());

        score.textProperty().bind(board.scoreProperty().asString());
        level.textProperty().bind(board.levelProperty().asString());
        line.textProperty().bind(board.lineClearProperty().asString());

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
        over.setTitle("Game Over");
        over.setHeaderText("");
        over.setGraphic(new ImageView(this.getClass().getResource("assets/game_over.png").toString()));
        over.setContentText("Your final result:\nScore :\t"+board.getScore()+"\nLevel :\t"+board.getLevel()+"\nLine :\t"+board.getLineClear());
        DialogPane dialog = over.getDialogPane();
        dialog.setStyle("-fx-text-alignment: center-right; -fx-font-size: 26px; -fx-font-weight: bold; -fx-background-color: #FFFFFF; -fx-font-family: Garamond");
    }
}
