import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Cat {
    public static int food = 100;
    public static int sleep = 100;
    public static int power = 100;

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();




    public void foodCount() {
        while (true) {
            try {
                for (; food > 0; food--) {
                    Thread.sleep(150);
                    System.out.println("Left food percent: " + food);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
            if (food == 0) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }
        }
    };


    public void sendFood() {
        while (true) {
            try {
                lock.lock();
                System.out.print("Foods sending!");
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter food code!\n");
                String code = scanner.nextLine();
                if (code.equalsIgnoreCase("111")) {
                    for (; food < 100; food++) {
                        Thread.sleep(130);
                    }
                    condition.signalAll();
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                lock.unlock();
            }
            System.out.println("All food sent!");
        }

    };


    public static void main(String[] args) {

        Cat cat = new Cat();
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(new Runnable() {
            @Override
            public void run() {
                cat.foodCount();
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                cat.sendFood();
            }
        });
    }

}
