package singleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SingletonTest {

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Runnable testCase = () -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " interrupted");
                e.printStackTrace();
            }
            try {
                System.out.println(Thread.currentThread().getName() + " --> " + Singleton.getInstance(3, TimeUnit.SECONDS));
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        };
        final int COUNT = 2;
        Thread[] testThreads = new Thread[COUNT];
        for (int i = 0; i < COUNT; i++) {
            testThreads[i] = new Thread(testCase);
            testThreads[i].start();
        }

        Thread.sleep(100);
        countDownLatch.countDown();
        testThreads[0].interrupt();
        System.out.println("************* 万箭齐发 *************");
    }
}
