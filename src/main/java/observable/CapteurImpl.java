package observable;

import observers.Observer;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CapteurImpl extends Thread implements Capteur,Runnable {
    private int value;
    private BlockingQueue<Integer> input;
    private BlockingQueue<Integer> output;
    private ExecutorService pool;
    private List<Observer> observers = new ArrayList<>();
    private AlgoDiffusion diffusionAtomique;

    public CapteurImpl(BlockingQueue<Integer> input, BlockingQueue<Integer> output, AlgoDiffusion diffusionAtomique)  {
        this.input = input;
        this.output = output;
        this.diffusionAtomique = diffusionAtomique;
        diffusionAtomique.configure(input, output);
        Executors.newFixedThreadPool(10);
    }

    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    public void update() {
        for(Observer o: observers){
            //Technique pop
            o.update(this);
        }
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void tick() throws InterruptedException{
        //diffusionAtomique.execute();
        this.value = input.take();
        update();
        System.out.println("CapteurImpl Current value: " + this.value);
    }

    @Override
    public void run() {
        while(value < 5){
            try {
                Thread.sleep(10);
                tick();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
