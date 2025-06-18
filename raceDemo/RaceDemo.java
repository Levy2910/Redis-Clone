package raceDemo;

public class RaceDemo {
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                synchronized (RaceDemo.class){
                    count++; // synchronize makes sure all the thread run one at a time, at this task, using this RaceDemo.class or object (fixed) as a watcher to lock the operation
                }
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());

        System.out.println("Final count: " + count);
    }
}
