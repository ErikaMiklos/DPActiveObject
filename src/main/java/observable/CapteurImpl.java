package observable;

import observers.Observer;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CapteurImpl implements Capteur {
    private int value = 0;
    private boolean isLocked = false;
    private final List<Observer> observers;
    private final AlgoDiffusion algo;

    public CapteurImpl(AlgoDiffusion algo)  {
        this.algo = algo;
        this.observers = new ArrayList<>();
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
    }
    public void unLock() {
        this.isLocked = false;
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
