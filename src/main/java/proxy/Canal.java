package proxy;

import observable.Capteur;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Canal extends Thread implements Capteur,Observer {

    private List<Observer> observers = new ArrayList<>();
    private Capteur capteur;
    private Observer observer;
    private ScheduledExecutorService scheduledExecutorService;
    private ScheduledExecutorService futureExecutorService;

    public Canal(Capteur capteur) {
        this.capteur = capteur;
        //this.observer = new Afficheur();
        //capteur.attache(observer);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.futureExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void attache(Observer observer) {
        capteur.attache(observer);
    }

    @Override
    public void detache(Observer observer) {
        capteur.detache(observer);
    }

    @Override
    public int getValue() throws ExecutionException, InterruptedException {
        Callable<Integer> ctask = new Callable<Integer>() {
            public Integer call() throws ExecutionException, InterruptedException {
                return capteur.getValue();
            }
        };
        Future<Integer> result = futureExecutorService.schedule(ctask, 1, TimeUnit.SECONDS);
        try {
            Integer value = result.get();
            System.out.println("value = " + value);
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
        futureExecutorService.shutdown();

        return result.get();
        //return capteur.getValue();
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        capteur.tick();
    }

    @Override
    public void update(Capteur capteur) {
        Runnable rtask = () -> {
            try {
                observer.update(capteur);
                System.out.println("observer updated");
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        scheduledExecutorService.schedule(rtask, new Random().nextInt(1000) + 500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.shutdown();
        //observer.update(capteur);
    }


}
