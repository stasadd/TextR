package SB;

import GV.Fibonacci;
import GV.FibonacciTask;
import TD.FileLoader;
import ZD.FileSaver;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
        private JFXTextField idFildFibonachi;

        @FXML
        private JFXTextArea idFile;

        @FXML
        private JFXProgressBar idProgress;

        @FXML
        private Label idProcessProgress;

        @FXML
        private Label idProcessPercent;

        @FXML
        void initialize() {
                idFilePath.setText("C:\\Users\\td779\\Desktop\\text.txt");
                idProgress.setProgress(0);
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

        public void btnFibonacci(){
                try {
                        FibonacciTask fibonacciTask = new FibonacciTask(idFildFibonachi.getText());
                        idProgress.progressProperty().unbind();
                        idProgress.progressProperty().bind(fibonacciTask.progressProperty());
                        fibonacciTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                                new EventHandler<WorkerStateEvent>() {
                                        @Override
                                        public void handle(WorkerStateEvent event) {
                                                String rezult = fibonacciTask.getValue();
                                                FileSaver.saveString("C:\\Users\\td779\\Desktop\\text3.txt", rezult);
                                        }
                                });
                        new Thread(fibonacciTask).start();
                }
                catch (Exception ex) {

                }
        }
}

