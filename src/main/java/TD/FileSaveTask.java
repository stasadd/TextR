package TD;

import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSaveTask extends Task<Boolean> {

    String filePath;
    String text;

    public FileSaveTask() {}

    public FileSaveTask(String filePath, String text) {
        this.filePath = filePath;
        this.text = text;
    }

    private Boolean saveFile() {
        if(new File(filePath).isFile()) {
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                byte[] buffer = text.getBytes();
                int size = buffer.length;
                for(int i = 0; i < size; i++) {
                    fos.write(buffer[i]);
                    this.updateProgress(i, size);
                    if(Thread.currentThread().isInterrupted()) return false;
                }
                return true;
            }catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, " Файл для сохранения не найден  ").showAndWait();
                return false;
            }
        } else {
            new Alert(Alert.AlertType.ERROR, " Файл для сохранения не найден  ").showAndWait();
            return false;
        }

    }

    @Override
    protected Boolean call() throws Exception {
        return saveFile();
    }
}
