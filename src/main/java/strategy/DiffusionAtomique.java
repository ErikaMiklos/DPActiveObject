package strategy;


public class DiffusionAtomique implements AlgoDiffusion {
    private int value;

    @Override
    public void configure() {
        this.value = 0;
    }

    @Override
    public void execute() {
        this.value++;
    }

    public int getValue() {
        return this.value;
    }


}
