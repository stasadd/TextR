package TD;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileLoadTask extends Task<String> {

    String filePath;
    String output;

    public FileLoadTask() {}

    public FileLoadTask(String filePath) {
        this.filePath = filePath;
        this.output = "";
    }

    private String getTextFromFile() {
        output = "";
        try (FileInputStream fis = new FileInputStream(filePath))
        {
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer, 0, size);
            for(int i =0; i < buffer.length; i++) {
                output += (char)buffer[i];
                this.updateProgress(output.length(), size);
                if(Thread.currentThread().isInterrupted()) return "";
            }
        }catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, " Файл для загрузки не найден  ").showAndWait();
        }
        return output;
    }

    @Override
    protected String call() throws Exception {
        return getTextFromFile();
    }
}
