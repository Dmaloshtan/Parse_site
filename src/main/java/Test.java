import java.io.IOException;

public class Test {
    private int c;
    public static boolean isWrite;

    public int value(){
        return c;
    }

    public Test() {
        c = 0;
    }

    public synchronized void inc(){
        c++;
        System.out.print(c);
        isWrite = true;
        notifyAll();
    }

    public synchronized void dec(){
        while(!isWrite){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        c--;
        System.out.print(c);
        isWrite = false;
        notifyAll();
    }

    public static void main(String[] args) {
        Test test = new Test();

        Thread incThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    test.inc();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread decThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    test.dec();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        try {
            incThread.start();
            decThread.start();
            incThread.join();
            decThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
