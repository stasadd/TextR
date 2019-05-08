package SB;

import GV.Fibonacci;
import TD.FileLoader;
import ZD.FileSaver;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
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

        public void btnFibonacci(){

                try {
                        ExecutorService service = Executors.newFixedThreadPool(10);
                        String number = idFildFibonachi.getText();
                        Fibonacci fibonacci = new Fibonacci(number);
                        Callable<String> c = new Callable<String>() {
                                @Override
                                public String call() throws Exception {
                                        return fibonacci.createRezult();
                                }
                        };
                        Future<String> futureCollable = service.submit(c);
                        FileSaver.saveString("C:\\Users\\td779\\Desktop\\text2.txt", futureCollable.get());

                        service.shutdown();

                } catch (Exception ex) {
                        System.out.println("error");
                }

//                new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                                String number = idFildFibonachi.getText();
//                                try {
//                                        Fibonacci fibonacci = new Fibonacci(number);
//                                        String rezult = fibonacci.createRezult();
//                                        FileSaver.saveString("C:\\Users\\td779\\Desktop\\text2.txt", rezult);
//
//                                } catch (Exception ex) {
//                                        System.out.println("error fibonachi");
//                                }
//                        }
//                }).start();

        }
}

