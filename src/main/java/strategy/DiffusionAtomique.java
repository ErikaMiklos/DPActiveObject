package strategy;


import java.util.concurrent.BlockingQueue;

public class DiffusionAtomique implements AlgoDiffusion {

    private BlockingQueue<Integer> input;
    private BlockingQueue<Integer> output;
    private int value = 0;


    @Override
    public void configure(BlockingQueue<Integer> input, BlockingQueue<Integer> output) {
        this.input = input;
        this.output = output;

    }

    @Override
    public void execute() throws InterruptedException{
        input.put(++value);
        System.out.println("valeur écriture " + input.peek());
    }
}
