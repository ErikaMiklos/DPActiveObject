import observable.CapteurImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;
import strategy.DiffusionEpoque;
import strategy.DiffusionSequence;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class TestRun {
    private static final int QUEUE_CAPACITY = 1;
    private AlgoDiffusion algo;
    private CapteurImpl capteur;
    private ScheduledExecutorService scheduler;
    private BlockingQueue<Integer> queue;

    @BeforeEach
    void setup() {
        scheduler = Executors.newScheduledThreadPool(2);
        queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
    }

    @AfterEach
    void stopThread() {
        scheduler.shutdownNow();
    }

    @Test
    @DisplayName("DiffusionAtomique")
    void diffusionAtomique() throws InterruptedException {

        algo = new DiffusionAtomique();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        System.out.println("DiffusionAtomique travaille pendant 8s, attendez svp ....");
        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.setValue();
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 500, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(8000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La Résultat:");
        for (Canal c: capteur.getCanals()) {
            System.out.println("Afficheur id " + c.getAfficheur().hashCode() +
                    " : Liste des valeurs récupérées: " + c.getAfficheur().getAfficheListe());
        }
    }

    /**
     * Pour que l'algorithme fonctionne correctement, deux thread indépendants
     * doivent être lancés simultanement. Un thread didié à incrémenter la valeur
     * sans cesse avec certain périodicité (setValue). L'autre fait la mise à jour,
     * mais il est blocqué de temps en temps (tick).
     */
    @Test
    @DisplayName("DiffusionSequence")
    void diffusionSequence() throws InterruptedException {

        algo = new DiffusionSequence();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        System.out.println("DiffusionSequence travaille pendant 8s, attendez svp ....");

        ScheduledFuture<?> futureValue =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.setValue();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, 200, 400, TimeUnit.MILLISECONDS);

        ScheduledFuture<?> futureTick =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.tick();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }, 500, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(8000);
            futureValue.cancel(true);
            futureTick.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La Résultat:");
        for (Canal c: capteur.getCanals()) {
            System.out.println("Afficheur id " + c.getAfficheur().hashCode() +
                    " : Liste des valeurs récupérées: " + c.getAfficheur().getAfficheListe());
        }

    }

    /**
     * Il y a pas de blockage dans ce procedure. Même l'incrémetation de
     * la valeur, même la mise à jour se déroulent avec certain périodicité
     * sans cesse. Par contre la périodicité est important. Si cette valeur est
     * égale ou très proche à 1500ms, tous les afficheurs récupérent tous
     * les valeur (1,2,3,4,5), similairement d'algo atomique. Par contre,
     * si la périodicité est trop petit (500ms), les afficheurs perdent
     * beaucoup des valeurs, car les valeurs changent trop vite. Alors
     * la périodicité idéale c'est trouve au millieu de 500-1500 ms.
     */
    @Test
    @DisplayName("DiffusionEpoque")
    void diffusionEpoque() throws InterruptedException {

        algo = new DiffusionEpoque();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        System.out.println("DiffusionEpoque travaille pendant 8s, attendez svp ....");
        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.setValue();
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 500, 900, TimeUnit.MILLISECONDS);
        try {
            sleep(8000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("La Résultat:");
        for (Canal c: capteur.getCanals()) {
            System.out.println("Afficheur id " + c.getAfficheur().hashCode() +
                    " : Liste des valeurs récupérées: " + c.getAfficheur().getAfficheListe());
        }

    }

    @Test
    @DisplayName("DiffusionRandom")
    void diffusionRandom() throws InterruptedException {

        int r = new Random().nextInt(3);
        switch (r) {
            case 0:
                diffusionAtomique(); break;
            case 1:
                diffusionSequence(); break;
            case 2:
                diffusionEpoque(); break;
        }

    }

}
