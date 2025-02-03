
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static final String FILE_NAME = "test.txt";
    private static final Semaphore evenSemaphore = new Semaphore(1);
    private static final Semaphore oddSemaphore = new Semaphore(0);
    private static final Semaphore readeSemaphore = new Semaphore(0);

    public static void main(String[] args) {
        WorkerReadeFile workerReadeFile = new WorkerReadeFile(FILE_NAME);
        WorkerWriteFile workerWriteFile = new WorkerWriteFile(FILE_NAME);

        Thread w1 = new Thread(() -> {
            while (true) {
                Random random = new Random();
                int evenInt = random.nextInt(100000) * 2;
                try {
                    evenSemaphore.acquire();
                    workerWriteFile.writeToFile(String.valueOf(evenInt));
                    readeSemaphore.release();
                    Thread.sleep(1000);
                    oddSemaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread w2 = new Thread(() -> {
            while (true) {
                Random random = new Random();
                int oddInt = random.nextInt(100000) * 2 + 1;
                try {
                    oddSemaphore.acquire();
                    workerWriteFile.writeToFile(String.valueOf(oddInt));
                    readeSemaphore.release();
                    Thread.sleep(1000);
                    evenSemaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread r3 = new Thread(() -> {
            while (true) {
                try {
                    readeSemaphore.acquire();
                    System.out.println(workerReadeFile.reade());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        w1.start();
        w2.start();
        r3.start();
    }
}