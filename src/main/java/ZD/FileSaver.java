package ZD;

import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;

public abstract class FileSaver
{
    public static void saveString(String Path,String text)
    {
        try(FileOutputStream fos=new FileOutputStream(Path))
        {
            byte[] buffer = text.getBytes();
            fos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
            new Alert(Alert.AlertType.ERROR, " Файл для сохранения не найден  ").showAndWait();
        }
        System.out.println("SAVED!");
    }
}