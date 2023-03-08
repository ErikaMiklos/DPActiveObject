package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import observers.Observer;
import async.ObserverAsync;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.*;

public class Canal implements ObserverAsync,CapteurAsync {

    private final Capteur capteurImpl;
    private final Observer afficheur;
    private final ScheduledExecutorService scheduler;
    private final ExecutorService executor;

    public Canal(@NotNull Capteur capteur) {
        capteurImpl = capteur;
        this.afficheur = new Afficheur();
        capteur.attache(afficheur);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        this.executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public Future<Integer> getValue(){
        //Create a new Callable to perform the task
        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                //Perform the task
                return capteurImpl.getValue();
            }
        };
        //Schedule the Callable task with 500ms delay
        Future<Integer> result = scheduler.schedule(task, 500, TimeUnit.MILLISECONDS);
        try {
            Integer value = result.get();
            System.out.println("value = " + value);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        //scheduler.shutdown();

        return result;
    }

    @Override
    public Future<?> update(Capteur capteur){
        Runnable task = () -> {
            try {
                afficheur.update(this);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("observer updated");
        };
        /*Future<?> schedule = scheduler.schedule(task,
                new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);*/
        //executor.shutdown();

        return executor.submit(task);
    }


}
