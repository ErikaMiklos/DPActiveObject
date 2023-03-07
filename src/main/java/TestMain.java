import observable.CapteurImpl;
import observers.Afficheur;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class TestMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        BlockingQueue<Integer> input = new ArrayBlockingQueue<>(1);
        BlockingQueue<Integer> output = new ArrayBlockingQueue<>(1);

        Executor scheduler = Executors.newFixedThreadPool(1);
        ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(1);

        AlgoDiffusion algo = new DiffusionAtomique();

        CapteurImpl capteur = new CapteurImpl(input, output, algo);

        //new Thread((Runnable) algo).start();
        //new Thread(capteur).start();


        //List<Canal> canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(capteur);
            //new Thread(canal).start();
            //Afficheur afficheur = new Afficheur();
            //afficheur.setId(i);
            //canal.attache(afficheur);
            //canals.add(canal);
        }

        /*for (int i = 0; i < 4; i++) {
            Afficheur afficheur = new Afficheur();
            afficheur.setId(i);
            canals.get(i).attache(afficheur);
        }*/

        /*scheduler.execute( () -> {
            try {
                capteur.tick();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });*/


        scheduler.execute( () -> {
            for (int i = 0; i < 5; i++) {
                try {
                    //scheduledExecutorService.schedule(capteur.tick(), 500, TimeUnit.MILLISECONDS);
                    capteur.tick();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<Integer> sf = service.schedule(new GetValue(), 10, TimeUnit.SECONDS);*/

    }
}
