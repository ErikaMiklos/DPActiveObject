package observable;

import observers.Observer;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;
import java.util.Random;
import java.util.concurrent.*;

public class CapteurImpl extends Thread implements Capteur,Runnable {
    private int value;
    private ScheduledFuture<Integer> sFutureValue;
    private BlockingQueue<Integer> input;
    private BlockingQueue<Integer> output;
    private ScheduledExecutorService service;
    private List<Observer> observers = new ArrayList<>();
    private AlgoDiffusion diffusionAtomique;

    public CapteurImpl(BlockingQueue<Integer> input, BlockingQueue<Integer> output, AlgoDiffusion diffusionAtomique)  {
        this.input = input;
        this.output = output;
        this.diffusionAtomique = diffusionAtomique;
        diffusionAtomique.configure(input, output);
        this.service = Executors.newScheduledThreadPool(2);
    }

    @Override
    public void attache(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detache(Observer observer) {
        observers.remove(observer);
    }

    public void update() throws ExecutionException, InterruptedException {
        for(Observer o: observers){
            //service.schedule(o.update(this),2,TimeUnit.SECONDS);
            //Technique pop
            o.update(this);
        }
    }

    @Override
    public int getValue() throws ExecutionException, InterruptedException {
        return this.sFutureValue.get();
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {

        diffusionAtomique.execute();
        this.sFutureValue = service.schedule(() -> input.take(), new Random().nextInt(1000)+500, TimeUnit.MILLISECONDS);
        //this.value = input.take();
        update();
        System.out.println("CapteurImpl Current value: " + this.getValue());
    }

    @Override
    public void run() {
        while(value < 5){
            try {
                Thread.sleep(10);
                tick();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


}
