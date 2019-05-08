package SB;

import TD.FileLoader;
import ZD.FileSaver;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;
public class XMLFORMControllerMain {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private MenuItem idLoad;

        @FXML
        private MenuItem idSave;

        @FXML
        private JFXTextField idFilePath;

        @FXML
        private JFXTextArea idFile;

        @FXML
        void initialize() {
                idFilePath.setText("C:\\Users\\td779\\Desktop\\text.txt");
        }

        public  void btnSave (){
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                FileSaver.saveString(idFilePath.getText(), idFile.getText());
                        }
                }).start();

        }

        public void btnLoad (){
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                String filePath = idFilePath.getText();
                                try {
                                        String text = FileLoader.loadString(filePath);
                                        idFile.setText(text);
                                } catch (Exception ex) {   }
                        }
                }).start();
        }
}

