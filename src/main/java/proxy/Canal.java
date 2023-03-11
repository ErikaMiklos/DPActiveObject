package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import async.ObserverAsync;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Le canal est l'intermedaire entre le capteur et l'afficheur,
 * il joue le rôle de proxy pour le capteur et l'afficheur.
 */
public class Canal implements ObserverAsync,CapteurAsync {
    /**
     * attributs
     */
    private final Capteur capteurImpl;
    private final Afficheur afficheur;
    private final ScheduledExecutorService schedulerGetValue;
    private final ScheduledExecutorService schedulerUpdate;
    private final ExecutorService executor;

    /**
     * Constructeur
     * @param capteur: CapteurImpl
     */
    public Canal(@NotNull Capteur capteur) {
        capteurImpl = capteur;
        this.afficheur = new Afficheur();
        capteur.attache(afficheur);
        this.schedulerGetValue = Executors.newSingleThreadScheduledExecutor();
        this.schedulerUpdate = Executors.newSingleThreadScheduledExecutor();
        this.executor = Executors.newSingleThreadExecutor();
    }

    /**
     * Méthode pour récupérer d'Afficheur associé à ce canal actuel
     * @return : Afficheur
     */
    public Afficheur getAfficheur() {
        return afficheur;
    }

    /**
     * Cette méthode crée une classe getValue à l'aide d'un callable qui est ensuite utilisé
     * par le scheduler avec des délais d'initialisation allant de 500 à 1500 pour simuler le réseau.
     * @return Future<Integer>
     */
    @Override
    public Future<Integer> getValue(){
        //Create a new Callable to perform the task
        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                //Perform the task
                return capteurImpl.getValue();
            }
        };
        Future<Integer> result = schedulerGetValue.schedule(task,
                new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);

        return result;
    }

    /**
     * Ce fonctionne permets de mettre à jour l'Object CapteurImpl
     * et de transferer soi-même vers l'afficheur correspondant du canal actuel.
     * @param capteur: Rôle Observable
     * @return Future: le retour et un "placeholder", une promesse pour rendre le canal.
     */
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
