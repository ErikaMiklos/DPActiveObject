import observable.Capteur;
import observable.CapteurImpl;
import observers.Afficheur;
import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) {
        CapteurImpl capteur = new CapteurImpl();

        List<Canal> canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(capteur);
            canals.add(canal);
        }

        for (int i = 0; i < 4; i++) {
            Afficheur afficheur = new Afficheur();
            afficheur.setId(i);
            canals.get(i).attache(afficheur);
        }

        DiffusionAtomique diffusionAtomique = new DiffusionAtomique(capteur);
        diffusionAtomique.run();

        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<Integer> sf = service.schedule(new GetValue(), 10, TimeUnit.SECONDS);*/

    }
}
