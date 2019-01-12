public class CrossPrinter implements Runnable {
    public final int UPPER;// 打印上限

    private volatile int value;// 当前打印的数字

    public CrossPrinter(int lower, int upper) {
        this.value = lower;
        this.UPPER = upper;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        synchronized (this) {
            while (this.value <= this.UPPER) {
                System.out.printf("Thread %s print %d\n", threadName, this.value++);

                this.notify();
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (this.value >= this.UPPER) {
                this.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CrossPrinter printer = new CrossPrinter(1, 100);
        Thread threadA = new Thread(printer, "A");
        Thread threadB = new Thread(printer, "B");

        long t0 = System.nanoTime();
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        System.out.println(System.nanoTime() - t0);
    }
}
