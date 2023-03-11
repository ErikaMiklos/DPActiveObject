package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;
import strategy.DiffusionSequence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CapteurImpl implements Capteur {
    private int value = 0;
    private final List<Observer> observers;
    private final AlgoDiffusion algo;
    private BlockingQueue<Integer> queue;
    private List<Canal> canals;
    private int compteur = 0;


    public CapteurImpl(BlockingQueue<Integer> queue, AlgoDiffusion algo)  {
        this.algo = algo;
        observers = new ArrayList<>();
        canals = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Canal canal = new Canal(this);
            canals.add(canal);
        }
        this.queue = queue;
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
    public int getValue() throws InterruptedException {

        if(algo.getClass().equals(DiffusionAtomique.class) || algo.getClass().equals(DiffusionSequence.class)){
            compteur++;
            if (compteur == 4) {
                compteur = 0;
                return queue.take();
            } else {
                return queue.peek();
            }
        } else {
            return value;
        }

    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        if(value < 6) {
            ++value;
            algo.execute();
        }

    }

    public void setValue() throws InterruptedException {
        algo.setValue();
    }


}
