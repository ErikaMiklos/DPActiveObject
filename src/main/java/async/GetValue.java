package async;

import java.util.Random;
import java.util.concurrent.*;

public class GetValue implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return new Random().nextInt();
    }

    /*public class CallableScheduledExecutorExample {
        public static void main(String[] args) {

            ScheduledExecutorService scheduler
                    = Executors.newSingleThreadScheduledExecutor();

            Callable<Integer> task = new Callable<Integer>() {
                public Integer call() {
                    // fake computation time
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    return 1000000;
                }
            };

            int delay = 5;

            Future<Integer> result = scheduler.schedule(task, delay, TimeUnit.SECONDS);

            try {
                Integer value = result.get();
                System.out.println("value = " + value);
            } catch (InterruptedException | ExecutionException ex) {
                ex.printStackTrace();
            }
            scheduler.shutdown();
        }
    }*/
}
