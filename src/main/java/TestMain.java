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
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AtomicInteger test= new AtomicInteger();

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(5);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(5);
        List<Canal> canals = new ArrayList<>();

        AlgoDiffusion algo = new DiffusionAtomique();

        CapteurImpl capteur = new CapteurImpl(input, output, algo);


        for (int i = 1; i < 5; i++) {
            Canal canal = new Canal(capteur);
            canals.add(canal);
            //capteur.attache(canal);
        }
        algo.configure(input, output, canals, capteur);

        ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();

        ScheduledExecutorService scheduler = newScheduledThreadPool(2);

        Runnable task = () -> {
            try {
                if(capteur.getValue()<5){
                    capteur.tick();
                }
                else{
                    scheduler.shutdown();
                }

                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
        };

        //scheduledExecutorService.schedule(task, 500, TimeUnit.MILLISECONDS);


        scheduler.scheduleWithFixedDelay(task, 0, 500, TimeUnit.MILLISECONDS);

        scheduledExecutorService.shutdown();

        //Executor scheduler = Executors.newFixedThreadPool(1);

        /*scheduler.execute( () -> {
            try {
                capteur.tick();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });*/
    }
}
