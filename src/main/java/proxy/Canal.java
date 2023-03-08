package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observers.Afficheur;
import observers.Observer;
import async.ObserverAsync;

import java.util.Random;
import java.util.concurrent.*;

public class Canal implements ObserverAsync,CapteurAsync {

    private Capteur capteurImpl;
    private Observer afficheur;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledExecutorService futureExecutorService;

    public Canal(Capteur capteur) {
        capteurImpl = capteur;
        this.afficheur = new Afficheur();
        capteur.attache(afficheur);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.futureExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public Future<Integer> getValue(){
        Callable<Integer> ctask = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                return capteurImpl.getValue();
            }
        };
        Future<Integer> result = futureExecutorService.schedule(ctask, 500, TimeUnit.MILLISECONDS);
        try {
            Integer value = result.get();
            System.out.println("value = " + value);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        futureExecutorService.shutdown();

        return result;
    }

    @Override
    public void update(Capteur capteur){
        Runnable rtask = () -> {
            try {
                afficheur.update(this);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("observer updated");
        };
        scheduledExecutorService.schedule(rtask, new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.shutdown();
    }


}
