package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CapteurImpl implements Capteur {
    private int value = 0;
    private final List<Observer> observers;
    private final AlgoDiffusion algo;
    private List<Canal> canals;

    public CapteurImpl(AlgoDiffusion algo)  {
        this.algo = algo;
        observers = new ArrayList<>();
        canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(this);
            canals.add(canal);
        }
    }

    public List<Canal> getCanals() {
        return canals;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public int getValue(){
        return  value;
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        value++;
        System.out.println("valeur capteurImpl: " + value);
        algo.execute();
    }


}
