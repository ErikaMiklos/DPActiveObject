package observable;

import observers.Observer;
import strategy.AlgoDiffusion;

import java.util.ArrayList;
import java.util.List;
import java.lang.Thread;
import java.util.concurrent.*;

public class CapteurImpl extends Thread implements Capteur {
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

    public void update() throws ExecutionException, InterruptedException {
        //Technique pop
        for(Observer o: observers) {
            o.update(this);
        }
    }

    @Override
    public int getValue() throws ExecutionException, InterruptedException {
        //return this.sFutureValue.get();
        return  this.value;
    }

    @Override
    public void tick() throws InterruptedException, ExecutionException {

        diffusionAtomique.execute();
        //this.sFutureValue = service.schedule(() -> input.take(), new Random().nextInt(1000)+500, TimeUnit.MILLISECONDS);
        this.value = input.take();
        update();
        System.out.println("valeur lecture: " + this.getValue());
    }


}
