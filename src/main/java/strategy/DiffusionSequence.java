package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.concurrent.BlockingQueue;

public class DiffusionSequence implements AlgoDiffusion {

    private CapteurImpl capteur;
    private BlockingQueue<Integer> queue;

    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur) throws InterruptedException {
        this.capteur = capteur;
        this.queue = queue;
        for (int i = 1; i < 6; i++) {
            queue.put(i);
        }
    }

    @Override
    public void execute(){

        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }
}
