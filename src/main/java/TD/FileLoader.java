package TD;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileLoader  {
    public static String loadString(String filePath) throws IOException {
        String str = "";
        try {
            File file = new File(filePath);
            if (file.exists()) {
                str = new String(Files.readAllBytes(Paths.get(filePath)));
            } else throw null;
        } catch (NullPointerException nex) {
            new Alert(Alert.AlertType.ERROR, " Файл для загрузки не найден  ").showAndWait();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, " Неверный формат  ").showAndWait();
        }
        return str;
    }
}
