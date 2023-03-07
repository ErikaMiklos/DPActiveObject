package proxy;

import observable.Capteur;
import observers.Afficheur;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Canal extends Thread implements Capteur,Observer {

    private List<Observer> observers = new ArrayList<>();
    private Capteur capteur;
    private Observer observer;

    public Canal( Capteur capteur) {
        this.capteur = capteur;
        this.observer = new Afficheur();
        capteur.attache(observer);
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
    public void update(Capteur capteur) throws ExecutionException, InterruptedException {
        observer.update(capteur);
    }


}
