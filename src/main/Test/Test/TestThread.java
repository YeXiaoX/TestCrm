/**
 * Created by yexiaoxin on 2017/8/22.
 */
public class TestThread {
    public static Integer i = 0;
    public static String lock = "";

    public static void addOdd() {
        synchronized (lock) {
            if (i % 2 != 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                lock.notify();
            }
        }
    }

    public static void addEven() {
        synchronized (lock) {
            if (i % 2 == 0) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                i++;
                lock.notify();
            }
        }
    }

    public static void main(String args[]) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    addOdd();
                    System.out.println(Thread.currentThread().getId() + ":" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    addEven();
                    System.out.println(Thread.currentThread().getId() + ":" + i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
        thread1.start();
    }

}
