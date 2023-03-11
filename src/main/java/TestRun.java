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

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class TestRun {
    private int sizeOfQueue;
    private AlgoDiffusion algo;
    private CapteurImpl capteur;
    private ScheduledExecutorService scheduler;
    private BlockingQueue<Integer> queue;

    @BeforeEach
    void setup() {
        scheduler = Executors.newScheduledThreadPool(2);
    }

    @AfterEach
    void stopThread() {}

    @Test
    @DisplayName("DiffusionAtomique")
    void diffusionAtomique() throws InterruptedException {
        sizeOfQueue = 1;
        queue = new ArrayBlockingQueue<>(sizeOfQueue);
        algo = new DiffusionAtomique();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        System.out.println("setValue");
                        capteur.setValue();
                        System.out.println("tick");
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
    }

    @Test
    @DisplayName("DiffusionSequence")
    void diffusionSequence() throws InterruptedException {
        sizeOfQueue = 1;
        queue = new ArrayBlockingQueue<>(sizeOfQueue);
        algo = new DiffusionSequence();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        System.out.println("setValue");
                        capteur.setValue();
                        System.out.println("tick");
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

    }

    @Test
    @DisplayName("DiffusionEpoque")
    void diffusionEpoque() throws InterruptedException {
        sizeOfQueue = 1;
        queue = new ArrayBlockingQueue<>(sizeOfQueue);
        algo = new DiffusionEpoque();
        capteur = new CapteurImpl(queue, algo);
        algo.configure(queue, capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        System.out.println("setValue");
                        capteur.setValue();
                        System.out.println("tick");
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 500, 700, TimeUnit.MILLISECONDS);
        try {
            sleep(7000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Canal c: capteur.getCanals()) {
            System.out.println("Afficheur id " + c.getAfficheur().hashCode() + " : Liste des valeurs récupérées: " + c.getAfficheur().getAfficheListe());
        }

    }
}
