package GUI;

import Main.Board;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
    private StackPane stackPane;

    @FXML
    private BorderPane borderPane;

    private Board board;

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
        borderPane.setCenter(board.getPane());

        score.textProperty().bind(board.scoreProperty().asString());
        level.textProperty().bind(board.levelProperty().asString());
        line.textProperty().bind(board.lineClearProperty().asString());

        board.startBoard();
    }

    public Board getBoard() {
        return board;
    }
}
