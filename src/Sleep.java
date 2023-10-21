import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Sleep implements Runnable{


    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();


//

    public void sleepCount() {
        lock.lock();
        while (true) {
            try {
                for (; Cat.sleep > 0; Cat.sleep--) {
                    Thread.sleep(150);
                    System.out.println("Left sleep percent: " + Cat.sleep);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
            if (Cat.sleep == 0) {
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

    public void sendsleep() {
        while (true) {
            try {
                lock.lock();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter sleeo code!");
                String code = scanner.nextLine();
                if (code.equalsIgnoreCase("222")) {
                    System.out.println("Sleep sending!");
                    for (; Cat.sleep < 100; Cat.sleep++) {
                        Thread.sleep(130);
                    }
                    condition.signalAll();
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                lock.unlock();
            }
            System.out.println("All sleep sent!");
        }

    };


//    public static void main(String[] args) {
//        Sleep sleep = new Sleep();
//        ExecutorService service = Executors.newFixedThreadPool(2);
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                sleep.sleepCount();
//            }
//        });
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                sleep.sendsleep();
//            }
//        });
//    }

    @Override
    public void run() {
        Sleep sleep = new Sleep();
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(new Runnable() {
            @Override
            public void run() {
                sleep.sleepCount();
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                sleep.sendsleep();
            }
        });
    }
}
