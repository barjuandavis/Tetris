import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
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
        score.setText("011110000");
    }
}
