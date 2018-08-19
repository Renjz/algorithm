/**
 * Created by renjz on 2018/4/29.
 */
public class MyThread extends Thread {

    /**
     * 自定义的join方法
     */
    public final void join2() {
        while (this.isAlive()) ;
    }

    @Override
    public void run() {
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("my thread run over");
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        myThread.join2();
        System.out.println("main thread run over");
    }
}
