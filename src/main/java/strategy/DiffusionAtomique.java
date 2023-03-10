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
    public BlockingQueue<Integer> queue;
    @Override
    public void configure(int sizeOfQueue, CapteurImpl capteur) {
        this.capteur = capteur;
        this.queue = new ArrayBlockingQueue<>(sizeOfQueue);
    }

    @Override
    public void execute() throws InterruptedException {
        queue.put(capteur.getValue());

        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }

        //capteur.getCanals().stream().map(Canal::getAfficheur).forEach().filter;

        queue.take();
        /*if(canals.stream().map(Canal::getValue).allMatch(Future::isDone)) {
        }*/


    }
}
