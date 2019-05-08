package ZD;


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
        }
        System.out.println("SAVED!");
    }
}