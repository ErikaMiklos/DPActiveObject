import async.GetValue;
import observable.Capteur;
import observable.CapteurImpl;
import observers.Afficheur1;
import observers.Observer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TestMain {
    public static void main(String[] args) {
        CapteurImpl capteur = new CapteurImpl();
        Observer observer1 = new Afficheur1();
        capteur.attache(observer1);
        capteur.run();

        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<Integer> sf = service.schedule(new GetValue(), 10, TimeUnit.SECONDS);

    }
}
