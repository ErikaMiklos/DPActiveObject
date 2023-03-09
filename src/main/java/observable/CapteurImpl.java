package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;
import java.util.concurrent.*;

public class CapteurImpl implements Capteur {
    private int value;
    private BlockingQueue<Integer> input;
    private BlockingQueue<Integer> output;
    private ScheduledExecutorService service;
    private List<Observer> observers;
    private AlgoDiffusion diffusionAtomique;

    public CapteurImpl(BlockingQueue<Integer> input, BlockingQueue<Integer> output, AlgoDiffusion diffusionAtomique)  {
        this.input = input;
        this.output = output;
        this.diffusionAtomique = diffusionAtomique;
        //diffusionAtomique.configure(input, output);
        this.observers = new ArrayList<>();
        this.service = Executors.newScheduledThreadPool(4);
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
        //unlock
        this.value = input.take();
        System.out.println("valeur lecture: " + this.value);
        return  this.value;
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {
        //lock
        diffusionAtomique.execute();
        for(Observer o: observers) {
            o.update(new Canal(this));
        }

    }


}
