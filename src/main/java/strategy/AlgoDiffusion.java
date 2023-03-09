package strategy;

import observable.Capteur;
import proxy.Canal;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface AlgoDiffusion {
    void configure(BlockingQueue<Integer> input, BlockingQueue<Integer> output, List<Canal> canals, Capteur capteur);
    void execute() throws InterruptedException;
}
