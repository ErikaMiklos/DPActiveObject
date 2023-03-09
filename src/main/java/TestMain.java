import observable.CapteurImpl;
import observers.Afficheur;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestMain {
    public static void main(String[] args){

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(1);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(1);
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

        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    capteur.tick();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        if(!capteur.isLocked()){
            scheduledExecutorService.schedule(task, 500, TimeUnit.MILLISECONDS);
        }

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
