import java.io.*;

public class WorkerReadeFile {
    private final String fileName;
    private String lastPrintedNumber = "";

    public WorkerReadeFile(String fileName) {
        this.fileName = fileName;
    }

    public String reade() {
        String lastLine = null;
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) {

            String currentLine;
            while ((currentLine = raf.readLine()) != null && !lastPrintedNumber.equals(lastLine)) {
                lastLine = currentLine;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lastLine;
    }
}
