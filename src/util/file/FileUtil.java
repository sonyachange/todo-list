package util.file;

import model.Task;

import java.io.*;
import java.util.List;

public class FileUtil {
    public static void writeFile(Task task, String path) {
        try (FileOutputStream fos = new FileOutputStream(path, true)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(List<Task> task, String path) {
        try (FileInputStream fis = new FileInputStream(path)){
            while (fis.available() != 0) {
                ObjectInputStream ois = new ObjectInputStream(fis);
                Task task1 = (Task) ois.readObject();
                if (task1 != null)
                    task.add(task1);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
