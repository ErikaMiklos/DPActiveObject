import observable.CapteurImpl;
import observers.Afficheur;
import observers.Observer;
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

    @Test
    @DisplayName("DiffusionAtomique")
    void diffusionAtomique() throws InterruptedException {

        algo = new DiffusionAtomique();
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

    @Test
    @DisplayName("DiffusionSequence")
    void diffusionSequence() throws InterruptedException {

        algo = new DiffusionSequence();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        System.out.println("DiffusionEpoque travaille pendant 8s, attendez svp ....");
        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.setValue();
                        capteur.tick();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
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
        HashMap<Integer, AlgoDiffusion> map = new HashMap<>();
        map.put(1, new DiffusionAtomique());
        map.put(2, new DiffusionSequence());
        map.put(3, new DiffusionEpoque());
        Random random = new Random();
        int select = random.nextInt(4);

        algo = map.get(select);
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);
        int period;
        if(select == 3) {
            period = 900;
        }else {
            period = 500;
        }

        System.out.println(map.get(select).toString() + " travaille pendant 8s, attendez svp ....");
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
                }, 500, period, TimeUnit.MILLISECONDS);
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

}
