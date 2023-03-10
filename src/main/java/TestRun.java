import observable.CapteurImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class TestRun {
    private static final int QUEUE_CAPACITY = 1;
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
        algo = new DiffusionAtomique();
        capteur = new CapteurImpl(algo);
        algo.configure(capteur);

        ScheduledFuture<?> future =
                scheduler.scheduleAtFixedRate(() -> {
                    try {
                        capteur.tick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 500, TimeUnit.MILLISECONDS);
        try {
            sleep(2500);
            future.cancel(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
