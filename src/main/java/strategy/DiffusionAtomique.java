package strategy;


import observable.Capteur;
import observers.Observer;
import proxy.Canal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class DiffusionAtomique implements AlgoDiffusion {

    private BlockingQueue<Integer> input;
    private BlockingQueue<Integer> output;
    private List<Canal> canals;
    private Capteur capteur;
    private int value = 0;


    @Override
    public void configure(BlockingQueue<Integer> input, BlockingQueue<Integer> output, List<Canal> canals, Capteur capteur) {
        this.capteur = capteur;
        this.canals = canals;
        this.input = input;
        this.output = output;

    }

    @Override
    public void execute() throws InterruptedException{
        input.put(++value);
        for(Canal c: canals) {
            c.update(capteur);
        }
        System.out.println("valeur écriture " + input.peek());
    }
}
