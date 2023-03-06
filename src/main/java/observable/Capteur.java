package observable;

import observers.Observer;
import proxy.Canal;
import strategy.AlgoDiffusion;
import strategy.DiffusionAtomique;

import java.util.concurrent.BlockingQueue;

public interface Capteur {
    void attache(Observer observer);
    void detache(Observer observer);
    int getValue();
    void tick() throws InterruptedException;
}
