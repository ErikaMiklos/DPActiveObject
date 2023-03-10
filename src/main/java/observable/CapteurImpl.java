package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CapteurImpl implements Capteur {
    private int value = 0;
    private boolean isLocked = false;
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
        return  this.value;
    }

    public void lock() {
        this.isLocked = true;
        //System.out.println("isLocked");
    }
    public void unLock() {
        this.isLocked = false;
        //System.out.println("UnLocked");
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        if (!isLocked){
            this.value++;
            System.out.println("valeur capteurImpl: " + this.value);
            algo.execute();
        }
        else {
            System.out.println("tick is locked!!!");
        }
    }


}
