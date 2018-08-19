package singleton;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Singleton {

    private static final RunnableFuture<Singleton> buildTask = new FutureTask<Singleton>(() -> new Singleton());
    private static final AtomicInteger number = new AtomicInteger();

    private Singleton() throws InterruptedException, ExecutionException, TimeoutException {
        Thread.sleep(300);
        assert buildTask.get(0, TimeUnit.NANOSECONDS) == null;
        number.addAndGet(1);
    }

    @Override
    public String toString() {
        return "Singleton 个数：" + number.get();
    }

    public static Singleton getInstance(long timeout, TimeUnit unit) throws TimeoutException {
        buildTask.run();
        try {
            return buildTask.get(timeout, unit);
        } catch (InterruptedException e) {
            return getInstance(timeout, unit);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof InterruptedException) {
                System.out.println("------------------");
                buildTask.run();
                try {
                    return buildTask.get(timeout, unit);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (ExecutionException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}
