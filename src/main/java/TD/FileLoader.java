package TD;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class FileLoader  {
    public static String loadString(String filePath) throws IOException {
        String str = "";

        File file = new File(filePath);
        if (file.exists()) {
            str = new String(Files.readAllBytes(Paths.get(filePath)));
        } else throw null;

        return str;
    }
}
