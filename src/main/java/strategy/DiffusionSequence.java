package strategy;

import observable.CapteurImpl;
import proxy.Canal;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class DiffusionSequence implements AlgoDiffusion {

    private CapteurImpl capteur;
    private BlockingQueue<Integer> copy;
    private Queue<Integer> originList;

    @Override
    public void configure(BlockingQueue<Integer> queue, CapteurImpl capteur){
        this.capteur = capteur;
        this.copy = queue;
        this.originList = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            originList.add(i);
        }
    }

    @Override
    public void execute() throws InterruptedException {

        if(originList.peek() != null){
            copy.put(originList.peek());
        }
        originList.remove();
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }

    @Override
    public void setValue(){
        originList.remove();

    }

}
