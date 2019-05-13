package SB;

import GV.Fibonacci;
import GV.FibonacciTask;
import TD.*;
import ZD.FileSaver;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

        Thread threadFibonachi, threadLoad, threadSave, threadEdit;

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
                threadFibonachi = null;
                threadLoad = null;
                threadSave = null;

                idFilePath.setText("C:\\Users\\td779\\Desktop\\text.txt");
                setProgress(0);



                idFile.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                                if(threadFibonachi == null && threadLoad == null && threadSave == null) {
                                        setStatus(Status.Editing);
                                        setPercentage(0);
                                        setProgress(0);
                                        threadEdit = new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                        try {
                                                                Thread.sleep(3000);
                                                        } catch (Exception ex) {

                                                        }
                                                        setStatus(Status.Ready);
                                                        setPercentage(100);
                                                        setProgress(1);
                                                }
                                        });
                                        if(!threadEdit.isAlive()) {
                                                threadEdit.setDaemon(true);
                                                threadEdit.start();
                                        }

                                }
                        }
                });
        }

        public  void btnSave (){
                if(idFilePath.getText().isEmpty()) {
                        new Alert(Alert.AlertType.ERROR, " Файл для сохранения не найден  ").showAndWait();
                        return;
                }

                try {
                        setStatus(Status.Saving);
                        FileSaveTask fileSaveTask = new FileSaveTask(idFilePath.getText(), idFile.getText());
                        idProgress.progressProperty().unbind();
                        idProgress.progressProperty().bind(fileSaveTask.progressProperty());

                        Thread timer = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        try {
                                                while(!Thread.currentThread().isInterrupted()) {
                                                        int perc = (int) (idProgress.getProgress()*100);
                                                        setPercentage(perc);
                                                        Thread.sleep(10);
                                                }
                                        } catch (Exception ex) {  }
                                }
                        });

                        fileSaveTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                                new EventHandler<WorkerStateEvent>() {
                                        @Override
                                        public void handle(WorkerStateEvent event) {
                                                Boolean rezult = fileSaveTask.getValue();
                                                idProgress.progressProperty().unbind();
                                                setStatus(Status.Ready);
                                                timer.interrupt();
                                                threadSave = null;
                                        }
                                });

                        threadSave = new Thread(fileSaveTask);
                        threadSave.start();
                        timer.start();

                } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, "щось пішло не так").showAndWait();
                }
        }

        public void btnLoad (){
                if(idFilePath.getText().isEmpty()) {
                        new Alert(Alert.AlertType.ERROR, " Файл для загрузки не найден  ").showAndWait();
                        return;
                }

                try {
                        setStatus(Status.Loading);
                        FileLoadTask fileLoadTask = new FileLoadTask(idFilePath.getText());
                        idProgress.progressProperty().unbind();
                        idProgress.progressProperty().bind(fileLoadTask.progressProperty());

                        Thread timer = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                        try {
                                               while(!Thread.currentThread().isInterrupted()) {
                                                       int perc = (int) (idProgress.getProgress()*100);
                                                       setPercentage(perc);
                                                       Thread.sleep(10);
                                               }
                                        } catch (Exception ex) {  }
                                }
                        });

                        fileLoadTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                                new EventHandler<WorkerStateEvent>() {
                                        @Override
                                        public void handle(WorkerStateEvent event) {
                                                String rezult = fileLoadTask.getValue();
                                                idFile.setText(rezult);
                                                idProgress.progressProperty().unbind();
                                                setStatus(Status.Ready);
                                                timer.interrupt();
                                                threadLoad = null;
                                        }
                                });

                        threadLoad = new Thread(fileLoadTask);
                        threadLoad.start();
                        timer.start();

                } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, "щось пішло не так").showAndWait();
                }
        }

        public void btnCancel() {
                if(threadFibonachi != null && threadFibonachi.isAlive()) {
                        threadFibonachi.interrupt();
                        new Alert(Alert.AlertType.WARNING, " Поиск Х остановлен (кнопкой Cancel) ").showAndWait();
                        idProgress.progressProperty().unbind();
                        setStatus(Status.Ready);
                        setPercentage(0);
                        setProgress(0);
                        threadFibonachi = null;
                }

                if(threadLoad != null && threadLoad.isAlive()) {
                        threadLoad.interrupt();
                        new Alert(Alert.AlertType.WARNING, " Загрузка остановлена (кнопкой Cancel)  ").showAndWait();
                        idProgress.progressProperty().unbind();
                        setStatus(Status.Ready);
                        setPercentage(0);
                        setProgress(0);
                        threadLoad = null;
                }

                if(threadSave != null && threadSave.isAlive()) {
                        threadSave.interrupt();
                        new Alert(Alert.AlertType.WARNING, " Сохранение остановлено (кнопкой Cancel)  ").showAndWait();
                        idProgress.progressProperty().unbind();
                        setStatus(Status.Ready);
                        setPercentage(0);
                        setProgress(0);
                        threadSave = null;
                }
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
                                                while(!Thread.currentThread().isInterrupted()) {
                                                        int perc = (int) (idProgress.getProgress()*100);
                                                        setPercentage(perc);
                                                        Thread.sleep(10);
                                                }
                                        } catch (Exception ex) {  }
                                }
                        });

                        fibonacciTask.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED,
                                new EventHandler<WorkerStateEvent>() {
                                        @Override
                                        public void handle(WorkerStateEvent event) {
                                                String rezult = fibonacciTask.getValue();
                                                if(!rezult.isEmpty()) {
                                                        FileSaver.saveString(idFilePath.getText(), rezult);
                                                }
                                                idProgress.progressProperty().unbind();
                                                setStatus(Status.Ready);
                                                timer.interrupt();
                                                threadFibonachi = null;
                                        }
                                });

                        threadFibonachi = new Thread(fibonacciTask);
                        threadFibonachi.start();
                        timer.start();

                } catch (Exception ex) {
                        new Alert(Alert.AlertType.ERROR, " Неверный формат \n" + ErrorMs).showAndWait();
                }
        }

        private void setStatus(Status status) {
                Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                                idProcessProgress.setText(status.toString());
                        }
                });
        }

        private void setPercentage(int percent) {
                Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                                idProcessPercent.setText(percent + "%");
                        }
                });
        }

        private void setProgress(double step) {
                Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                                idProgress.setProgress(step);
                        }
                });
        }

}

