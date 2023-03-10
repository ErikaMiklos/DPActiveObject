package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import observers.Observer;
import async.ObserverAsync;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Le canal est l'intermedaire entre le capteur et l'afficheur,
 * il joue le rôle de proxy pour le capteur et l'afficheur.
 */
public class Canal implements ObserverAsync,CapteurAsync {

    private final Capteur capteurImpl;
    private final Observer afficheur;
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

    /**
     * Cette méthode crée une classe getValue à l'aide d'un callable qui est ensuite utilisé
     * par le scheduler avec des délais d'initialisation allant de 500 à 1500 pour simuler le réseau.
     *
     * @return Future<Integer>
     */
    @Override
    public Future<Integer> getValue(){
        //Create a new Callable to perform the task
        Callable<Integer> task = () -> {
            //Perform the task
            return capteurImpl.getValueAsync();
        };
        //Schedule the Callable task with 500ms delay
        //Future<Integer> result = schedulerGetValue.schedule(task, 1, TimeUnit.MILLISECONDS);
        int delay = (int) (500 + Math.random()*(1500-500));
        Future<Integer> result = schedulerGetValue.schedule(task, delay, TimeUnit.MILLISECONDS);
        try {
            Integer value = result.get();
            System.out.println("Canal Getvalue = " + value);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        //schedulerGetValue.shutdown();

        return result;
    }

    /**
     *
     * @param capteur
     * @return Future<Integer>
     */
    @Override
    public Future<?> update(Capteur capteur){
        Runnable task = () -> {
            try {
                afficheur.update(this);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Canal updated");
        };
        /*Future<?> scheduleUpdate = schedulerUpdate.schedule(task,
                new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);*/
        //schedulerUpdate.shutdown();

        //return scheduleUpdate;
        return executor.submit(task);
    }

}
