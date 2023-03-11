package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import async.ObserverAsync;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.*;

public class Canal implements ObserverAsync,CapteurAsync {

    private final Capteur capteurImpl;
    private final Afficheur afficheur;
    private final ScheduledExecutorService schedulerGetValue;
    private final ScheduledExecutorService schedulerUpdate;
    private final ExecutorService executor;

    public Canal(@NotNull Capteur capteur) {
        capteurImpl = capteur;
        this.afficheur = new Afficheur();
        capteur.attache(afficheur);
        this.schedulerGetValue = Executors.newSingleThreadScheduledExecutor();
        this.schedulerUpdate = Executors.newSingleThreadScheduledExecutor();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public Afficheur getAfficheur() {
        return afficheur;
    }

    @Override
    public Future<Integer> getValue() throws InterruptedException {
        //Create a new Callable to perform the task
        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                //Perform the task
                return capteurImpl.getValue();
            }
        };
        Future<Integer> result = schedulerGetValue.schedule(task, new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);

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
        };
        //Future<?> scheduleUpdate = schedulerUpdate.schedule(task, 1, TimeUnit.MILLISECONDS);
        //return scheduleUpdate;
        return executor.submit(task);
    }

}
