package strategy;

import observable.CapteurImpl;
import observers.Afficheur;
import observers.Observer;
import proxy.Canal;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;

public class DiffusionAtomique implements AlgoDiffusion {

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
