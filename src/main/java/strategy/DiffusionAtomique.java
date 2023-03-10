package strategy;

import observable.CapteurImpl;
import proxy.Canal;
import java.util.List;
import java.util.concurrent.Future;

public class DiffusionAtomique implements AlgoDiffusion {

    private CapteurImpl capteur;
    @Override
    public void configure(CapteurImpl capteur) {
        this.capteur = capteur;
    }

    @Override
    public void execute(){
        this.capteur.lock();
        for(Canal c: capteur.getCanals()) {
            c.update(capteur);
        }
        /*if(canals.stream().map(Canal::getValue).allMatch(Future::isDone)) {
            this.capteur.unLock();
        }*/
        this.capteur.unLock();

    }
}
