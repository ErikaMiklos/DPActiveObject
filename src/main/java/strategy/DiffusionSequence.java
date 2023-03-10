package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.concurrent.BlockingQueue;

public class DiffusionSequence implements AlgoDiffusion {

    private CapteurImpl capteur;
    private BlockingQueue<Integer> queue;

    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur) {
        this.capteur = capteur;
        this.queue = queue;
    }

    @Override
    public void execute() {
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }
}
