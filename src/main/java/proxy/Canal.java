package proxy;

import async.CapteurAsync;
import observable.Capteur;
import observable.CapteurImpl;
import observers.Afficheur;
import observers.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Canal implements Capteur,Observer {

    private int value;
    private List<Observer> observers = new ArrayList<>();
    private Capteur capteur = new CapteurImpl();
    private Afficheur afficheur = new Afficheur();


    @Override
    public void attache(Observer observer) {
        capteur.attache(observer);
    }

    @Override
    public void detache(Observer observer) {
        capteur.detache(observer);
    }

    @Override
    public int getValue() {
        return capteur.getValue();
    }

    @Override
    public void tick() {
        capteur.tick();
    }

    @Override
    public void update(Capteur capteur) {
        afficheur.update(capteur);
    }
}
