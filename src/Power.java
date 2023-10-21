import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Power implements Runnable{
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
//

    public void powerCount() {
        lock.lock();
        while (true) {
            try {
                for (; Cat.power > 0; Cat.power--) {
                    Thread.sleep(110);
                    System.out.println("Left power percent: " + Cat.power);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);

            }
            if (Cat.power == 0) {
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

    public void sendPower() {
        while (true) {
            try {
                lock.lock();

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter power code!");
                String code = scanner.nextLine();
                if (code.equalsIgnoreCase("333")) {
                    System.out.println("Power sending!");
                    for (; Cat.power < 100; Cat.power++) {
                        Thread.sleep(130);
                    }
                    condition.signalAll();
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                lock.unlock();
            }
            System.out.println("All power sent!");
        }

    };


//    public static void main(String[] args) {
//        Power power = new Power();
//        ExecutorService service = Executors.newFixedThreadPool(2);
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                power.powerCount();
//            }
//        });
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                power.sendPower();
//            }
//        });
//    }

    @Override
    public void run() {
        Power power = new Power();
        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(new Runnable() {
            @Override
            public void run() {
                power.powerCount();
            }
        });

        service.execute(new Runnable() {
            @Override
            public void run() {
                power.sendPower();
            }
        });
    }
}
