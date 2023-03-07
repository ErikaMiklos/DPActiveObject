package proxy;

import observable.Capteur;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Canal extends Thread implements Capteur,Observer {

    private List<Observer> observers = new ArrayList<>();
    private Capteur capteur;
    private Observer observer;
    private ScheduledExecutorService scheduledExecutorService;

    public Canal(Capteur capteur) {
        this.capteur = capteur;
        //this.observer = new Afficheur();
        //capteur.attache(observer);
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
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
        return capteur.getValue();
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        capteur.tick();
    }

    @Override
    public void update(Capteur capteur) {
        Runnable task = () -> {
            try {
                observer.update(capteur);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        scheduledExecutorService.schedule(task,new Random().nextInt(1000)+500, TimeUnit.MILLISECONDS);
        scheduledExecutorService.shutdown();
        //observer.update(capteur);
    }


}
