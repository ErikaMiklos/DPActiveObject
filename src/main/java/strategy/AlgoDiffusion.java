package strategy;

import observable.Capteur;
import observable.CapteurImpl;
import proxy.Canal;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;

public interface AlgoDiffusion {
    void configure(BlockingQueue<Integer> queue, CapteurImpl capteur);
    void execute() throws InterruptedException, ExecutionException;
}
