package strategy;

import observable.CapteurImpl;
import proxy.Canal;

public class DiffusionSequence implements AlgoDiffusion {

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
