import observable.CapteurImpl;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class TestMain {
    public static void main(String[] args) throws InterruptedException {

        AlgoDiffusion algo = new DiffusionAtomique();
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        CapteurImpl capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();

        ScheduledExecutorService scheduler = newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                if(capteur.getValue()<5){
                    capteur.tick();
                }

            } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
            }
        };

        //scheduledExecutorService.schedule(task, 500, TimeUnit.MILLISECONDS);
        Future<?> f = scheduler.scheduleWithFixedDelay(task, 0, 500, TimeUnit.MILLISECONDS);
        //scheduler.awaitTermination(10, TimeUnit.SECONDS);

        Runnable cancelTask = () -> f.cancel(true);
        scheduler.schedule(cancelTask, 2500, TimeUnit.MILLISECONDS);
        //scheduler.shutdown();
        //scheduledExecutorService.shutdown();

    }
}
