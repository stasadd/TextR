package TD;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorMassage {

    public void ShowMessage(String message, Stage mainStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ExceptionWindowFXML.fxml"));
            Scene scene = new Scene(root);
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.initOwner(mainStage);
            secondStage.initModality(Modality.WINDOW_MODAL);
            secondStage.setWidth(351);
            secondStage.setHeight(79);
            secondStage.setTitle("Error");
            secondStage.show();
        } catch (Exception ex) {
            System.out.println("inner error show mes");
        }
    }

}
