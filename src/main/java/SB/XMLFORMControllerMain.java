package SB;

import GV.Fibonacci;
import GV.FibonacciTask;
import TD.ErrorMassage;
import TD.FileLoader;
import TD.Status;
import ZD.FileSaver;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
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
        private MenuItem idCansel;

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
                                try {
                                        Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                        setStatus(Status.Saving);
                                                }
                                        });
                                        FileSaver.saveString(idFilePath.getText(), idFile.getText());
                                        Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                        setStatus(Status.Ready);
                                                }
                                        });
                                } catch (Exception ex) {
                                        new Alert(Alert.AlertType.ERROR, " Не удалось сохранить в файл  ").showAndWait();
                                }
                        }
                }).start();
        }

        public void btnLoad (){
                new Thread(new Runnable() {
                        @Override
                        public void run() {
                                Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                                setStatus(Status.Loading);
                                        }
                                });
                                String filePath = idFilePath.getText();
                                try {
                                        String text = FileLoader.loadString(filePath);
                                        idFile.setText(text);
                                        Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                        setStatus(Status.Ready);
                                                }
                                        });
                                } catch (Exception ex) {
                                        System.out.println("file not found");
                                        new Alert(Alert.AlertType.ERROR, " Файл для загрузки не найден  ").showAndWait();

                                }
                        }
                }).start();
        }

        public void btnCancel() {

                new Alert(Alert.AlertType.WARNING, " Поиск Х остановлен (кнопкой Cancel) ").showAndWait();
                new Alert(Alert.AlertType.WARNING, " Сохранение остановлено (кнопкой Cancel)  ").showAndWait();
                new Alert(Alert.AlertType.WARNING, " Загрузка остановлена (кнопкой Cancel)  ").showAndWait();
        }

        public void btnFibonacci() {
                setStatus(Status.Xsearching);
                String ErrorMs = null;
                try {


                        try {

                                Integer.parseInt(idFildFibonachi.getText());
                        } catch (Exception e) {
                                if (idFildFibonachi.getText().contains(".") || idFildFibonachi.getText().contains(","))
                                        ErrorMs = "Число должно быть целым (если оно дробное)";
                                else
                                        ErrorMs = "Значение должно быть числовым (если текст)";

                        }
                        FibonacciTask fibonacciTask = new FibonacciTask(idFildFibonachi.getText());
                        idProgress.progressProperty().unbind();
                        idProgress.progressProperty().bind(fibonacciTask.progressProperty());

                        Thread timer = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        try {
                                                int perc = fibonacciTask.getPercentage();
                                                while (perc <= 100) {
                                                        int finalPerc = perc;
                                                        Platform.runLater(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                        setPercentage(finalPerc);
                                                                }
                                                        });
                                                        perc = fibonacciTask.getPercentage();
                                                        Thread.sleep(200);
                                                        if (perc > 100) break;
                                                }
                                        } catch (Exception ex) {
                                        }
                                }
                        });


                        fibonacciTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                                new EventHandler<WorkerStateEvent>() {
                                        @Override

                                        public void handle(WorkerStateEvent event) {

                                                String rezult = fibonacciTask.getValue();
                                                FileSaver.saveString("C:\\Users\\td779\\Desktop\\text3.txt", rezult);
                                                setStatus(Status.Ready);
                                        }
                                });


                        new Thread(fibonacciTask).start();
                        timer.setDaemon(true);
                        timer.start();

                } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, " Неверный формат \n" + ErrorMs).showAndWait();
                }
        }

        private void setStatus(Status status) {
                idProcessProgress.setText(status.toString());
        }

        private void setPercentage(int percent) {
                idProcessPercent.setText(percent + "%");
        }

}

