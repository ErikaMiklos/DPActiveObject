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

    @Override
    public void configure(CapteurImpl capteur) {
        this.capteur = capteur;
    }

    @Override
    public void execute() {
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
    }
}
