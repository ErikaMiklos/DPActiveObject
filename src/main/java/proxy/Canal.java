package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import async.ObserverAsync;
import org.jetbrains.annotations.NotNull;
import strategy.DiffusionAtomique;

import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

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
     * Cette méthode crée une méthode invocation type callable, ce qui
     * return une value Future. Ce méthode appelle la méthode getValue() de
     * la classe capteurImpl avec décalage random (entre 501-1499ms), qui permets
     * que les différents canaux récoivent différents valeurs selon le décalage,
     * concernant l'algo époque. Concernant l'algo atomique et l'algo séquence ce
     * décalage n'influence pas le résultat, car la valeur à récuperer rest la
     * même tant que tous les canaux ne l'ont pas reçue.
     * La retour de la valeur est décalé également aléatoirement (entre 501-1499ms).
     * Dans le cas d'algo époque ce deuxieme décalage permets de stimuler la situation
     * où un chiffre n'arrive pas dans le bon ordre, par exemple 1 arrive plus tard que 2.
     * Puis, remettre les valeurs en bon ordre se fait dans la class Afficheur.
     * Concernant les autre algos, ce deuxieme décalage n'influence pas la fonctionnement.
     * @return Future<Integer>
     */
    @Override
    public Future<Integer> getValue(){
        int randomDelay = new Random().nextInt(1000) + 501;
        //Create a new Callable to perform the task
        Callable<Integer> task = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                //Perform the task
                return capteurImpl.getValue();
            }
        };
        Future<Integer> result = schedulerGetValue.schedule(task,
                randomDelay, TimeUnit.MILLISECONDS);
        try {
            //return la value avec décalage aléatoire
            sleep(randomDelay);
            //System.out.println("Value returned: " +result.get() + " avec sleep " + random + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
