import observable.CapteurImpl;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;
import strategy.DiffusionEpoque;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class TestMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //AtomicInteger test= new AtomicInteger();

        List<Canal> canals = new ArrayList<>();

        AlgoDiffusion algo = new DiffusionAtomique();

        CapteurImpl capteur = new CapteurImpl(algo);

        algo.configure(capteur);

        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();

        ScheduledExecutorService scheduler = newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                    capteur.tick();
            } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
            }
        };

        //scheduledExecutorService.schedule(task, 500, TimeUnit.MILLISECONDS);
        Future<?> f = scheduler.scheduleWithFixedDelay(task, 0, 500, TimeUnit.MILLISECONDS);
        //scheduler.awaitTermination(10, TimeUnit.SECONDS);

        Runnable cancelTask = () -> f.cancel(true);
        scheduler.schedule(cancelTask, 15, TimeUnit.SECONDS);
        //scheduler.shutdown();
        //scheduledExecutorService.shutdown();

    }
}
