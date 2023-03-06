package strategy;

import java.util.concurrent.BlockingQueue;

public interface AlgoDiffusion {
    void configure(BlockingQueue<Integer> input, BlockingQueue<Integer> output);
    void execute() throws InterruptedException;
}
