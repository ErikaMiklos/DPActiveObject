import observable.CapteurImpl;
import observers.Afficheur;
import observers.Observer;

public class TestMain {
    public static void main(String[] args) {
        CapteurImpl capteur = new CapteurImpl();
        Observer observer1 = new Afficheur();
        capteur.attache(observer1);
        capteur.run();

        /*ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
        ScheduledFuture<Integer> sf = service.schedule(new GetValue(), 10, TimeUnit.SECONDS);*/

    }
}
