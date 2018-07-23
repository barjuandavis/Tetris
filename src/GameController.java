import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/game.fxml"));
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

        board.startBoard();

    }

    public Board getBoard() {
        return board;
    }
}
