import java.io.*;
import java.util.concurrent.CountDownLatch;

public class WorkerWriteFile {
    private final String fileName;

    public WorkerWriteFile(String fileName) {
        this.fileName = fileName;
    }

    public void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content+"\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
