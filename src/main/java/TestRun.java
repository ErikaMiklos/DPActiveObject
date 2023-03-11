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
    private BlockingQueue<Integer> queue;

    @BeforeEach
    void setup() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler = Executors.newScheduledThreadPool(2);
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
                        System.out.println("tick");
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
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
                        System.out.println("tick");
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(8000);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
