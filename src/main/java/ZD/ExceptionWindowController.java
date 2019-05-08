package ZD;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class ExceptionWindowController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnOkId;

    @FXML
    private Label typeOfExceptionId;

    @FXML
    void ButtonOkClicked(MouseEvent event) {

    }

    @FXML
    void initialize() {
        assert btnOkId != null : "fx:id=\"btnOkId\" was not injected: check your FXML file 'Untitled'.";
        assert typeOfExceptionId != null : "fx:id=\"typeOfExeptionId\" was not injected: check your FXML file 'Untitled'.";

    }
}

