package strategy;


import java.util.concurrent.BlockingQueue;

public class DiffusionAtomique implements AlgoDiffusion, Runnable {

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
        System.out.println("input value " + input.peek());
    }

    @Override
    public void run() {
        while(value < 5){
            try {
                Thread.sleep(10);
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
