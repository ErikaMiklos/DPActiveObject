package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.concurrent.BlockingQueue;

public class DiffusionEpoque implements AlgoDiffusion {

    private CapteurImpl capteur;
    private BlockingQueue<Integer> queue;
    private int value = 0;

    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur) {
        this.capteur = capteur;
        this.queue = queue;
    }

    @Override
    public void execute() throws InterruptedException {

        queue.put(value);
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }
    @Override
    public void setValue() {
         ++value;
    }
}
