/**
 * Created by renjz on 2018/4/29.
 */
public class MutiLockTest {

    public static class DoubleLock implements Runnable {

        private final Object lock1;
        private final Object lock2;

        public DoubleLock(Object lock1, Object lock2) {
            this.lock1 = lock1;
            this.lock2 = lock2;
        }

        @Override
        public void run() {
            synchronized (this.lock1) {
                System.out.println(Thread.currentThread().getName() + "已获得锁" + this.lock1.toString());
                synchronized (this.lock2) {
                    System.out.println(Thread.currentThread().getName() + "已获得锁" + this.lock2.toString());
                    try {
                        this.lock2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static class SingleLock implements Runnable {
        private final Object lock;

        public SingleLock(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.printf("线程%s试图获得锁%s\n", Thread.currentThread().getName(), this.lock.toString());
            synchronized (this.lock) {
                System.out.printf("线程%s已获得锁%s\n", Thread.currentThread().getName(), this.lock.toString());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final Object lock1 = "lock1", lock2 = "lock2";
        Runnable doubleLock = new DoubleLock(lock1, lock2);
        Runnable singleLock1 = new SingleLock(lock1), singleLock2 = new SingleLock(lock2);

        Thread doubleLockThread = new Thread(doubleLock, "doubleLockThread");
        Thread singleLockThread1 = new Thread(singleLock1, "singleLockThread1");
        Thread singleLockThread2 = new Thread(singleLock2, "singleLockThread2");

        doubleLockThread.start();
        Thread.sleep(500);
        singleLockThread1.start();
        singleLockThread2.start();
    }
}