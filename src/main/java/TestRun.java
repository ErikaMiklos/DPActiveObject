import observable.CapteurImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;
import strategy.DiffusionSequence;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class TestRun {
    private int sizeOfQueue;
    private AlgoDiffusion algo;
    private CapteurImpl capteur;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    void setup() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterEach
    void stopThread() {}

    @Test
    @DisplayName("DiffusionAtomique")
    void diffusionAtomique() {
        sizeOfQueue = 1;
        algo = new DiffusionAtomique();
        capteur = new CapteurImpl(sizeOfQueue, algo);
        algo.configure(capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        System.out.println("tick");
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(7000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("DiffusionSequence")
    void diffusionSequence() {
        sizeOfQueue = 5;
        algo = new DiffusionSequence();
        capteur = new CapteurImpl(sizeOfQueue, algo);
        algo.configure(capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        System.out.println("tick");
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(6000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
